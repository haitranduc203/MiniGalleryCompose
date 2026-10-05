# Phụ lục mã nguồn MiniGalleryCompose

Snapshot ngày 05/10/2026 của phiên bản chọn ảnh và hiển thị trực tiếp. Code chạy thực tế ở các file được liên kết; khi thay implementation, cần cập nhật snapshot này. Dùng cùng [hướng dẫn viết lại](REBUILD_GUIDE_VI.md) và [giải thích code](CODE_WALKTHROUGH_VI.md).

Không bao gồm local.properties, dữ liệu IDE, build cache, APK, keystore hoặc file binary của wrapper/launcher. Giữ resource binary và wrapper từ skeleton dự án. Các ví dụ ở tài liệu giải thích có thể rút gọn; nội dung các block dưới đây là nguyên văn file tương ứng.

## Danh sách file

- [settings.gradle.kts](../settings.gradle.kts)
- [build.gradle.kts](../build.gradle.kts)
- [app/build.gradle.kts](../app/build.gradle.kts)
- [gradle/libs.versions.toml](../gradle/libs.versions.toml)
- [gradle.properties](../gradle.properties)
- [gradle/wrapper/gradle-wrapper.properties](../gradle/wrapper/gradle-wrapper.properties)
- [app/src/main/AndroidManifest.xml](../app/src/main/AndroidManifest.xml)
- [app/src/main/java/com/example/androidtrainingexample/MainActivity.kt](../app/src/main/java/com/example/androidtrainingexample/MainActivity.kt)
- [app/src/main/java/com/example/androidtrainingexample/minigallery/data/MediaRepository.kt](../app/src/main/java/com/example/androidtrainingexample/minigallery/data/MediaRepository.kt)
- [app/src/main/java/com/example/androidtrainingexample/minigallery/data/MediaStoreRepositoryImpl.kt](../app/src/main/java/com/example/androidtrainingexample/minigallery/data/MediaStoreRepositoryImpl.kt)
- [app/src/main/java/com/example/androidtrainingexample/minigallery/model/MediaImage.kt](../app/src/main/java/com/example/androidtrainingexample/minigallery/model/MediaImage.kt)
- [app/src/main/java/com/example/androidtrainingexample/minigallery/model/PermissionState.kt](../app/src/main/java/com/example/androidtrainingexample/minigallery/model/PermissionState.kt)
- [app/src/main/java/com/example/androidtrainingexample/minigallery/model/SortOrder.kt](../app/src/main/java/com/example/androidtrainingexample/minigallery/model/SortOrder.kt)
- [app/src/main/java/com/example/androidtrainingexample/minigallery/ui/MiniGalleryScreen.kt](../app/src/main/java/com/example/androidtrainingexample/minigallery/ui/MiniGalleryScreen.kt)
- [app/src/main/java/com/example/androidtrainingexample/minigallery/ui/MiniGalleryUiState.kt](../app/src/main/java/com/example/androidtrainingexample/minigallery/ui/MiniGalleryUiState.kt)
- [app/src/main/java/com/example/androidtrainingexample/minigallery/ui/MiniGalleryViewModel.kt](../app/src/main/java/com/example/androidtrainingexample/minigallery/ui/MiniGalleryViewModel.kt)
- [app/src/main/java/com/example/androidtrainingexample/minigallery/ui/theme/MiniGalleryTheme.kt](../app/src/main/java/com/example/androidtrainingexample/minigallery/ui/theme/MiniGalleryTheme.kt)
- [app/src/main/java/com/example/androidtrainingexample/minigallery/util/PermissionHelper.kt](../app/src/main/java/com/example/androidtrainingexample/minigallery/util/PermissionHelper.kt)
- [app/src/test/java/com/example/androidtrainingexample/ExampleUnitTest.kt](../app/src/test/java/com/example/androidtrainingexample/ExampleUnitTest.kt)
- [app/src/test/java/com/example/androidtrainingexample/minigallery/MediaRepositoryBehaviorTest.kt](../app/src/test/java/com/example/androidtrainingexample/minigallery/MediaRepositoryBehaviorTest.kt)
- [app/src/test/java/com/example/androidtrainingexample/minigallery/MediaStoreQueryTest.kt](../app/src/test/java/com/example/androidtrainingexample/minigallery/MediaStoreQueryTest.kt)
- [app/src/test/java/com/example/androidtrainingexample/minigallery/MiniGalleryViewModelTest.kt](../app/src/test/java/com/example/androidtrainingexample/minigallery/MiniGalleryViewModelTest.kt)
- [app/src/androidTest/java/com/example/androidtrainingexample/ExampleInstrumentedTest.kt](../app/src/androidTest/java/com/example/androidtrainingexample/ExampleInstrumentedTest.kt)
- [app/src/androidTest/java/com/example/androidtrainingexample/minigallery/MediaStoreRepositoryInstrumentedTest.kt](../app/src/androidTest/java/com/example/androidtrainingexample/minigallery/MediaStoreRepositoryInstrumentedTest.kt)
- [app/src/androidTest/java/com/example/androidtrainingexample/minigallery/MiniGalleryScreenTest.kt](../app/src/androidTest/java/com/example/androidtrainingexample/minigallery/MiniGalleryScreenTest.kt)
- [app/src/main/res/drawable/ic_add.xml](../app/src/main/res/drawable/ic_add.xml)
- [app/src/main/res/drawable/ic_check_circle.xml](../app/src/main/res/drawable/ic_check_circle.xml)
- [app/src/main/res/drawable/ic_close.xml](../app/src/main/res/drawable/ic_close.xml)
- [app/src/main/res/drawable/ic_error_circle.xml](../app/src/main/res/drawable/ic_error_circle.xml)
- [app/src/main/res/drawable/ic_image.xml](../app/src/main/res/drawable/ic_image.xml)
- [app/src/main/res/drawable/ic_launcher_background.xml](../app/src/main/res/drawable/ic_launcher_background.xml)
- [app/src/main/res/drawable/ic_launcher_foreground.xml](../app/src/main/res/drawable/ic_launcher_foreground.xml)
- [app/src/main/res/drawable/ic_search.xml](../app/src/main/res/drawable/ic_search.xml)
- [app/src/main/res/drawable/ic_sort.xml](../app/src/main/res/drawable/ic_sort.xml)
- [app/src/main/res/drawable/ic_warning.xml](../app/src/main/res/drawable/ic_warning.xml)
- [app/src/main/res/mipmap-anydpi/ic_launcher_round.xml](../app/src/main/res/mipmap-anydpi/ic_launcher_round.xml)
- [app/src/main/res/mipmap-anydpi/ic_launcher.xml](../app/src/main/res/mipmap-anydpi/ic_launcher.xml)
- [app/src/main/res/values-night/themes.xml](../app/src/main/res/values-night/themes.xml)
- [app/src/main/res/values/colors.xml](../app/src/main/res/values/colors.xml)
- [app/src/main/res/values/strings.xml](../app/src/main/res/values/strings.xml)
- [app/src/main/res/values/themes.xml](../app/src/main/res/values/themes.xml)
- [app/src/main/res/xml/backup_rules.xml](../app/src/main/res/xml/backup_rules.xml)
- [app/src/main/res/xml/data_extraction_rules.xml](../app/src/main/res/xml/data_extraction_rules.xml)

## settings.gradle.kts

Source: [settings.gradle.kts](../settings.gradle.kts). SHA256: `8BB12925C5BCD6D7C4C0668455415E07463767E20471152EEC3D9F0DBBBA72C6`.

