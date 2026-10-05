package com.example.androidtrainingexample.minigallery

import android.net.Uri
import com.example.androidtrainingexample.minigallery.data.MediaRepository
import com.example.androidtrainingexample.minigallery.model.MediaImage
import com.example.androidtrainingexample.minigallery.model.PermissionState
import com.example.androidtrainingexample.minigallery.model.SortOrder
import com.example.androidtrainingexample.minigallery.ui.MiniGalleryViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

private class FakeMediaRepository : MediaRepository {
    val imagesFlow = MutableSharedFlow<List<MediaImage>>(replay = 1)
    val failedUris = mutableSetOf<String>()
    var cancelMetadata = false
    var observeCount = 0
    override fun observeImages(): Flow<List<MediaImage>> {
        observeCount++
        return imagesFlow
    }
    override suspend fun queryImages() = imagesFlow.replayCache.firstOrNull() ?: emptyList()
    override suspend fun getMediaInfo(uri: Uri): MediaImage {
        if (cancelMetadata) throw CancellationException("Cancelled metadata")
        if (uri.toString() in failedUris) throw SecurityException("Quyền ảnh đã bị thu hồi")
        return MediaImage(0, uri, "picked_${uri.lastPathSegment}.jpg", "image/jpeg", 1024, 5000)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MiniGalleryViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var repository: FakeMediaRepository
    private lateinit var viewModel: MiniGalleryViewModel

    @Before fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = FakeMediaRepository()
        viewModel = MiniGalleryViewModel(repository)
    }
    @After fun tearDown() { Dispatchers.resetMain() }

    private fun uri(id: Int): Uri = mock<Uri>().also {
        whenever(it.toString()).thenReturn("content://test/$id")
        whenever(it.lastPathSegment).thenReturn(id.toString())
    }
    private fun image(id: Int, name: String, date: Long) = MediaImage(id.toLong(), uri(id), name, "image/jpeg", 1024, date)

    @Test fun `chon anh hien ngay trong luoi khi chua co quyen thu vien`() = runTest {
        val source = uri(1)
        viewModel.onPhotosSelected(listOf(source))
        advanceUntilIdle()
        assertEquals(PermissionState.Denied, viewModel.uiState.value.permissionState)
        assertEquals(source, viewModel.uiState.value.displayedImages.single().uri)
        assertFalse(viewModel.uiState.value.isAddingPhotos)
    }

    @Test fun `chon hon 10 anh chi doc 10 anh dau`() = runTest {
        viewModel.onPhotosSelected(List(15) { uri(it) })
        advanceUntilIdle()
        assertEquals(10, viewModel.uiState.value.totalCount)
        assertTrue(viewModel.uiState.value.userMessage?.contains("10 ảnh") == true)
    }

    @Test fun `chon lai cung uri khong tao o anh trung`() = runTest {
        val source = uri(1)
        viewModel.onPhotosSelected(listOf(source, source))
        advanceUntilIdle()
        viewModel.onPhotosSelected(listOf(uri(1)))
        advanceUntilIdle()
        assertEquals(1, viewModel.uiState.value.totalCount)
    }

    @Test fun `them nhieu lan giu cac anh da chon`() = runTest {
        viewModel.onPhotosSelected(listOf(uri(1)))
        advanceUntilIdle()
        viewModel.onPhotosSelected(listOf(uri(2)))
        advanceUntilIdle()
        assertEquals(2, viewModel.uiState.value.totalCount)
    }

    @Test fun `refresh quyen denied khong xoa anh picker`() = runTest {
        viewModel.onPhotosSelected(listOf(uri(1)))
        advanceUntilIdle()
        viewModel.updatePermissionState(PermissionState.Denied)
        assertEquals(1, viewModel.uiState.value.displayedCount)
    }

