package com.example.androidtrainingexample.minigallery.model

/**
 * Các chế độ sắp xếp danh sách ảnh trong ứng dụng.
 *
 * THIẾT KẾ:
 * - [label]: Nhãn tiếng Việt để hiển thị trực tiếp lên UI (ExposedDropdownMenuBox trong Jetpack Compose)
 *   mà không cần thêm logic ánh xạ (map) rườm rà ở tầng UI.
 * - Các giá trị tương ứng trực tiếp với các hàm mở rộng sắp xếp của Kotlin Collection:
 *   [sortedBy] hoặc [sortedByDescending].
 */
enum class SortOrder(val label: String) {
    /** Mới nhất trước: Áp dụng `sortedByDescending { it.dateAddedSeconds }` (Mặc định) */
    DATE_DESC("Mới nhất trước"),

    /** Cũ nhất trước: Áp dụng `sortedBy { it.dateAddedSeconds }` */
    DATE_ASC("Cũ nhất trước"),

    /** Tên từ A đến Z: Áp dụng `sortedBy { it.displayName.lowercase() }` */
    NAME_ASC("Tên (A → Z)"),

    /** Tên từ Z về A: Áp dụng `sortedByDescending { it.displayName.lowercase() }` */
    NAME_DESC("Tên (Z → A)")
}