```kotlin
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MiniGalleryCompose"
include(":app")
```

## build.gradle.kts

Source: [build.gradle.kts](../build.gradle.kts). SHA256: `BE7DBFA1841B18B020F744E203B995D3DAC5709C2A6B8E310FBCFAED0364919A`.

```kotlin
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
}
```

## app/build.gradle.kts

Source: [app/build.gradle.kts](../app/build.gradle.kts). SHA256: `0CD864E7F3451736EC26CF72AB003E9A8CDF60282590037D6206D774328E46F1`.

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))

    }
}

android {
    namespace = "com.example.androidtrainingexample"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.minigallerycompose"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
    lint {
        abortOnError = true
        checkReleaseBuilds = false
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)



    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.coil.compose)
    implementation(libs.kotlinx.coroutines.android)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
```

## gradle/libs.versions.toml

Source: [gradle/libs.versions.toml](../gradle/libs.versions.toml). SHA256: `DFCB1511104F549D938B7399760FBB2A8A225AFBE4A90677B1987946E3522FEB`.

```toml
[versions]
agp = "9.1.1"
kotlin = "2.2.10"
composeBom = "2026.03.01"
coreKtx = "1.15.0"
junit = "4.13.2"
junitVersion = "1.3.0"
espressoCore = "3.7.0"
appcompat = "1.7.1"
material = "1.14.0"
activity = "1.13.0"
constraintlayout = "2.2.1"
lifecycle = "2.8.7"
coil = "2.7.0"
coroutines = "1.8.1"
mockito = "5.14.2"
mockitoKotlin = "5.4.0"

[libraries]
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-compose-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-compose-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
androidx-compose-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
androidx-compose-material3 = { group = "androidx.compose.material3", name = "material3" }
androidx-compose-ui-test-junit4 = { group = "androidx.compose.ui", name = "ui-test-junit4" }
androidx-compose-ui-test-manifest = { group = "androidx.compose.ui", name = "ui-test-manifest" }
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activity" }
androidx-lifecycle-runtime-compose = { group = "androidx.lifecycle", name = "lifecycle-runtime-compose", version.ref = "lifecycle" }
coil-compose = { group = "io.coil-kt", name = "coil-compose", version.ref = "coil" }
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
junit = { group = "junit", name = "junit", version.ref = "junit" }
androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
androidx-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }
androidx-appcompat = { group = "androidx.appcompat", name = "appcompat", version.ref = "appcompat" }
material = { group = "com.google.android.material", name = "material", version.ref = "material" }
androidx-activity = { group = "androidx.activity", name = "activity", version.ref = "activity" }
androidx-activity-ktx = { group = "androidx.activity", name = "activity-ktx", version.ref = "activity" }
androidx-constraintlayout = { group = "androidx.constraintlayout", name = "constraintlayout", version.ref = "constraintlayout" }
androidx-lifecycle-viewmodel-ktx = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-ktx", version.ref = "lifecycle" }
androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycle" }
coil = { group = "io.coil-kt", name = "coil", version.ref = "coil" }
kotlinx-coroutines-android = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-android", version.ref = "coroutines" }
kotlinx-coroutines-test = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-test", version.ref = "coroutines" }
mockito-core = { group = "org.mockito", name = "mockito-core", version.ref = "mockito" }
mockito-kotlin = { group = "org.mockito.kotlin", name = "mockito-kotlin", version.ref = "mockitoKotlin" }

[plugins]
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
android-application = { id = "com.android.application", version.ref = "agp" }
```

## gradle.properties

Source: [gradle.properties](../gradle.properties). SHA256: `79AD0C14972F33EFBC5D2DECD9AED6FBB0ACBA745B04566E62BEC22B290F9662`.

```properties
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
kotlin.code.style=official
```

## gradle/wrapper/gradle-wrapper.properties

Source: [gradle/wrapper/gradle-wrapper.properties](../gradle/wrapper/gradle-wrapper.properties). SHA256: `C348CC904DA4F56DD2716530354BED8F0C808DB30CB17B1EE3B081754CF41F73`.

```properties
#Tue Sep 29 09:59:12 ICT 2026
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionSha256Sum=b266d5ff6b90eada6dc3b20cb090e3731302e553a27c5d3e4df1f0d76beaff06
distributionUrl=https\://services.gradle.org/distributions/gradle-9.3.1-bin.zip
networkTimeout=10000
validateDistributionUrl=true
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
```

## app/src/main/AndroidManifest.xml

Source: [app/src/main/AndroidManifest.xml](../app/src/main/AndroidManifest.xml). SHA256: `E769593690CA2AE9621982B100129247EACA3EB19BF4D573432A12774578753E`.

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <!-- Quyền đọc ảnh cho Android 10-12 (API 29-32) -->
    <uses-permission
        android:name="android.permission.READ_EXTERNAL_STORAGE"
        android:maxSdkVersion="32" />

    <!-- Quyền đọc ảnh cho Android 13+ (API 33+) -->
    <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />

    <!-- Quyền chọn ảnh một phần cho Android 14+ (API 34+) -->
    <uses-permission android:name="android.permission.READ_MEDIA_VISUAL_USER_SELECTED" />

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.AndroidTrainingExample">

        <!-- Kích hoạt Photo Picker trên thiết bị Android 10 có Google Play Services -->
        <service
            android:name="com.google.android.gms.metadata.ModuleDependencies"
            android:enabled="false"
            android:exported="false"
            tools:ignore="MissingClass">
            <intent-filter>
                <action android:name="com.google.android.gms.metadata.MODULE_DEPENDENCIES" />
            </intent-filter>
            <meta-data
                android:name="photopicker_activity:0:required"
                android:value="" />
        </service>

        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

## app/src/main/java/com/example/androidtrainingexample/MainActivity.kt

Source: [app/src/main/java/com/example/androidtrainingexample/MainActivity.kt](../app/src/main/java/com/example/androidtrainingexample/MainActivity.kt). SHA256: `CE134C6B31A4D77D0D902140BAB554CA1C9DDCCD4B91585498566D67A8A0C0F6`.

```kotlin
package com.example.androidtrainingexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.androidtrainingexample.minigallery.data.MediaStoreRepositoryImpl
import com.example.androidtrainingexample.minigallery.ui.MiniGalleryScreen
import com.example.androidtrainingexample.minigallery.ui.MiniGalleryViewModel
import com.example.androidtrainingexample.minigallery.ui.theme.MiniGalleryTheme
import com.example.androidtrainingexample.minigallery.util.PermissionHelper

