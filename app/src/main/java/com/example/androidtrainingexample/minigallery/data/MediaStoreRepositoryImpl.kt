package com.example.androidtrainingexample.minigallery.data

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.provider.OpenableColumns
import com.example.androidtrainingexample.minigallery.model.MediaImage
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Implementation cụ thể của [MediaRepository], chịu trách nhiệm giao tiếp trực tiếp với hệ điều hành Android:
 * 1. Truy vấn và quan sát thay đổi của [MediaStore] hệ thống qua [ContentResolver].
 * 2. Đọc metadata của các URI độc lập do Photo Picker cấp quyền truy cập.
 *
 * CÁC QUY TẮC BẢO MẬT & BỘ NHỚ ĐƯỢC TUÂN THỦ:
 * - Không bao giờ truy xuất cột `_data` (tuân thủ Scoped Storage từ Android 10+).
 * - Sử dụng [ContentUris.withAppendedId] để tạo Content URI chuẩn cho MediaStore.
 * - Toàn bộ thao tác I/O đọc ổ đĩa hoặc truy vấn database chạy trên [Dispatchers.IO].
 * - Không ghi, không sao chép (copy) file ảnh vào bộ nhớ riêng của app.
 */
class MediaStoreRepositoryImpl(
    private val contentResolver: ContentResolver,
    private val context: Context
) : MediaRepository {

    /**
     * Bọc API lắng nghe [ContentObserver] truyền thống của Android thành [Flow] hiện đại của Kotlin.
     *
     * CƠ CHẾ HOẠT ĐỘNG:
     * 1. `callbackFlow`: Cầu nối chuyển đổi các sự kiện callback sang Coroutine Flow.
     * 2. `registerContentObserver`: Lắng nghe mọi thay đổi (thêm/sửa/xóa ảnh) trên `EXTERNAL_CONTENT_URI`.
     * 3. `trySend(Unit)`: Phát ngay một sự kiện đầu tiên để UI có dữ liệu ban đầu mà không cần chờ thay đổi.
     * 4. `awaitClose`: Dọn dẹp quan trọng nhất! Tự động hủy đăng ký observer khi ViewModel bị hủy hoặc
     *    màn hình không còn collect flow này nữa, chống rò rỉ bộ nhớ (Memory Leak).
     * 5. `conflate()`: Nếu có quá nhiều sự kiện dồn dập, chỉ giữ sự kiện mới nhất.
     * 6. `debounce(300L)`: Chờ 300ms sau sự kiện thay đổi cuối cùng mới thực hiện query, tránh tình trạng
     *    chụp liên tiếp hoặc quét hàng loạt làm app liên tục truy vấn SQLite quá tải.
     * 7. `map { queryImages() }`: Chuyển đổi tín hiệu kích hoạt thành danh sách ảnh thực tế.
     * 8. `flowOn(Dispatchers.IO)`: Đảm bảo toàn bộ luồng xử lý trên chạy trên Background IO Thread.
     */
    @OptIn(kotlinx.coroutines.FlowPreview::class)
    override fun observeImages(): Flow<List<MediaImage>> {
        return callbackFlow {
            // Observer nhận thông báo từ hệ thống mỗi khi bảng MediaStore.Images thay đổi
            val observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
                override fun onChange(selfChange: Boolean, uri: Uri?) {
                    // Phát tín hiệu thông báo vào channel của Flow
                    trySend(Unit)
                }
            }

            // Đăng ký theo dõi toàn bộ collection ảnh trên bộ nhớ ngoài (notifyForDescendants = true)
            contentResolver.registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                true,
                observer
            )

            // Phát tín hiệu ban đầu để tải dữ liệu ngay khi vừa bắt đầu collect
            trySend(Unit)

            // Khối này được gọi khi Flow bị đóng hoặc Coroutine Scope bị cancel
            awaitClose {
                contentResolver.unregisterContentObserver(observer)
            }
        }
            .conflate() // Bỏ qua các tín hiệu bị tồn đọng khi consumer chưa xử lý kịp
            .debounce(300L) // Khử rung 300ms: gộp các thông báo thay đổi liên tiếp
            .map { queryImages() } // Truy vấn lại danh sách ảnh mới nhất
            .flowOn(Dispatchers.IO) // Chạy tác vụ trên Background Thread
    }

    /**
     * Truy vấn danh sách ảnh từ MediaStore bằng ContentResolver.
     *
     * CHI TIẾT KỸ THUẬT:
     * - [projection]: Chỉ định rõ các cột cần đọc, tránh dùng `null` (đọc toàn bộ) gây lãng phí RAM.
     * - [selection]: `IS_PENDING = 0` loại trừ các ảnh đang trong quá trình ghi (ví dụ camera đang chụp dở),
     *   tính năng được giới thiệu từ Android 10 (API 29).
     * - `Cursor.use { ... }`: Extension function tự động đóng Cursor ngay cả khi xảy ra Exception.
     * - `currentCoroutineContext().ensureActive()`: Kiểm tra tính sẵn sàng của Coroutine sau mỗi vòng lặp
     *   để có thể hủy ngay lập tức nếu người dùng thoát màn hình giữa chừng khi danh sách có hàng ngàn ảnh.
     */
    override suspend fun queryImages(): List<MediaImage> = withContext(Dispatchers.IO) {
        val resultList = mutableListOf<MediaImage>()
        
        // Danh sách các cột tối thiểu cần đọc từ bảng Images của hệ thống
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DATE_ADDED
        )

        // Sắp xếp mặc định: ảnh mới thêm vào trước
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            "${MediaStore.Images.Media.IS_PENDING} = 0", // Chỉ lấy ảnh đã ghi hoàn tất
            null,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val mimeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
            val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)

            while (cursor.moveToNext()) {
                // Kiểm tra nếu Coroutine bị hủy thì dừng vòng lặp ngay lập tức
                currentCoroutineContext().ensureActive()

                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn) ?: "IMG_$id.jpg"
                val mime = cursor.getString(mimeColumn) ?: "image/jpeg"
                val size = cursor.getLong(sizeColumn)
                val dateAdded = cursor.getLong(dateColumn)

                // Tạo Content URI chuẩn theo ID ảnh: content://media/external/images/media/<id>
                val itemUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                resultList.add(
                    MediaImage(
                        id = id,
                        uri = itemUri,
                        displayName = name,
                        mimeType = mime,
                        sizeBytes = size,
                        dateAddedSeconds = dateAdded
                    )
                )
            }
        }
        // Để lỗi quyền/I/O ném ra tới ViewModel xử lý, không bắt nuốt lỗi để tránh báo nhầm thành thư viện trống.

        resultList
    }

    /**
     * Đọc metadata của một URI do Photo Picker cấp quyền tạm thời.
     *
     * ĐẶC ĐIỂM:
     * 1. Xác thực quyền đọc: Thử mở và đóng nhanh `openInputStream(uri)`. Nếu quyền bị thu hồi hoặc
     *    file không còn tồn tại thì ném IOException ngay.
     * 2. Truy vấn [OpenableColumns]: Dùng ContentResolver truy vấn 2 cột tiêu chuẩn của Storage Access Framework
     *    là `DISPLAY_NAME` và `SIZE`.
     * 3. ID = 0L: Nguồn Photo Picker không thuộc MediaStore ID chuẩn của app, nên gán `id = 0L` và
     *    sử dụng chính `uri` làm khóa định danh duy nhất (identity).
     * 4. [dateAddedSeconds]: Gán thời gian hiện tại (`System.currentTimeMillis() / 1000L`) làm thời điểm
     *    ảnh được người dùng thêm vào app trong phiên làm việc này.
     */
    override suspend fun getMediaInfo(uri: Uri): MediaImage = withContext(Dispatchers.IO) {
        currentCoroutineContext().ensureActive()
        var displayName = "selected_photo.jpg"
        var sizeBytes = 0L

        // 1. Kiểm tra xem quyền truy cập URI từ Photo Picker còn hiệu lực để đọc hay không
        contentResolver.openInputStream(uri)?.use { }
            ?: throw IOException("Không thể đọc ảnh đã chọn")

        // 2. Cố gắng đọc tên hiển thị và kích thước qua OpenableColumns
        try {
            contentResolver.query(
                uri, 
                arrayOf(OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE),
                null, 
                null, 
                null
            )?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex >= 0) cursor.getString(nameIndex)?.let { displayName = it }

                    val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                    if (sizeIndex >= 0) sizeBytes = cursor.getLong(sizeIndex)
                }
            }
        } catch (error: Exception) {
            // Không nuốt lỗi hủy Coroutine hoặc lỗi bảo mật
            if (error is CancellationException || error is SecurityException) throw error
            // Một số Content Provider đặc thù không cung cấp cột metadata; tiếp tục dùng giá trị fallback
        }

        currentCoroutineContext().ensureActive()

        // 3. Trả về đối tượng MediaImage hoàn chỉnh với URI nguồn
        MediaImage(
            id = 0L, // ID mặc định cho ảnh từ Picker, phân biệt và định danh bằng uri
            uri = uri,
            displayName = displayName,
            mimeType = contentResolver.getType(uri) ?: "image/jpeg",
            sizeBytes = sizeBytes,
            dateAddedSeconds = System.currentTimeMillis() / 1000L
        )
    }
}