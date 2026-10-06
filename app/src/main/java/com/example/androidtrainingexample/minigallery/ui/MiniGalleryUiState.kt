package com.example.androidtrainingexample.minigallery.ui

import com.example.androidtrainingexample.minigallery.model.MediaImage
import com.example.androidtrainingexample.minigallery.model.PermissionState
import com.example.androidtrainingexample.minigallery.model.SortOrder

/**
 * Đại diện cho trạng thái giao diện (UI State) duy nhất của màn hình MiniGallery.
 * Tuân thủ chặt chẽ mô hình UDF (Unidirectional Data Flow) trong Jetpack Compose:
 * - ViewModel quản lý và phát ra (emit) state mới thông qua StateFlow.
 * - Composable nhận state để render giao diện, đẩy sự kiện người dùng (event) ngược lại ViewModel.
 *
 * QUY TẮC THIẾT KẾ UI STATE:
 * 1. KHÔNG LƯU GIỮ: Context, Activity, Bitmap, hay Composable vào state để tránh rò rỉ bộ nhớ (Memory Leak)
 *    và hỗ trợ tốt việc khôi phục state khi xoay màn hình (Configuration Changes).
 * 2. TÁCH BIỆT DỮ LIỆU GỐC VÀ HIỂN THỊ:
 *    - [allImages]: Dữ liệu nguồn hợp nhất (Single Source of Truth).
 *    - [displayedImages]: Dữ liệu thực tế đang vẽ lên lưới sau khi filter/sort.
 */
data class MiniGalleryUiState(
    /**
     * Toàn bộ danh sách ảnh hợp nhất từ cả 2 nguồn:
     * (ảnh MediaStore có quyền đọc + ảnh người dùng chọn qua Photo Picker).
     * Đây là tập dữ liệu đầy đủ chưa qua lọc tìm kiếm.
     */
    val allImages: List<MediaImage> = emptyList(),

    /**
     * Danh sách ảnh đang hiển thị trực tiếp trên lưới (LazyVerticalGrid).
     * Kết quả này có được sau khi lấy [allImages] áp dụng lọc theo [searchQuery] và sắp xếp theo [sortOrder].
     */
    val displayedImages: List<MediaImage> = emptyList(),

    /** Từ khóa tìm kiếm hiện tại do người dùng nhập vào ô SearchBar */
    val searchQuery: String = "",

    /** Chế độ sắp xếp hiện tại (mặc định: Mới nhất trước) */
    val sortOrder: SortOrder = SortOrder.DATE_DESC,

    /** Cờ báo đang tải dữ liệu ban đầu từ MediaStore (hiển thị CircularProgressIndicator) */
    val isLoading: Boolean = false,

    /**
     * Trạng thái cấp quyền đọc bộ nhớ máy (Denied, GrantedPartial, GrantedFull).
     * Dùng để quyết định có hiển thị PermissionBanner cảnh báo hay không.
     */
    val permissionState: PermissionState = PermissionState.Denied,

    /**
     * Cờ báo đang trong quá trình đọc metadata các ảnh vừa chọn từ Photo Picker.
     * Khi cờ này = true: Vô hiệu hóa (disable) nút "Thêm ảnh" để tránh spam click.
     */
    val isAddingPhotos: Boolean = false,

    /**
     * Thông báo ngắn gửi đến người dùng (ví dụ: lỗi đọc ảnh, thông báo giới hạn 10 ảnh).
     * Được lắng nghe bởi LaunchedEffect trên UI và tự động xóa sau khi hiển thị.
     */
    val userMessage: String? = null
) {
    /**
     * Tổng số ảnh mà app đang quản lý trong bộ nhớ.
     * Dùng getter tính toán trực tiếp từ [allImages], tránh lỗi lệch state nếu lưu biến riêng.
     */
    val totalCount: Int get() = allImages.size

    /**
     * Số ảnh đang được hiển thị sau khi lọc.
     * Dùng getter tính toán trực tiếp từ [displayedImages].
     */
    val displayedCount: Int get() = displayedImages.size
}