class MainActivity : ComponentActivity() {
    private val viewModel: MiniGalleryViewModel by viewModels {
        MiniGalleryViewModel.Factory(
            MediaStoreRepositoryImpl(applicationContext.contentResolver, applicationContext)
        )
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { refreshPermission() }

    private val photoPickerLauncher = registerForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(10)
    ) { uris -> viewModel.onPhotosSelected(uris) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniGalleryTheme {
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                LaunchedEffect(state.userMessage) {
                    state.userMessage?.let {
                        Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                        viewModel.clearUserMessage()
                    }
                }
                MiniGalleryScreen(
                    state = state,
                    onSearchQueryChange = viewModel::setSearchQuery,
                    onSortOrderChange = viewModel::setSortOrder,
                    onRequestPermission = {
                        permissionLauncher.launch(PermissionHelper.getRequiredPermissions())
                    },
                    onAddPhotos = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh on return from Settings or Android partial-access photo selection.
        refreshPermission()
    }

    private fun refreshPermission() {
        viewModel.updatePermissionState(
            PermissionHelper.checkPermissionState(this, viewModel.uiState.value.totalCount)
        )
    }
}
```

## app/src/main/java/com/example/androidtrainingexample/minigallery/data/MediaRepository.kt

Source: [app/src/main/java/com/example/androidtrainingexample/minigallery/data/MediaRepository.kt](../app/src/main/java/com/example/androidtrainingexample/minigallery/data/MediaRepository.kt). SHA256: `C7E4BA1623395259D74CE67441C83827B2427972EB7EF44A305B886C579DA634`.

```kotlin
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
```

## app/src/main/java/com/example/androidtrainingexample/minigallery/data/MediaStoreRepositoryImpl.kt

Source: [app/src/main/java/com/example/androidtrainingexample/minigallery/data/MediaStoreRepositoryImpl.kt](../app/src/main/java/com/example/androidtrainingexample/minigallery/data/MediaStoreRepositoryImpl.kt). SHA256: `1363C666761CD48997969FE921D5ABA4B2A8112ABA5067A93CEDB7FD13ACF4E7`.

```kotlin
package com.example.androidtrainingexample.minigallery.data

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.provider.OpenableColumns
import com.example.androidtrainingexample.minigallery.model.MediaImage
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException

/** Đọc ảnh qua ContentResolver, quan sát MediaStore và đọc URI nguồn từ Photo Picker. */
class MediaStoreRepositoryImpl(
    private val contentResolver: ContentResolver,
    private val context: Context
) : MediaRepository {

    @OptIn(kotlinx.coroutines.FlowPreview::class)
    override fun observeImages(): Flow<List<MediaImage>> {
        return callbackFlow {
            val observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
                override fun onChange(selfChange: Boolean, uri: Uri?) {
                    // Nhận thông báo thay đổi từ MediaStore, phát tín hiệu vào Flow
                    trySend(Unit)
                }
            }

            // Đăng ký theo dõi toàn bộ collection ảnh trên bộ nhớ ngoài
            contentResolver.registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                true,
                observer
            )

            // Phát tín hiệu ban đầu để tải dữ liệu ngay khi vừa collect
            trySend(Unit)

            // Hủy đăng ký khi flow bị đóng / scope bị cancel
            awaitClose {
                contentResolver.unregisterContentObserver(observer)
            }
        }
            .conflate() // Bỏ qua các tín hiệu dồn dập
            .debounce(300L) // Gộp các thông báo thay đổi liên tiếp để tránh query MediaStore quá nhiều
            .map { queryImages() }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun queryImages(): List<MediaImage> = withContext(Dispatchers.IO) {
        val resultList = mutableListOf<MediaImage>()
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DATE_ADDED
        )

        // Sắp xếp mặc định: ảnh mới thêm vào trước
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            "${MediaStore.Images.Media.IS_PENDING} = 0",
            null,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val mimeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
            val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)

            while (cursor.moveToNext()) {
                currentCoroutineContext().ensureActive()
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn) ?: "IMG_$id.jpg"
                val mime = cursor.getString(mimeColumn) ?: "image/jpeg"
                val size = cursor.getLong(sizeColumn)
                val dateAdded = cursor.getLong(dateColumn)

                // Tạo Uri theo quy chuẩn MediaStore Content Provider
                val itemUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                resultList.add(
                    MediaImage(
                        id = id,
                        uri = itemUri,
                        displayName = name,
                        mimeType = mime,
                        sizeBytes = size,
                        dateAddedSeconds = dateAdded
                    )
                )
            }
        }
        // Để lỗi quyền/I/O tới ViewModel, không báo sai thành thư viện trống.

        resultList
    }

    /** Read the source URI directly; no MediaStore insert, copy, or write occurs. */
    override suspend fun getMediaInfo(uri: Uri): MediaImage = withContext(Dispatchers.IO) {
        currentCoroutineContext().ensureActive()
        var displayName = "selected_photo.jpg"
        var sizeBytes = 0L
        // Verify that the picker grant still lets the app read the source.
        contentResolver.openInputStream(uri)?.use { }
            ?: throw IOException("Không thể đọc ảnh đã chọn")
        try {
            contentResolver.query(
                uri, arrayOf(OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE),
                null, null, null
            )?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex >= 0) cursor.getString(nameIndex)?.let { displayName = it }
                    val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                    if (sizeIndex >= 0) sizeBytes = cursor.getLong(sizeIndex)
                }
            }
        } catch (error: Exception) {
            if (error is CancellationException || error is SecurityException) throw error
            // Some providers cannot supply optional metadata; the readable source is still usable.
        }
        currentCoroutineContext().ensureActive()
        MediaImage(
            id = 0L, // Picker IDs are provider-specific; use URI as the grid/merge identity.
            uri = uri,
            displayName = displayName,
            mimeType = contentResolver.getType(uri) ?: "image/jpeg",
            sizeBytes = sizeBytes,
            dateAddedSeconds = System.currentTimeMillis() / 1000L
        )
    }
}
```

## app/src/main/java/com/example/androidtrainingexample/minigallery/model/MediaImage.kt

Source: [app/src/main/java/com/example/androidtrainingexample/minigallery/model/MediaImage.kt](../app/src/main/java/com/example/androidtrainingexample/minigallery/model/MediaImage.kt). SHA256: `E21069A51EE228CB9DD51B2D7BA20E72839B4612B967EBFC55CE71291F4FCDB7`.

```kotlin
package com.example.androidtrainingexample.minigallery.model

import android.net.Uri
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.ln
import kotlin.math.pow

/**
 * Đại diện cho thông tin một ảnh lấy từ MediaStore (ContentResolver.query).
 *
 * Lưu ý kiến thức Android Storage:
 * - uri được tạo thông qua ContentUris.withAppendedId(Images.Media.EXTERNAL_CONTENT_URI, id)
 * - Tuyệt đối không dùng đường dẫn thực tế (_data) theo chuẩn Scoped Storage.
 */
data class MediaImage(
    val id: Long,
    val uri: Uri,
    val displayName: String,
    val mimeType: String,
    val sizeBytes: Long,
    val dateAddedSeconds: Long
) {
    /**
     * Định dạng dung lượng sang dạng người dùng dễ đọc (B, KB, MB, GB).
     */
    val formattedSize: String
        get() {
            if (sizeBytes <= 0) return "0 B"
            val units = arrayOf("B", "KB", "MB", "GB")
            val digitGroups = (ln(sizeBytes.toDouble()) / ln(1024.0)).toInt().coerceIn(0, units.size - 1)
            return String.format(Locale.getDefault(), "%.1f %s", sizeBytes / 1024.0.pow(digitGroups.toDouble()), units[digitGroups])
        }

    /**
     * Định dạng ngày thêm sang dạng ngày giờ đọc được.
     */
    val formattedDate: String
        get() {
            if (dateAddedSeconds <= 0) return "Không xác định"
            val date = Date(dateAddedSeconds * 1000L)
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            return sdf.format(date)
        }
}
```

## app/src/main/java/com/example/androidtrainingexample/minigallery/model/PermissionState.kt

Source: [app/src/main/java/com/example/androidtrainingexample/minigallery/model/PermissionState.kt](../app/src/main/java/com/example/androidtrainingexample/minigallery/model/PermissionState.kt). SHA256: `74A67423C42362FA2960C1C5BB1F784741D98526B178E4507C5320DD6FD79037`.

```kotlin
package com.example.androidtrainingexample.minigallery.model