    @Test fun `thu hoi quyen chi xoa anh thu vien giu anh picker`() = runTest {
        viewModel.updatePermissionState(PermissionState.GrantedFull)
        repository.imagesFlow.emit(listOf(image(2, "device.jpg", 100)))
        advanceUntilIdle()
        viewModel.onPhotosSelected(listOf(uri(1)))
        advanceUntilIdle()
        assertEquals(2, viewModel.uiState.value.totalCount)
        viewModel.updatePermissionState(PermissionState.Denied)
        advanceUntilIdle()
        assertEquals("picked_1.jpg", viewModel.uiState.value.allImages.single().displayName)
    }

    @Test fun `phat media moi khong ghi de cac anh picker`() = runTest {
        viewModel.onPhotosSelected(listOf(uri(1)))
        advanceUntilIdle()
        viewModel.updatePermissionState(PermissionState.GrantedFull)
        repository.imagesFlow.emit(listOf(image(2, "device.jpg", 100)))
        advanceUntilIdle()
        repository.imagesFlow.emit(listOf(image(3, "new.jpg", 200)))
        advanceUntilIdle()
        assertEquals(setOf("picked_1.jpg", "new.jpg"), viewModel.uiState.value.allImages.map { it.displayName }.toSet())
        viewModel.updatePermissionState(PermissionState.Denied)
    }

    @Test fun `them anh xoa tu khoa cu de anh moi hien thi`() = runTest {
        viewModel.setSearchQuery("missing")
        viewModel.onPhotosSelected(listOf(uri(1)))
        advanceUntilIdle()
        assertEquals("", viewModel.uiState.value.searchQuery)
        assertEquals(1, viewModel.uiState.value.displayedCount)
        viewModel.setSearchQuery("PICKED")
        assertEquals(1, viewModel.uiState.value.displayedCount)
    }

    @Test fun `sap xep anh thu vien theo ngay va ten`() = runTest {
        viewModel.updatePermissionState(PermissionState.GrantedFull)
        repository.imagesFlow.emit(listOf(image(1, "b.jpg", 100), image(2, "a.jpg", 300), image(3, "c.jpg", 200)))
        advanceUntilIdle()
        assertEquals(listOf("a.jpg", "c.jpg", "b.jpg"), viewModel.uiState.value.displayedImages.map { it.displayName })
        viewModel.setSortOrder(SortOrder.DATE_ASC)
        assertEquals(listOf("b.jpg", "c.jpg", "a.jpg"), viewModel.uiState.value.displayedImages.map { it.displayName })
        viewModel.setSortOrder(SortOrder.NAME_ASC)
        assertEquals(listOf("a.jpg", "b.jpg", "c.jpg"), viewModel.uiState.value.displayedImages.map { it.displayName })
        viewModel.setSortOrder(SortOrder.NAME_DESC)
        assertEquals(listOf("c.jpg", "b.jpg", "a.jpg"), viewModel.uiState.value.displayedImages.map { it.displayName })
        viewModel.updatePermissionState(PermissionState.Denied)
    }

    @Test fun `mot uri loi van hien thi cac uri doc duoc`() = runTest {
        repository.failedUris.add("content://test/1")
        viewModel.onPhotosSelected(listOf(uri(1), uri(2)))
        advanceUntilIdle()
        assertEquals("picked_2.jpg", viewModel.uiState.value.allImages.single().displayName)
        assertTrue(viewModel.uiState.value.userMessage?.contains("1 ảnh") == true)
        assertFalse(viewModel.uiState.value.isAddingPhotos)
    }

    @Test fun `huy doc metadata mo khoa nut them`() = runTest {
        repository.cancelMetadata = true
        viewModel.onPhotosSelected(listOf(uri(1)))
        advanceUntilIdle()
        assertFalse(viewModel.uiState.value.isAddingPhotos)
        assertTrue(viewModel.uiState.value.allImages.isEmpty())
    }

    @Test fun `quay lai cung quyen van query lai`() = runTest {
        viewModel.updatePermissionState(PermissionState.GrantedFull)
        advanceUntilIdle()
        viewModel.updatePermissionState(PermissionState.GrantedFull)
        advanceUntilIdle()
        assertEquals(2, repository.observeCount)
        viewModel.updatePermissionState(PermissionState.Denied)
    }
}