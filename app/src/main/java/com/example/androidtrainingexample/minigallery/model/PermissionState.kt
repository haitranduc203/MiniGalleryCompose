package com.example.androidtrainingexample.minigallery.model

/**
 * Biểu diễn trạng thái cấp quyền đọc ảnh trên các phiên bản Android (API 29-34+).
 *
 * TẠI SAO DÙNG `sealed interface`?
 * - Giúp Kotlin compiler kiểm tra tính vét cạn (exhaustive) trong các biểu thức `when (permissionState)`.
 * - Không cần nhánh `else`, tránh bỏ sót trường hợp khi thêm trạng thái mới và đảm bảo an toàn kiểu dữ liệu.
 */
sealed interface PermissionState {
    /**
     * Người dùng chưa cấp hoặc đã từ chối quyền đọc thư viện ảnh.
     *
     * HÀNH VI ỨNG DỤNG:
     * - Hiển thị PermissionBanner ở đầu màn hình nhắc người dùng cấp quyền.
     * - VẪN CHO PHÉP người dùng nhấn nút "Thêm ảnh" vì Photo Picker của hệ thống hoạt động độc lập
     *   và không yêu cầu quyền đọc toàn bộ thư viện.
     */
    data object Denied : PermissionState

    /**
     * Người dùng chỉ cấp quyền truy cập MỘT PHẦN ảnh (Selected Photos).
     *
     * ĐẶC ĐIỂM KỸ THUẬT:
     * - Xuất hiện từ Android 14 (API 34) thông qua quyền `READ_MEDIA_VISUAL_USER_SELECTED`.
     * - Người dùng chỉ cho phép app đọc các ảnh mà họ đã chủ động tích chọn trong hộp thoại hệ thống.
     * - [selectedCount]: Số lượng ảnh hiện tại mà app được phép truy cập.
     */
    data class GrantedPartial(
        val selectedCount: Int = 0
    ) : PermissionState

    /**
     * Người dùng đã cấp TOÀN QUYỀN đọc thư viện ảnh.
     *
     * ĐẶC ĐIỂM KỸ THUẬT:
     * - Android 13+ (API 33+): Được cấp quyền `android.permission.READ_MEDIA_IMAGES`.
     * - Android 10-12 (API 29-32): Được cấp quyền `android.permission.READ_EXTERNAL_STORAGE`.
     * - App có thể tự do truy vấn toàn bộ ảnh công khai thông qua ContentResolver / MediaStore.
     */
    data object GrantedFull : PermissionState
}