/**
 * Biểu diễn trạng thái cấp quyền đọc ảnh trên các phiên bản Android (API 29-34+).
 *
 * - [Denied]: Chưa được cấp quyền truy cập bộ nhớ ảnh.
 * - [GrantedPartial]: Chỉ được cấp quyền truy cập một phần ảnh (Android 14+ với READ_MEDIA_VISUAL_USER_SELECTED).
 * - [GrantedFull]: Được cấp toàn quyền đọc ảnh (READ_MEDIA_IMAGES hoặc READ_EXTERNAL_STORAGE).
 */
sealed interface PermissionState {
    data object Denied : PermissionState

    data class GrantedPartial(
        val selectedCount: Int = 0
    ) : PermissionState

    data object GrantedFull : PermissionState
}
```

## app/src/main/java/com/example/androidtrainingexample/minigallery/model/SortOrder.kt

Source: [app/src/main/java/com/example/androidtrainingexample/minigallery/model/SortOrder.kt](../app/src/main/java/com/example/androidtrainingexample/minigallery/model/SortOrder.kt). SHA256: `ABF1E466644B22E3A48D8F993BD8F02FDD23069807EFF3CBAD4278764BB6278A`.

```kotlin
package com.example.androidtrainingexample.minigallery.model

/**
 * Các chế độ sắp xếp danh sách ảnh.
 * Dùng để áp dụng Kotlin Collections extensions: sortedBy, sortedByDescending.
 */
enum class SortOrder(val label: String) {
    DATE_DESC("Mới nhất trước"),
    DATE_ASC("Cũ nhất trước"),
    NAME_ASC("Tên (A → Z)"),
    NAME_DESC("Tên (Z → A)")
}
```

## app/src/main/java/com/example/androidtrainingexample/minigallery/ui/MiniGalleryScreen.kt

Source: [app/src/main/java/com/example/androidtrainingexample/minigallery/ui/MiniGalleryScreen.kt](../app/src/main/java/com/example/androidtrainingexample/minigallery/ui/MiniGalleryScreen.kt). SHA256: `246EE79059F1F9EB63BFCBFCA9F382707B46494B28856196109112B0A5127286`.

```kotlin
package com.example.androidtrainingexample.minigallery.ui

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.androidtrainingexample.R
import com.example.androidtrainingexample.minigallery.model.*
import com.example.androidtrainingexample.minigallery.ui.theme.MiniGalleryTheme

/** Stateless screen callbacks keep Android launchers and storage work outside composition. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiniGalleryScreen(
    state: MiniGalleryUiState,
    onSearchQueryChange: (String) -> Unit,
    onSortOrderChange: (SortOrder) -> Unit,
    onRequestPermission: () -> Unit,
    onAddPhotos: () -> Unit
) {
    var sortMenuExpanded by remember { mutableStateOf(false) }
    // URI survives Activity recreation; revoking access removes the detail from the screen.
    var detailUri by rememberSaveable { mutableStateOf<String?>(null) }
    val detailImage = state.allImages.firstOrNull { it.uri.toString() == detailUri }

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.app_name)) }) },
        floatingActionButton = {
            Button(onClick = onAddPhotos, enabled = !state.isAddingPhotos) {
                Icon(painterResource(R.drawable.ic_add), contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.btn_add_photos))
            }
        }
    ) { contentPadding ->
        Column(Modifier.fillMaxSize().padding(contentPadding).consumeWindowInsets(contentPadding)) {
            PermissionBanner(state.permissionState, onRequestPermission)
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                label = { Text(stringResource(R.string.search_hint)) },
                leadingIcon = {
                    Icon(painterResource(R.drawable.ic_search), contentDescription = null)
                },
                singleLine = true
            )
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(R.string.photo_count_format, state.displayedCount, state.totalCount),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
                Box {
                    TextButton(onClick = { sortMenuExpanded = true }) {
                        Icon(painterResource(R.drawable.ic_sort), stringResource(R.string.sort_tooltip))
                        Spacer(Modifier.width(4.dp))
                        Text(state.sortOrder.label)
                    }
                    DropdownMenu(
                        expanded = sortMenuExpanded,
                        onDismissRequest = { sortMenuExpanded = false }
                    ) {
                        SortOrder.entries.forEach { order ->
                            DropdownMenuItem(
                                text = { Text(order.label) },
                                onClick = {
                                    sortMenuExpanded = false
                                    onSortOrderChange(order)
                                }
                            )
                        }
                    }
                }
            }
            Box(Modifier.fillMaxWidth().weight(1f)) {
                if (state.displayedImages.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 88.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(state.displayedImages, key = { it.uri.toString() }) { image ->
                            Card(onClick = { detailUri = image.uri.toString() }) {
                                Photo(image.uri, image.displayName, Modifier.fillMaxWidth().aspectRatio(1f))
                                Text(
                                    image.displayName,
                                    Modifier.padding(6.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                } else if (!state.isLoading && !state.isAddingPhotos) {
                    Column(
                        Modifier.align(Alignment.Center).padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(painterResource(R.drawable.ic_image), null, Modifier.size(64.dp))
                        Spacer(Modifier.height(12.dp))
                        Text(stringResource(
                            if (state.searchQuery.isNotEmpty()) R.string.empty_search_msg
                            else R.string.empty_gallery_msg
                        ))
                    }
                }
                if (state.isLoading || state.isAddingPhotos) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }
        }
    }

    detailImage?.let { image -> ImageDetailDialog(image) { detailUri = null } }
}

@Composable
private fun PermissionBanner(permission: PermissionState, onRequestPermission: () -> Unit) {
    if (permission == PermissionState.GrantedFull) return
    val partial = permission is PermissionState.GrantedPartial
    Card(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(R.drawable.ic_warning), null)
                Spacer(Modifier.width(8.dp))
                Text(
                    stringResource(if (partial) R.string.permission_partial_title else R.string.permission_denied_title),
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Text(
                stringResource(if (partial) R.string.permission_partial_desc else R.string.permission_denied_desc),
                modifier = Modifier.padding(top = 4.dp),
                style = MaterialTheme.typography.bodySmall
            )
            TextButton(onClick = onRequestPermission) {
                Text(stringResource(if (partial) R.string.permission_manage_btn else R.string.permission_grant_btn))
            }
        }
    }
}

@Composable
private fun Photo(uri: Uri, description: String?, modifier: Modifier, contentScale: ContentScale = ContentScale.Crop) {
    AsyncImage(
        model = uri,
        contentDescription = description,
        modifier = modifier,
        contentScale = contentScale,
        placeholder = painterResource(R.drawable.ic_image),
        error = painterResource(R.drawable.ic_image)
    )
}

@Composable
private fun ImageDetailDialog(image: MediaImage, onDismiss: () -> Unit) {
    GalleryDialog(onDismiss) {
        Text(stringResource(R.string.dialog_details_title), style = MaterialTheme.typography.titleLarge)
        LazyColumn(Modifier.weight(1f, fill = false), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            item { Photo(image.uri, image.displayName, Modifier.fillMaxWidth().height(240.dp), ContentScale.Fit) }
            item { Metadata(stringResource(R.string.label_file_name), image.displayName) }
            item { Metadata(stringResource(R.string.label_mime_type), image.mimeType) }
            item { Metadata(stringResource(R.string.label_file_size), "${image.formattedSize} (${image.sizeBytes} bytes)") }
            item { Metadata(stringResource(R.string.label_date_added), image.formattedDate) }
            item { Metadata(stringResource(R.string.label_content_uri), image.uri.toString()) }
        }
        TextButton(onClick = onDismiss, modifier = Modifier.align(Alignment.End)) {
            Text(stringResource(R.string.btn_close))
        }
    }
}

@Composable
private fun Metadata(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
        SelectionContainer { Text(value, style = MaterialTheme.typography.bodyMedium) }
    }
}

@Composable
private fun GalleryDialog(
    onDismiss: () -> Unit,
    dismissible: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = dismissible, dismissOnClickOutside = dismissible)
    ) {
        Surface(shape = MaterialTheme.shapes.extraLarge, tonalElevation = 6.dp) {
            Column(
                Modifier.fillMaxWidth().heightIn(max = 640.dp).padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                content = content
            )
        }
    }
}

@Preview(showBackground = true, locale = "vi")
@Composable
private fun MiniGalleryPreview() {
    MiniGalleryTheme {
        MiniGalleryScreen(MiniGalleryUiState(), {}, {}, {}, {})
    }
}
```

## app/src/main/java/com/example/androidtrainingexample/minigallery/ui/MiniGalleryUiState.kt

Source: [app/src/main/java/com/example/androidtrainingexample/minigallery/ui/MiniGalleryUiState.kt](../app/src/main/java/com/example/androidtrainingexample/minigallery/ui/MiniGalleryUiState.kt). SHA256: `4A4803896D14AE6A31E7BBB4F3C4DDF5DBBAE7954A0E356A7138A69AA09D1B82`.

```kotlin
package com.example.androidtrainingexample.minigallery.ui

import com.example.androidtrainingexample.minigallery.model.MediaImage
import com.example.androidtrainingexample.minigallery.model.PermissionState
import com.example.androidtrainingexample.minigallery.model.SortOrder

/**
 * State duy nhất cho màn hình MiniGallery tuân thủ mô hình UDF (Unidirectional Data Flow).
 */
