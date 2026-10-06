package com.example.androidtrainingexample.minigallery.data

import android.net.Uri
import com.example.androidtrainingexample.minigallery.model.MediaImage
import kotlinx.coroutines.flow.Flow

/**
 * Interface trừu tượng hóa tầng truy xuất dữ liệu (Data Access Layer) của ứng dụng.
 *
 * MỤC ĐÍCH THIẾT KẾ:
 * 1. NGUYÊN TẮC TÁCH BIỆT (Separation of Concerns):
 *    - ViewModel chỉ tương tác với interface này mà không cần biết dữ liệu đến từ đâu
 *      (MediaStore, Photo Picker, mạng hay bộ nhớ tạm).
 *    - Tuyệt đối không để ViewModel gọi trực tiếp ContentResolver hoặc câu lệnh truy vấn SQLite/Cursor.
 * 2. HỖ TRỢ TESTABILITY (Kiểm thử đơn vị):
 *    - Cho phép dễ dàng tạo FakeMediaRepository hoặc Mock trong Unit Test trên máy tính (JVM)
 *      mà không cần phụ thuộc vào môi trường Android hay thiết bị thật.
 */
interface MediaRepository {

    /**
     * Luồng Flow quan sát sự thay đổi dữ liệu ảnh trên thiết bị thời gian thực.
     *
     * CƠ CHẾ:
     * - Bọc ContentObserver của Android vào Kotlin Coroutines `callbackFlow`.
     * - Khi có ảnh mới được chụp/tải về hoặc bị xóa khỏi máy, Flow này sẽ tự động phát ra (emit)
     *   danh sách ảnh mới nhất sau khi đã khử rung (debounce).
     * - Tự động hủy đăng ký (unregister) ContentObserver khi Flow bị đóng hoặc Job bị hủy.
     */
    fun observeImages(): Flow<List<MediaImage>>

    /**
     * Truy vấn trực tiếp danh sách ảnh hiện có từ kho ảnh hệ thống (MediaStore).
     *
     * ĐẶC ĐIỂM:
     * - Hàm tạm dừng (suspend function) chạy bất đồng bộ trên luồng IO (Dispatchers.IO).
     * - Chỉ trả về các ảnh công khai và đã ghi hoàn tất (IS_PENDING = 0).
     */
    suspend fun queryImages(): List<MediaImage>

    /**
     * Lấy thông tin metadata cơ bản (tên file, dung lượng, MIME type) của một URI nguồn do Photo Picker trả về.
     *
     * ĐẶC ĐIỂM:
     * - Đọc trực tiếp từ URI được người dùng cấp quyền tạm thời thông qua Photo Picker.
     * - Không sao chép (copy) file, không tạo bản sao vào bộ nhớ ứng dụng.
     * - Giữ nguyên URI gốc làm định danh duy nhất.
     */
    suspend fun getMediaInfo(uri: Uri): MediaImage
}
