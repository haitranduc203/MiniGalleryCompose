package com.example.androidtrainingexample.minigallery.model

import android.net.Uri
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.ln
import kotlin.math.pow

/**
 * Đại diện cho thông tin một ảnh trong ứng dụng, có thể đến từ:
 * 1. MediaStore hệ thống (truy vấn qua ContentResolver).
 * 2. Photo Picker (người dùng chủ động chọn qua ActivityResultLauncher).
 *
 * KIẾN THỨC CỐT LÕI (ANDROID STORAGE & JETPACK COMPOSE):
 * -------------------------------------------------------------------------------------------------
 * 1. VÌ SAO DÙNG Uri THAY VÌ FILE PATH (_data)?
 *    - Từ Android 10 (API 29) với cơ chế Scoped Storage, việc truy cập trực tiếp bằng file path
 *      ("/storage/emulated/0/...") bị hạn chế nghiêm ngặt; cột MediaStore.Images.Media.DATA bị deprecated.
 *    - Nguồn Photo Picker chỉ trả về Content URI (`content://...`). Dùng [Uri] trừu tượng hóa nguồn dữ liệu,
 *      đảm bảo tương thích hoàn toàn với ContentResolver, Security permissions và thư viện Coil AsyncImage.
 *
 * 2. VÌ SAO URI LÀ KEY TỐT HƠN id=0 CHO NGUỒN PHOTO PICKER?
 *    - Ảnh từ Photo Picker không có ID chuẩn trong MediaStore hệ thống nên gán fallback `id = 0L`.
 *    - Nếu dùng `id` làm key hiển thị trong Compose LazyVerticalGrid hoặc để loại trùng (distinctBy),
 *      toàn bộ ảnh từ Picker sẽ bị trùng key (id=0), dẫn đến crash Compose hoặc bị ghi đè chỉ còn 1 ảnh.
 *    - Dùng `uri.toString()` làm key đảm bảo tính duy nhất tuyệt đối cho mọi ảnh từ cả 2 nguồn.
 *
 * 3. VÌ SAO dateAddedSeconds PHẢI NHÂN 1000L KHI TẠO Date?
 *    - Cột MediaStore.Images.Media.DATE_ADDED lưu thời gian theo đơn vị GIÂY (seconds Unix timestamp).
 *    - Constructor của java.util.Date(timeMs) nhận thời gian theo đơn vị MILI-GIÂY (milliseconds).
 *    - Bắt buộc phải nhân 1000L (`dateAddedSeconds * 1000L`) để tránh sai lệch hiển thị về năm 1970.
 * -------------------------------------------------------------------------------------------------
 */
data class MediaImage(
    /** ID trong MediaStore. Với ảnh từ Photo Picker, giá trị này mặc định là 0L */
    val id: Long,

    /** Địa chỉ Content URI trỏ tới ảnh (ví dụ: content://media/external/images/media/1234) */
    val uri: Uri,

    /** Tên hiển thị của file ảnh kèm phần mở rộng (ví dụ: IMG_20261005.jpg) */
    val displayName: String,

    /** Định dạng MIME type của ảnh (ví dụ: image/jpeg, image/png) */
    val mimeType: String,

    /** Dung lượng file tính bằng Byte thô (Long) */
    val sizeBytes: Long,

    /** Thời điểm thêm ảnh tính bằng GIÂY (Unix timestamp). Cần * 1000L khi chuyển sang Date */
    val dateAddedSeconds: Long
) {
    /**
     * Định dạng dung lượng sang dạng người dùng dễ đọc (B, KB, MB, GB).
     *
     * Giải thuật:
     * - Sử dụng logarit tự nhiên: log1024(sizeBytes) = ln(sizeBytes) / ln(1024).
     * - Giá trị nguyên phần này tương ứng với chỉ số của mảng ["B", "KB", "MB", "GB"].
     * - Sau đó chia dung lượng cho 1024^digitGroups để lấy phần thập phân làm tròn 1 chữ số.
     */
    val formattedSize: String
        get() {
            if (sizeBytes <= 0) return "0 B"
            val units = arrayOf("B", "KB", "MB", "GB")
            val digitGroups = (ln(sizeBytes.toDouble()) / ln(1024.0)).toInt().coerceIn(0, units.size - 1)
            return String.format(Locale.getDefault(), "%.1f %s", sizeBytes / 1024.0.pow(digitGroups.toDouble()), units[digitGroups])
        }

    /**
     * Định dạng ngày thêm sang dạng ngày giờ đọc được (dd/MM/yyyy HH:mm).
     * Lưu ý: Phải nhân 1000L vì dateAddedSeconds tính bằng giây, còn Date() nhận mili-giây.
     */
    val formattedDate: String
        get() {
            if (dateAddedSeconds <= 0) return "Không xác định"
            val date = Date(dateAddedSeconds * 1000L)
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            return sdf.format(date)
        }
}