data class MiniGalleryUiState(
    val allImages: List<MediaImage> = emptyList(),
    val displayedImages: List<MediaImage> = emptyList(),
    val searchQuery: String = "",
    val sortOrder: SortOrder = SortOrder.DATE_DESC,
    val isLoading: Boolean = false,
    val permissionState: PermissionState = PermissionState.Denied,
    val isAddingPhotos: Boolean = false,
    val userMessage: String? = null
) {
    /**
     * Tổng số ảnh đang có quyền truy cập.
     */
    val totalCount: Int get() = allImages.size

    /**
     * Số ảnh hiện đang hiển thị sau khi lọc tìm kiếm.
     */
    val displayedCount: Int get() = displayedImages.size
}
```

## app/src/main/java/com/example/androidtrainingexample/minigallery/ui/MiniGalleryViewModel.kt

Source: [app/src/main/java/com/example/androidtrainingexample/minigallery/ui/MiniGalleryViewModel.kt](../app/src/main/java/com/example/androidtrainingexample/minigallery/ui/MiniGalleryViewModel.kt). SHA256: `3646D8C189DC8372133944CCE47F4FC952DDE8F29EF8FC08ADAD8F25B9F861BB`.

```kotlin
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

/** Picker sources appear directly in the gallery; this ViewModel never creates copies. */
class MiniGalleryViewModel(private val repository: MediaRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(MiniGalleryUiState())
    val uiState: StateFlow<MiniGalleryUiState> = _uiState.asStateFlow()
    private var observeJob: Job? = null
    private var deviceImages: List<MediaImage> = emptyList()
    private var pickedImages: List<MediaImage> = emptyList()

    fun updatePermissionState(newState: PermissionState) {
        _uiState.update { it.copy(permissionState = newState) }
        when (newState) {
            PermissionState.GrantedFull, is PermissionState.GrantedPartial -> startObservingMedia()
            PermissionState.Denied -> {
                observeJob?.cancel()
                observeJob = null
                deviceImages = emptyList()
                publishImages()
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

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
                if (error is CancellationException) throw error
                _uiState.update {
                    it.copy(isLoading = false, userMessage = "Lỗi khi đọc thư viện ảnh: ${error.localizedMessage}")
                }
            }
        }
    }

    /** Combine both sources so a permission refresh does not erase picker selections. */
    private fun publishImages(clearSearch: Boolean = false) {
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

    fun setSearchQuery(query: String) {
        _uiState.update { current ->
            current.copy(
                searchQuery = query,
                displayedImages = applyFilterAndSort(current.allImages, query, current.sortOrder)
            )
        }
    }

    fun setSortOrder(order: SortOrder) {
        _uiState.update { current ->
            current.copy(
                sortOrder = order,
                displayedImages = applyFilterAndSort(current.allImages, current.searchQuery, order)
            )
        }
    }

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
                        // Show each successfully read source immediately and clear a stale filter.
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
                _uiState.update { it.copy(isAddingPhotos = false) }
            }
        }
    }

    fun clearUserMessage() {
        _uiState.update { it.copy(userMessage = null) }
    }

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
```

## app/src/main/java/com/example/androidtrainingexample/minigallery/ui/theme/MiniGalleryTheme.kt

Source: [app/src/main/java/com/example/androidtrainingexample/minigallery/ui/theme/MiniGalleryTheme.kt](../app/src/main/java/com/example/androidtrainingexample/minigallery/ui/theme/MiniGalleryTheme.kt). SHA256: `7EF876DC5F51339B764943BEC3EA358F582A896BC8A354399B6D1C9C0772E275`.

```kotlin
package com.example.androidtrainingexample.minigallery.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF5364BE), secondary = Color(0xFF5B5D72), tertiary = Color(0xFF77536E)
)
private val DarkColors = darkColorScheme(
    primary = Color(0xFFBAC3FF), secondary = Color(0xFFC4C4DC), tertiary = Color(0xFFE7B8D7)
)

@Composable
fun MiniGalleryTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) DarkColors else LightColors,
        content = content
    )
}
```

## app/src/main/java/com/example/androidtrainingexample/minigallery/util/PermissionHelper.kt

Source: [app/src/main/java/com/example/androidtrainingexample/minigallery/util/PermissionHelper.kt](../app/src/main/java/com/example/androidtrainingexample/minigallery/util/PermissionHelper.kt). SHA256: `EA1247BDC333DB110526DDD89F1350A15301FD442CAB45CCCE990B228A03D2AA`.

```kotlin
package com.example.androidtrainingexample.minigallery.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.example.androidtrainingexample.minigallery.model.PermissionState

/**
 * Tiện ích kiểm tra và trả về danh sách quyền đọc ảnh tùy theo phiên bản Android.
 *
 * Chiến lược:
 * - API 34+ (Android 14+): Hỗ trợ Partial Access với READ_MEDIA_VISUAL_USER_SELECTED.
 * - API 33 (Android 13): Sử dụng READ_MEDIA_IMAGES.
 * - API 29-32 (Android 10 - 12): Sử dụng READ_EXTERNAL_STORAGE.
 */
object PermissionHelper {

