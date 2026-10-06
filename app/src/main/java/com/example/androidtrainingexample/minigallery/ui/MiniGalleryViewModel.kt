package com.example.androidtrainingexample.minigallery.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidtrainingexample.minigallery.data.MediaRepository
import com.example.androidtrainingexample.minigallery.model.MediaImage
import com.example.androidtrainingexample.minigallery.model.PermissionState
import com.example.androidtrainingexample.minigallery.model.SortOrder
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

/**
 * ViewModel trung tâm điều phối toàn bộ luồng dữ liệu (State & Event) của MiniGallery theo mô hình UDF.
 *
 * CHIẾN LƯỢC QUẢN LÝ DỮ LIỆU ĐA NGUỒN (DUAL-SOURCE PATTERN):
 * 1. [deviceImages]: Danh sách ảnh đọc từ MediaStore thiết bị (cần cấp quyền đọc bộ nhớ).
 * 2. [pickedImages]: Danh sách các ảnh người dùng chủ động chọn từ Photo Picker (không cần quyền bộ nhớ).
 * 3. Hợp nhất: Cả hai danh sách được gộp lại trong [publishImages] và loại trùng bằng `it.uri.toString()`.
 *    Điều này đảm bảo: Khi thu hồi quyền đọc máy, các ảnh do người dùng chọn từ Photo Picker vẫn còn nguyên!
 *
 * NGUYÊN TẮC:
 * - Không tạo bản sao file trên đĩa cứng.
 * - Hiển thị ảnh picker trực tiếp lên lưới ngay khi đọc xong metadata, không có bước xác nhận lưu.
 */
class MiniGalleryViewModel(private val repository: MediaRepository) : ViewModel() {

    /** State nội bộ có thể sửa đổi */
    private val _uiState = MutableStateFlow(MiniGalleryUiState())

    /** State công khai chỉ đọc cho Compose UI thu thập (collect) */
    val uiState: StateFlow<MiniGalleryUiState> = _uiState.asStateFlow()

    /** Job quản lý Coroutine lắng nghe Flow từ MediaStore */
    private var observeJob: Job? = null

    /** Bộ nhớ RAM lưu danh sách ảnh lấy từ MediaStore */
    private var deviceImages: List<MediaImage> = emptyList()

    /** Bộ nhớ RAM lưu danh sách ảnh lấy từ Photo Picker */
    private var pickedImages: List<MediaImage> = emptyList()

