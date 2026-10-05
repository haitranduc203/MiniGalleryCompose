package com.example.androidtrainingexample.minigallery.data

import android.net.Uri
import com.example.androidtrainingexample.minigallery.model.MediaImage
import kotlinx.coroutines.flow.Flow

/**
 * Đọc ảnh từ MediaStore hoặc URI nguồn do Photo Picker trả về.
 */
interface MediaRepository {

    /**
     * Luồng Flow quan sát sự thay đổi dữ liệu ảnh trên thiết bị qua ContentObserver.
     * Tự động unregister khi kết thúc hoặc huỷ bỏ.
     */
    fun observeImages(): Flow<List<MediaImage>>

    /**
     * Truy vấn trực tiếp danh sách ảnh hiện có từ MediaStore trên Dispatchers.IO.
     */
    suspend fun queryImages(): List<MediaImage>

    /**
     * Lấy thông tin metadata cơ bản (tên, kích thước) của một Uri được chọn từ Photo Picker.
     */
    suspend fun getMediaInfo(uri: Uri): MediaImage
}