    /**
     * Lấy danh sách các quyền cần xin runtime từ người dùng.
     */
    fun getRequiredPermissions(): Array<String> {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> {
                arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
                )
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
            }
            else -> {
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    /**
     * Kiểm tra trạng thái quyền hiện tại của ứng dụng.
     */
    fun checkPermissionState(context: Context, currentKnownImageCount: Int = 0): PermissionState {
        return when {
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

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                val hasImages = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED

                if (hasImages) PermissionState.GrantedFull else PermissionState.Denied
            }

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
```

## app/src/test/java/com/example/androidtrainingexample/ExampleUnitTest.kt

Source: [app/src/test/java/com/example/androidtrainingexample/ExampleUnitTest.kt](../app/src/test/java/com/example/androidtrainingexample/ExampleUnitTest.kt). SHA256: `23A3F1BAD18472F636EE96AC3266284305D39BB296092BB667007C1DB139511B`.

```kotlin
package com.example.androidtrainingexample

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
```

## app/src/test/java/com/example/androidtrainingexample/minigallery/MediaRepositoryBehaviorTest.kt

Source: [app/src/test/java/com/example/androidtrainingexample/minigallery/MediaRepositoryBehaviorTest.kt](../app/src/test/java/com/example/androidtrainingexample/minigallery/MediaRepositoryBehaviorTest.kt). SHA256: `CD1B54FEEE09EBEC6386A0F0864675BD12A6F5BE7B133E824BBC82457F80B554`.

```kotlin
package com.example.androidtrainingexample.minigallery

import android.net.Uri
import com.example.androidtrainingexample.minigallery.model.MediaImage
import com.example.androidtrainingexample.minigallery.model.SortOrder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock

class MediaRepositoryBehaviorTest {

    @Test
    fun `MediaImage formatSize hien thi dung don vi dung luong`() {
        val uri = mock<Uri>()
        val imgBytes = MediaImage(1L, uri, "test.jpg", "image/jpeg", 500L, 0L)
        assertEquals("500.0 B", imgBytes.formattedSize)

        val imgKb = MediaImage(2L, uri, "test.jpg", "image/jpeg", 1024L * 50L, 0L)
        assertEquals("50.0 KB", imgKb.formattedSize)

        val imgMb = MediaImage(3L, uri, "test.jpg", "image/jpeg", 1024L * 1024L * 3L + (500L * 1024L), 0L)
        assertEquals("3.5 MB", imgMb.formattedSize)
    }

    @Test
    fun `MediaImage formatDate tra ve chuoi hop le voi timestamp duong`() {
        val uri = mock<Uri>()
        val imgWithDate = MediaImage(1L, uri, "test.jpg", "image/jpeg", 1000L, 1700000000L)
        assertNotEquals("Không xác định", imgWithDate.formattedDate)
        assertTrue(imgWithDate.formattedDate.contains("/"))

        val imgZeroDate = MediaImage(2L, uri, "test.jpg", "image/jpeg", 1000L, 0L)
        assertEquals("Không xác định", imgZeroDate.formattedDate)
    }

    @Test
    fun `SortOrder cung cap nhan tieng Viet ro rang`() {
        assertEquals("Mới nhất trước", SortOrder.DATE_DESC.label)
        assertEquals("Cũ nhất trước", SortOrder.DATE_ASC.label)
        assertEquals("Tên (A → Z)", SortOrder.NAME_ASC.label)
        assertEquals("Tên (Z → A)", SortOrder.NAME_DESC.label)
    }
}
```

## app/src/test/java/com/example/androidtrainingexample/minigallery/MediaStoreQueryTest.kt

Source: [app/src/test/java/com/example/androidtrainingexample/minigallery/MediaStoreQueryTest.kt](../app/src/test/java/com/example/androidtrainingexample/minigallery/MediaStoreQueryTest.kt). SHA256: `6357815D44F7ABE89889A2CCB515DFFEE73DAFEC2144C6833EF654ABE7EE79E1`.

```kotlin
package com.example.androidtrainingexample.minigallery

import android.content.ContentResolver
import android.content.Context
import com.example.androidtrainingexample.minigallery.data.MediaStoreRepositoryImpl
import kotlinx.coroutines.test.runTest
import org.junit.Assert.fail
import org.junit.Test
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class MediaStoreQueryTest {
    @Test
    fun `loi quyen khi query khong duoc gia thanh thu vien rong`() = runTest {
        val resolver = mock<ContentResolver>()
        whenever(resolver.query(
            anyOrNull(), anyOrNull(), anyOrNull<String>(),
            anyOrNull<Array<String>>(), anyOrNull<String>()
        )).thenThrow(SecurityException("Permission revoked"))
        val repository = MediaStoreRepositoryImpl(resolver, mock<Context>())
        try {
            repository.queryImages()
            fail("Query error must reach ViewModel so the UI can report it")
        } catch (expected: SecurityException) {
            org.junit.Assert.assertEquals("Permission revoked", expected.message)
        }
    }
}
```

## app/src/test/java/com/example/androidtrainingexample/minigallery/MiniGalleryViewModelTest.kt

Source: [app/src/test/java/com/example/androidtrainingexample/minigallery/MiniGalleryViewModelTest.kt](../app/src/test/java/com/example/androidtrainingexample/minigallery/MiniGalleryViewModelTest.kt). SHA256: `A2CC9145F72DF0A5744F03840044A9BA201323116F733AD3910B57E1F9DA28BE`.

```kotlin
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
```

## app/src/androidTest/java/com/example/androidtrainingexample/ExampleInstrumentedTest.kt

Source: [app/src/androidTest/java/com/example/androidtrainingexample/ExampleInstrumentedTest.kt](../app/src/androidTest/java/com/example/androidtrainingexample/ExampleInstrumentedTest.kt). SHA256: `181493E52F434EDA48486413A925CAB17098C27C4A78042EC2984D77924B63F9`.

```kotlin
package com.example.androidtrainingexample

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.minigallerycompose", appContext.packageName)
    }
}
```

## app/src/androidTest/java/com/example/androidtrainingexample/minigallery/MediaStoreRepositoryInstrumentedTest.kt

Source: [app/src/androidTest/java/com/example/androidtrainingexample/minigallery/MediaStoreRepositoryInstrumentedTest.kt](../app/src/androidTest/java/com/example/androidtrainingexample/minigallery/MediaStoreRepositoryInstrumentedTest.kt). SHA256: `039546043EFF850F8D965702EEA01591FBF7AA06CB710652D9C2F0A49805340C`.

```kotlin
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
```

## app/src/androidTest/java/com/example/androidtrainingexample/minigallery/MiniGalleryScreenTest.kt

Source: [app/src/androidTest/java/com/example/androidtrainingexample/minigallery/MiniGalleryScreenTest.kt](../app/src/androidTest/java/com/example/androidtrainingexample/minigallery/MiniGalleryScreenTest.kt). SHA256: `1294432890EE910CA00C772F258CDC4CC47F5B04D851007E7EDF6366CF833D8E`.

```kotlin
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
```

## app/src/main/res/drawable/ic_add.xml

Source: [app/src/main/res/drawable/ic_add.xml](../app/src/main/res/drawable/ic_add.xml). SHA256: `A197245D4087377776E8667C2C90F223315B262DF3894BDF6879F4BDAE04E23E`.

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24"
    android:tint="#FF757575">
    <path
        android:fillColor="@android:color/white"
        android:pathData="M19,13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z"/>
</vector>
```

## app/src/main/res/drawable/ic_check_circle.xml

Source: [app/src/main/res/drawable/ic_check_circle.xml](../app/src/main/res/drawable/ic_check_circle.xml). SHA256: `701B1C562C836DD3660304C98731A0B53325C610D04EC0FF5CD33459560BCCA9`.

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
    <path
        android:fillColor="#4CAF50"
        android:pathData="M12,2C6.48,2 2,6.48 2,12s4.48,10 10,10 10,-4.48 10,-10S17.52,2 12,2zM10,17l-5,-5 1.41,-1.41L10,14.17l7.59,-7.59L19,8l-9,9z"/>
</vector>
```

## app/src/main/res/drawable/ic_close.xml

Source: [app/src/main/res/drawable/ic_close.xml](../app/src/main/res/drawable/ic_close.xml). SHA256: `82AC0C98AF835B7F71C22442EA1D629198A8E88E850CBF6FF13F007C7CC61B31`.

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24"
    android:tint="#FF757575">
    <path
        android:fillColor="@android:color/white"
        android:pathData="M19,6.41L17.59,5 12,10.59 6.41,5 5,6.41 10.59,12 5,17.59 6.41,19 12,13.41 17.59,19 19,17.59 13.41,12z"/>
</vector>
```

## app/src/main/res/drawable/ic_error_circle.xml

Source: [app/src/main/res/drawable/ic_error_circle.xml](../app/src/main/res/drawable/ic_error_circle.xml). SHA256: `1275C9A0AAD8EBBB257BCA86AEB6B6C4567210400B937FAC066514221CE878F8`.

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
    <path
        android:fillColor="#F44336"
        android:pathData="M12,2C6.48,2 2,6.48 2,12s4.48,10 10,10 10,-4.48 10,-10S17.52,2 12,2zM13,17h-2v-2h2v2zM13,13h-2V7h2v6z"/>
</vector>
```

## app/src/main/res/drawable/ic_image.xml

Source: [app/src/main/res/drawable/ic_image.xml](../app/src/main/res/drawable/ic_image.xml). SHA256: `6D1ED248E42CB89242DEA7D09BA5CF1E21E8FC75B97194AD6A694EBD4D98540C`.

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24"
    android:tint="#FF757575">
    <path
        android:fillColor="@android:color/white"
        android:pathData="M21,19V5c0,-1.1 -0.9,-2 -2,-2H5c-1.1,0 -2,0.9 -2,2v14c0,1.1 0.9,2 2,2h14c1.1,0 2,-0.9 2,-2zM8.5,13.5l2.5,3.01L14.5,12l4.5,6H5l3.5,-4.5z"/>
</vector>
```

## app/src/main/res/drawable/ic_launcher_background.xml

Source: [app/src/main/res/drawable/ic_launcher_background.xml](../app/src/main/res/drawable/ic_launcher_background.xml). SHA256: `ED423C73A6F40A4D2909F0901E60527B3A807CD59E1B5593BCAAE1808B1C6321`.

```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="108dp"
    android:height="108dp"
    android:viewportWidth="108"
    android:viewportHeight="108">
    <path
        android:fillColor="#3DDC84"
        android:pathData="M0,0h108v108h-108z" />
    <path
        android:fillColor="#00000000"
        android:pathData="M9,0L9,108"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M19,0L19,108"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M29,0L29,108"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M39,0L39,108"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M49,0L49,108"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M59,0L59,108"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M69,0L69,108"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M79,0L79,108"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M89,0L89,108"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M99,0L99,108"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M0,9L108,9"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M0,19L108,19"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M0,29L108,29"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M0,39L108,39"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M0,49L108,49"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M0,59L108,59"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M0,69L108,69"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M0,79L108,79"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M0,89L108,89"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M0,99L108,99"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M19,29L89,29"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M19,39L89,39"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M19,49L89,49"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M19,59L89,59"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M19,69L89,69"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M19,79L89,79"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M29,19L29,89"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M39,19L39,89"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M49,19L49,89"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M59,19L59,89"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M69,19L69,89"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
    <path
        android:fillColor="#00000000"
        android:pathData="M79,19L79,89"
        android:strokeWidth="0.8"
        android:strokeColor="#33FFFFFF" />
</vector>
```

## app/src/main/res/drawable/ic_launcher_foreground.xml

Source: [app/src/main/res/drawable/ic_launcher_foreground.xml](../app/src/main/res/drawable/ic_launcher_foreground.xml). SHA256: `01D1A6A6C1234EB7FE270D097EB283D72B9C95AE5118886F1B6573AAD280F1F7`.

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:aapt="http://schemas.android.com/aapt"
    android:width="108dp"
    android:height="108dp"
    android:viewportWidth="108"
    android:viewportHeight="108">
    <path android:pathData="M31,63.928c0,0 6.4,-11 12.1,-13.1c7.2,-2.6 26,-1.4 26,-1.4l38.1,38.1L107,108.928l-32,-1L31,63.928z">
        <aapt:attr name="android:fillColor">
            <gradient
                android:endX="85.84757"
                android:endY="92.4963"
                android:startX="42.9492"
                android:startY="49.59793"
                android:type="linear">
                <item
                    android:color="#44000000"
                    android:offset="0.0" />
                <item
                    android:color="#00000000"
                    android:offset="1.0" />
            </gradient>
        </aapt:attr>
    </path>
    <path
        android:fillColor="#FFFFFF"
        android:fillType="nonZero"
        android:pathData="M65.3,45.828l3.8,-6.6c0.2,-0.4 0.1,-0.9 -0.3,-1.1c-0.4,-0.2 -0.9,-0.1 -1.1,0.3l-3.9,6.7c-6.3,-2.8 -13.4,-2.8 -19.7,0l-3.9,-6.7c-0.2,-0.4 -0.7,-0.5 -1.1,-0.3C38.8,38.328 38.7,38.828 38.9,39.228l3.8,6.6C36.2,49.428 31.7,56.028 31,63.928h46C76.3,56.028 71.8,49.428 65.3,45.828zM43.4,57.328c-0.8,0 -1.5,-0.5 -1.8,-1.2c-0.3,-0.7 -0.1,-1.5 0.4,-2.1c0.5,-0.5 1.4,-0.7 2.1,-0.4c0.7,0.3 1.2,1 1.2,1.8C45.3,56.528 44.5,57.328 43.4,57.328L43.4,57.328zM64.6,57.328c-0.8,0 -1.5,-0.5 -1.8,-1.2s-0.1,-1.5 0.4,-2.1c0.5,-0.5 1.4,-0.7 2.1,-0.4c0.7,0.3 1.2,1 1.2,1.8C66.5,56.528 65.6,57.328 64.6,57.328L64.6,57.328z"
        android:strokeWidth="1"
        android:strokeColor="#00000000" />
</vector>
```

## app/src/main/res/drawable/ic_search.xml

Source: [app/src/main/res/drawable/ic_search.xml](../app/src/main/res/drawable/ic_search.xml). SHA256: `0DD306B4BF7C3C19265E2ACD268433568A8404DD642C28DB7329554ADEC0407E`.

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24"
    android:tint="#FF757575">
    <path
        android:fillColor="@android:color/white"
        android:pathData="M15.5,14h-0.79l-0.28,-0.27C15.41,12.59 16,11.11 16,9.5 16,5.91 13.09,3 9.5,3S3,5.91 3,9.5 5.91,16 9.5,16c1.61,0 3.09,-0.59 4.23,-1.57l0.27,0.28v0.79l5,4.99L20.49,19l-4.99,-5zM9.5,14C7.01,14 5,11.99 5,9.5S7.01,5 9.5,5 14,7.01 14,9.5 11.99,14 9.5,14z"/>
</vector>
```

## app/src/main/res/drawable/ic_sort.xml

Source: [app/src/main/res/drawable/ic_sort.xml](../app/src/main/res/drawable/ic_sort.xml). SHA256: `E1ADB7C0D7C0BDE3ED90E16A11A2BDA7FB39F3B13D69D46641ACC63CA140C1BE`.

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24"
    android:tint="#FF757575">
    <path
        android:fillColor="@android:color/white"
        android:pathData="M3,18h6v-2L3,16v2zM3,6v2h18L21,6L3,6zM3,13h12v-2L3,11v2z"/>
</vector>
```

## app/src/main/res/drawable/ic_warning.xml

Source: [app/src/main/res/drawable/ic_warning.xml](../app/src/main/res/drawable/ic_warning.xml). SHA256: `136AEBDA2CCF1EC4A23AE38AA26CF9A9E2B95F494C109B6200A0DA5F8D393C04`.

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
    <path
        android:fillColor="#FF9800"
        android:pathData="M1,21h22L12,2 1,21zM13,18h-2v-2h2v2zM13,14h-2v-4h2v4z"/>
</vector>
```

## app/src/main/res/mipmap-anydpi/ic_launcher_round.xml

Source: [app/src/main/res/mipmap-anydpi/ic_launcher_round.xml](../app/src/main/res/mipmap-anydpi/ic_launcher_round.xml). SHA256: `88F7653499EF524126EA5018A99BAF9CC3269E7E584D6205DFDD76DB39F39CC0`.

```xml
<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@drawable/ic_launcher_background" />
    <foreground android:drawable="@drawable/ic_launcher_foreground" />
    <monochrome android:drawable="@drawable/ic_launcher_foreground" />
</adaptive-icon>
```

## app/src/main/res/mipmap-anydpi/ic_launcher.xml

Source: [app/src/main/res/mipmap-anydpi/ic_launcher.xml](../app/src/main/res/mipmap-anydpi/ic_launcher.xml). SHA256: `88F7653499EF524126EA5018A99BAF9CC3269E7E584D6205DFDD76DB39F39CC0`.

```xml
<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@drawable/ic_launcher_background" />
    <foreground android:drawable="@drawable/ic_launcher_foreground" />
    <monochrome android:drawable="@drawable/ic_launcher_foreground" />
</adaptive-icon>
```

## app/src/main/res/values-night/themes.xml

Source: [app/src/main/res/values-night/themes.xml](../app/src/main/res/values-night/themes.xml). SHA256: `4B5D02149420EC29B752621377E280A62916CA3A8A88AA8CA4AF67F8BC507227`.

```xml
<resources>
    <style name="Theme.AndroidTrainingExample" parent="android:style/Theme.Material.NoActionBar">
        <item name="android:fontFamily">sans</item>
        <item name="android:windowLightStatusBar">false</item>
        <item name="android:windowActionModeOverlay">true</item>
    </style>
</resources>
```

## app/src/main/res/values/colors.xml

Source: [app/src/main/res/values/colors.xml](../app/src/main/res/values/colors.xml). SHA256: `EFB70D7499549A57CEA32B00E790EF0632CB0C95CDAEEFD16903C8CD12C889E4`.

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="black">#FF000000</color>
    <color name="white">#FFFFFFFF</color>
</resources>
```

## app/src/main/res/values/strings.xml

Source: [app/src/main/res/values/strings.xml](../app/src/main/res/values/strings.xml). SHA256: `42D44C46B1F417FBE270FD6C98EB0C27BDA575CDA3BA75011C8708F319C1338F`.

```xml
<resources>
    <string name="app_name">MiniGallery</string>
    <string name="search_hint">Tìm kiếm ảnh theo tên…</string>
    <string name="sort_tooltip">Sắp xếp danh sách</string>
    <string name="sort_date_desc">Mới nhất trước</string>
    <string name="sort_date_asc">Cũ nhất trước</string>
    <string name="sort_name_asc">Tên (A → Z)</string>
    <string name="sort_name_desc">Tên (Z → A)</string>

