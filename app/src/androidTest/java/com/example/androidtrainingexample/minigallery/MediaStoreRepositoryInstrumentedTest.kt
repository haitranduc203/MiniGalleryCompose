package com.example.androidtrainingexample.minigallery

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.androidtrainingexample.minigallery.data.MediaStoreRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

/** Temporary app-owned rows only; source loading must never create copies. */
@RunWith(AndroidJUnit4::class)
class MediaStoreRepositoryInstrumentedTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val resolver = context.contentResolver
    private val repository = MediaStoreRepositoryImpl(resolver, context)
    private val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
    private val cleanup = mutableListOf<Uri>()

    private fun source(): Pair<Uri, String> {
        val name = "test_${UUID.randomUUID()}.png"
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, name)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MiniGalleryTests")
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }
        val uri = requireNotNull(resolver.insert(collection, values))
        cleanup.add(uri)
        resolver.openOutputStream(uri)!!.use { output ->
            val bitmap = Bitmap.createBitmap(24, 24, Bitmap.Config.ARGB_8888)
            try { bitmap.compress(Bitmap.CompressFormat.PNG, 100, output) }
            finally { bitmap.recycle() }
        }
        resolver.update(uri, ContentValues().apply { put(MediaStore.Images.Media.IS_PENDING, 0) }, null, null)
        return uri to name
    }

    @After fun cleanUp() { cleanup.forEach { resolver.delete(it, null, null) } }

    @Test fun selectedPhotoUsesOriginalUriAndDoesNotCreateOrModifyImages() = runBlocking {
        val (uri, name) = source()
        val before = repository.queryImages().map { it.uri.toString() }.toSet()
        val original = resolver.openInputStream(uri)!!.use { it.readBytes() }
        val image = repository.getMediaInfo(uri)
        assertEquals(uri, image.uri)
        assertEquals(name, image.displayName)
        assertEquals("image/png", image.mimeType)
        assertTrue(image.sizeBytes > 0)
        assertArrayEquals(original, resolver.openInputStream(uri)!!.use { it.readBytes() })
        assertEquals(before, repository.queryImages().map { it.uri.toString() }.toSet())
    }

    @Test fun deletedSourceFailsInsteadOfAddingUnreadablePhoto() = runBlocking {
        val (uri, _) = source()
        resolver.delete(uri, null, null)
        cleanup.remove(uri)
        try {
            repository.getMediaInfo(uri)
            fail("An unreadable source must not be added to the gallery")
        } catch (expected: java.io.IOException) {
            assertNotNull(expected.message)
        }
    }
}