    /**
     * Cập nhật trạng thái quyền truy cập từ Activity.
     *
     * LOGIC XỬ LÝ:
     * - Nếu được cấp quyền (Full hoặc Partial): Khởi động lắng nghe thư viện ảnh qua [startObservingMedia].
     * - Nếu bị từ chối (Denied): Hủy job lắng nghe, xóa sạch [deviceImages] nhưng GIỮ LẠI [pickedImages],
     *   sau đó phát lại danh sách ảnh để UI chỉ còn hiển thị các ảnh từ Photo Picker.
     */
    fun updatePermissionState(newState: PermissionState) {
        _uiState.update { it.copy(permissionState = newState) }
        when (newState) {
            PermissionState.GrantedFull, is PermissionState.GrantedPartial -> startObservingMedia()
            PermissionState.Denied -> {
                observeJob?.cancel()
                observeJob = null
                deviceImages = emptyList()
                publishImages() // Vẫn giữ lại pickedImages
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    /**
     * Bắt đầu thu thập dữ liệu ảnh thời gian thực từ Repository.
     * Hủy job cũ và chờ hoàn tất (`join()`) trước khi chạy job mới để tránh chạy song song 2 observer.
     */
    private fun startObservingMedia() {
        val previousJob = observeJob
        previousJob?.cancel()
        observeJob = viewModelScope.launch {
            previousJob?.join()
            _uiState.update { it.copy(isLoading = true) }
            try {
                repository.observeImages().collect { images ->
                    deviceImages = images
                    publishImages()
                    _uiState.update { current ->
                        current.copy(
                            isLoading = false,
                            permissionState = if (current.permissionState is PermissionState.GrantedPartial) {
                                PermissionState.GrantedPartial(images.size)
                            } else current.permissionState
                        )
                    }
                }
            } catch (error: Exception) {
                // Tuyệt đối không nuốt CancellationException để coroutine có thể hủy đúng cách
                if (error is CancellationException) throw error
                _uiState.update {
                    it.copy(isLoading = false, userMessage = "Lỗi khi đọc thư viện ảnh: ${error.localizedMessage}")
                }
            }
        }
    }

    /**
     * Hợp nhất 2 nguồn ảnh và đẩy ra UI State.
     *
     * @param clearSearch Nếu là true, tự động xóa từ khóa tìm kiếm cũ để ảnh mới xuất hiện ngay trên lưới.
     */
    private fun publishImages(clearSearch: Boolean = false) {
        // Gộp 2 danh sách và loại bỏ trùng lặp dựa trên chuỗi URI độc nhất
        val images = (pickedImages + deviceImages).distinctBy { it.uri.toString() }
        _uiState.update { current ->
            val query = if (clearSearch) "" else current.searchQuery
            current.copy(
                allImages = images,
                searchQuery = query,
                displayedImages = applyFilterAndSort(images, query, current.sortOrder)
            )
        }
    }

    /**
     * Cập nhật từ khóa tìm kiếm và lọc danh sách ảnh hiển thị tương ứng.
     */
    fun setSearchQuery(query: String) {
        _uiState.update { current ->
            current.copy(
                searchQuery = query,
                displayedImages = applyFilterAndSort(current.allImages, query, current.sortOrder)
            )
        }
    }

    /**
     * Thay đổi thứ tự sắp xếp và áp dụng ngay lên danh sách ảnh hiển thị.
     */
    fun setSortOrder(order: SortOrder) {
        _uiState.update { current ->
            current.copy(
                sortOrder = order,
                displayedImages = applyFilterAndSort(current.allImages, current.searchQuery, order)
            )
        }
    }

    /**
     * Xử lý danh sách URI người dùng vừa chọn từ Photo Picker của hệ thống.
     *
     * CÁC BƯỚC THỰC HIỆN:
     * 1. Kiểm tra danh sách rỗng hoặc app đang bận xử lý đợt trước (`isAddingPhotos`).
     * 2. Bật cờ `isAddingPhotos = true` để khóa nút Thêm trên UI.
     * 3. Giới hạn tối đa 10 ảnh (`take(10)`) và loại trừ trùng lặp URI.
     * 4. Lặp qua từng URI:
     *    - Đọc metadata qua `repository.getMediaInfo(uri)`.
     *    - Đưa vào `pickedImages` và gọi `publishImages(clearSearch = true)` để ảnh hiện ngay.
     *    - Dùng `try/catch` riêng cho từng ảnh: nếu 1 ảnh lỗi thì các ảnh khác vẫn đọc bình thường.
     * 5. Khối `finally`: Luôn đảm bảo `isAddingPhotos = false` để mở khóa nút Thêm.
     */
    fun onPhotosSelected(uris: List<Uri>) {
        if (uris.isEmpty() || _uiState.value.isAddingPhotos) return
        _uiState.update { it.copy(isAddingPhotos = true) }
        viewModelScope.launch {
            var failedCount = 0
            var lastError: String? = null
            try {
                for (uri in uris.take(10).distinctBy { it.toString() }) {
                    try {
                        val image = repository.getMediaInfo(uri)
                        pickedImages = (pickedImages + image).distinctBy { it.uri.toString() }
                        // Hiển thị ngay ảnh đọc thành công và xóa bộ lọc tìm kiếm cũ
                        publishImages(clearSearch = true)
                    } catch (error: Exception) {
                        if (error is CancellationException) throw error
                        failedCount++
                        lastError = error.localizedMessage
                    }
                }
                _uiState.update {
                    it.copy(userMessage = when {
                        failedCount > 0 -> "Không thể đọc $failedCount ảnh đã chọn: $lastError"
                        uris.size > 10 -> "Đã giới hạn chọn 10 ảnh đầu tiên."
                        else -> null
                    })
                }
            } finally {
                // Luôn mở khóa cờ isAddingPhotos dù thành công hay xảy ra ngoại lệ
                _uiState.update { it.copy(isAddingPhotos = false) }
            }
        }
    }

    /**
     * Xóa thông báo lỗi/thông tin sau khi UI đã hiển thị (tiêu thụ sự kiện).
     */
    fun clearUserMessage() {
        _uiState.update { it.copy(userMessage = null) }
    }

    /**
     * Hàm thuần túy (pure function) thực hiện lọc theo tên và sắp xếp danh sách ảnh.
     */
    private fun applyFilterAndSort(list: List<MediaImage>, query: String, order: SortOrder): List<MediaImage> {
        val trimmedQuery = query.trim()
        val filtered = if (trimmedQuery.isEmpty()) list else {
            list.filter { it.displayName.contains(trimmedQuery, ignoreCase = true) }
        }
        return when (order) {
            SortOrder.DATE_DESC -> filtered.sortedByDescending { it.dateAddedSeconds }
            SortOrder.DATE_ASC -> filtered.sortedBy { it.dateAddedSeconds }
            SortOrder.NAME_ASC -> filtered.sortedBy { it.displayName.lowercase(Locale.getDefault()) }
            SortOrder.NAME_DESC -> filtered.sortedByDescending { it.displayName.lowercase(Locale.getDefault()) }
        }
    }

    /**
     * Factory khởi tạo ViewModel kèm dependency [MediaRepository].
     */
    class Factory(private val repository: MediaRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MiniGalleryViewModel::class.java)) {
                return MiniGalleryViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}