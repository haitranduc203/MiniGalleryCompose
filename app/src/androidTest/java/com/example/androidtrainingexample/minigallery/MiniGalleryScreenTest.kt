package com.example.androidtrainingexample.minigallery

import android.net.Uri
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.androidtrainingexample.minigallery.model.*
import com.example.androidtrainingexample.minigallery.ui.MiniGalleryScreen
import com.example.androidtrainingexample.minigallery.ui.MiniGalleryUiState
import com.example.androidtrainingexample.minigallery.ui.theme.MiniGalleryTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MiniGalleryScreenTest {
    @get:Rule val compose = createComposeRule()

    private fun show(
        state: MiniGalleryUiState,
        onPermission: () -> Unit = {},
        onSearch: (String) -> Unit = {},
        onSort: (SortOrder) -> Unit = {},
        onAdd: () -> Unit = {}
    ) {
        val screenState = androidx.compose.runtime.mutableStateOf(state)
        compose.setContent {
            MiniGalleryTheme {
                MiniGalleryScreen(
                    screenState.value,
                    { query ->
                        screenState.value = screenState.value.copy(searchQuery = query)
                        onSearch(query)
                    },
                    { order ->
                        screenState.value = screenState.value.copy(sortOrder = order)
                        onSort(order)
                    },
                    onPermission, onAdd
                )
            }
        }
    }

    @Test fun deniedPermissionOffersActionAndEmptyState() {
        var requests = 0
        show(MiniGalleryUiState(), onPermission = { requests++ })
        compose.onNodeWithText("Chưa được cấp quyền đọc ảnh").assertIsDisplayed()
        compose.onNodeWithText("Thư viện trống hoặc chưa có ảnh nào").assertIsDisplayed()
        compose.onNodeWithText("Cấp quyền ngay").performClick()
        assertEquals(1, requests)
    }

    @Test fun partialPermissionOffersReselection() {
        var requests = 0
        show(MiniGalleryUiState(permissionState = PermissionState.GrantedPartial(2)), onPermission = { requests++ })
        compose.onNodeWithText("Quản lý / Chọn thêm ảnh").performClick()
        assertEquals(1, requests)
    }

    @Test fun searchAndSortSendEventsToViewModel() {
        var query = ""
        var sort = SortOrder.DATE_DESC
        show(
            MiniGalleryUiState(permissionState = PermissionState.GrantedFull),
            onSearch = { query = it }, onSort = { sort = it }
        )
        compose.onNodeWithText("Tìm kiếm ảnh theo tên…").performTextInput("holiday")
        compose.onNodeWithText("Mới nhất trước").performClick()
        compose.onNodeWithText("Tên (A → Z)").performClick()
        assertEquals("holiday", query)
        assertEquals(SortOrder.NAME_ASC, sort)
    }

    @Test fun emptySearchShowsSearchSpecificMessage() {
        show(MiniGalleryUiState(searchQuery = "missing", permissionState = PermissionState.GrantedFull))
        compose.onNodeWithText("Không tìm thấy ảnh nào khớp với từ khóa").assertIsDisplayed()
        compose.onNodeWithText("Chưa được cấp quyền đọc ảnh").assertDoesNotExist()
    }

    @Test fun detailDialogSurvivesStateRestorationAndCanClose() {
        val image = MediaImage(1, Uri.parse("content://media/external/images/media/1"), "sample.jpg", "image/jpeg", 2048, 1)
        val state = MiniGalleryUiState(allImages = listOf(image), displayedImages = listOf(image), permissionState = PermissionState.GrantedFull)
        val restoration = StateRestorationTester(compose)
        restoration.setContent {
            MiniGalleryTheme { MiniGalleryScreen(state, {}, {}, {}, {}) }
        }
        compose.onNodeWithContentDescription("sample.jpg").performClick()
        compose.onNodeWithText("Chi tiết Metadata ảnh").assertIsDisplayed()
        restoration.emulateSavedInstanceStateRestore()
        compose.onNodeWithText("Chi tiết Metadata ảnh").assertIsDisplayed()
        compose.onNodeWithText("image/jpeg").assertIsDisplayed()
        compose.onNodeWithText("Đóng").performClick()
        compose.onNodeWithText("Chi tiết Metadata ảnh").assertDoesNotExist()
    }

    @Test fun pickerPhotosRenderWithoutPreviewOrSaveDialogWhenPermissionDenied() {
        val image = MediaImage(0, Uri.parse("content://test/picked"), "picked.jpg", "image/jpeg", 100, 1)
        show(MiniGalleryUiState(allImages = listOf(image), displayedImages = listOf(image)))
        compose.onNodeWithContentDescription("picked.jpg").assertIsDisplayed()
        compose.onNodeWithText("Xem trước và lưu bản sao").assertDoesNotExist()
        compose.onNodeWithText("Lưu bản sao").assertDoesNotExist()
    }

    @Test fun addButtonOpensPickerCallback() {
        var requests = 0
        show(MiniGalleryUiState(), onAdd = { requests++ })
        compose.onNodeWithText("Thêm ảnh (tối đa 10)").performClick()
        assertEquals(1, requests)
    }

    @Test fun addButtonIsDisabledWhileReadingSelections() {
        show(MiniGalleryUiState(isAddingPhotos = true))
        compose.onNodeWithText("Thêm ảnh (tối đa 10)").assertIsNotEnabled()
    }
}