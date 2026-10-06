package com.example.androidtrainingexample.minigallery.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.example.androidtrainingexample.minigallery.model.PermissionState

/**
 * Lớp tiện ích quản lý và kiểm tra quyền truy cập bộ nhớ ảnh thích ứng theo từng phiên bản Android (API 29 - 34+).
 *
 * CHIẾN LƯỢC PHÂN QUYỀN (PERMISSION STRATEGY):
 * 1. API 34+ (Android 14 - UpsideDownCake):
 *    - Hỗ trợ cơ chế "Selected Photos Access" (truy cập một phần).
 *    - Cần khai báo và yêu cầu cả 2 quyền:
 *      + `READ_MEDIA_IMAGES`: Cấp quyền đọc toàn bộ ảnh.
 *      + `READ_MEDIA_VISUAL_USER_SELECTED`: Người dùng chỉ cấp quyền cho các ảnh đã chọn.
 * 2. API 33 (Android 13 - Tiramisu):
 *    - Google tách quyền Storage thành các loại media chuyên biệt: sử dụng `READ_MEDIA_IMAGES`.
 * 3. API 29 - 32 (Android 10 - 12):
 *    - Sử dụng quyền truyền thống `READ_EXTERNAL_STORAGE`.
 */
object PermissionHelper {

    /**
     * Trả về danh sách các quyền cần yêu cầu runtime từ người dùng tùy theo phiên bản hệ điều hành máy đang chạy.
     * Danh sách này sẽ được truyền trực tiếp vào `ActivityResultContracts.RequestMultiplePermissions()`.
     */
    fun getRequiredPermissions(): Array<String> {
        return when {
            // Android 14+ (API 34+): Yêu cầu cả quyền toàn phần lẫn quyền từng phần
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> {
                arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
                )
            }
            // Android 13 (API 33): Chỉ cần quyền ảnh chuyên biệt
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
            }
            // Android 10-12 (API 29-32): Dùng quyền bộ nhớ ngoài truyền thống
            else -> {
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    /**
     * Kiểm tra trạng thái cấp quyền hiện tại của app để cập nhật UI State tương ứng.
     *
     * @param context Context ứng dụng để gọi `ContextCompat.checkSelfPermission`.
     * @param currentKnownImageCount Số lượng ảnh đã biết (dùng để gán vào `GrantedPartial`).
     * @return [PermissionState] gồm [PermissionState.GrantedFull], [PermissionState.GrantedPartial], hoặc [PermissionState.Denied].
     */
    fun checkPermissionState(context: Context, currentKnownImageCount: Int = 0): PermissionState {
        return when {
            // Android 14+ (API 34+): Phải kiểm tra quyền Full TRƯỚC quyền Partial
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> {
                val hasFull = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED

                val hasPartial = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
                ) == PackageManager.PERMISSION_GRANTED

                when {
                    hasFull -> PermissionState.GrantedFull
                    hasPartial -> PermissionState.GrantedPartial(currentKnownImageCount)
                    else -> PermissionState.Denied
                }
            }

            // Android 13 (API 33): Kiểm tra quyền đọc ảnh
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                val hasImages = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED

                if (hasImages) PermissionState.GrantedFull else PermissionState.Denied
            }

            // Android 10-12 (API 29-32): Kiểm tra quyền đọc bộ nhớ ngoài
            else -> {
                val hasStorage = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED

                if (hasStorage) PermissionState.GrantedFull else PermissionState.Denied
            }
        }
    }
}