    <string name="permission_denied_title">Chưa được cấp quyền đọc ảnh</string>
    <string name="permission_denied_desc">Ứng dụng cần quyền đọc ảnh để hiển thị thư viện trên thiết bị của bạn.</string>
    <string name="permission_grant_btn">Cấp quyền ngay</string>

    <string name="permission_partial_title">Quyền truy cập một phần (Android 14+)</string>
    <string name="permission_partial_desc">Bạn đang cho phép ứng dụng truy cập một số ảnh đã chọn.</string>
    <string name="permission_manage_btn">Quản lý / Chọn thêm ảnh</string>

    <string name="btn_add_photos">Thêm ảnh (tối đa 10)</string>
    <string name="empty_gallery_msg">Thư viện trống hoặc chưa có ảnh nào</string>
    <string name="empty_search_msg">Không tìm thấy ảnh nào khớp với từ khóa</string>
    <string name="photo_count_format">Đang hiển thị %1$d / %2$d ảnh</string>

    <string name="dialog_details_title">Chi tiết Metadata ảnh</string>
    <string name="label_file_name">Tên file:</string>
    <string name="label_content_uri">Content URI:</string>
    <string name="label_mime_type">MIME Type:</string>
    <string name="label_file_size">Dung lượng:</string>
    <string name="label_date_added">Ngày thêm:</string>
    <string name="btn_close">Đóng</string>
</resources>
```

## app/src/main/res/values/themes.xml

Source: [app/src/main/res/values/themes.xml](../app/src/main/res/values/themes.xml). SHA256: `89AEFD20CAB09AC20B7B467EB9EDDC30F6DBB43FB1E77B83F58049FC4DE98747`.

```xml
<resources>
    <!-- Window theme only; screen content and dialogs use Jetpack Compose. -->
    <style name="Theme.AndroidTrainingExample" parent="android:style/Theme.Material.Light.NoActionBar">
        <item name="android:fontFamily">sans</item>
        <item name="android:windowLightStatusBar">true</item>
        <item name="android:windowActionModeOverlay">true</item>
    </style>
</resources>
```

## app/src/main/res/xml/backup_rules.xml

Source: [app/src/main/res/xml/backup_rules.xml](../app/src/main/res/xml/backup_rules.xml). SHA256: `6CF1A27E6807B1D24E41D3FBE7DDC1BFE1F42226027964F6EAF477D71B43B283`.

```xml
<?xml version="1.0" encoding="utf-8"?><!--
   Sample backup rules file; uncomment and customize as necessary.
   See https://developer.android.com/guide/topics/data/autobackup
   for details.
   Note: This file is ignored for devices older than API 31
   See https://developer.android.com/about/versions/12/backup-restore
-->
<full-backup-content>
    <!--
   <include domain="sharedpref" path="."/>
   <exclude domain="sharedpref" path="device.xml"/>
-->
</full-backup-content>
```

## app/src/main/res/xml/data_extraction_rules.xml

Source: [app/src/main/res/xml/data_extraction_rules.xml](../app/src/main/res/xml/data_extraction_rules.xml). SHA256: `CB1FC47AB4A984530ED60E0E6EE638929C3038290E7E7E0B4B03A3A30FBE7381`.

```xml
<?xml version="1.0" encoding="utf-8"?><!--
   Sample data extraction rules file; uncomment and customize as necessary.
   See https://developer.android.com/about/versions/12/backup-restore#xml-changes
   for details.
-->
<data-extraction-rules>
    <cloud-backup>
        <!-- TODO: Use <include> and <exclude> to control what is backed up.
        <include .../>
        <exclude .../>
        -->
    </cloud-backup>
    <!--
    <device-transfer>
        <include .../>
        <exclude .../>
    </device-transfer>
    -->
</data-extraction-rules>
```
