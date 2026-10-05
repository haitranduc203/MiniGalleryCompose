# Hướng dẫn tự viết lại MiniGalleryCompose

Mục tiêu: tự dựng app Compose có lưới ảnh, search/sort, quyền đọc thư viện và nút Thêm mở Photo Picker để ảnh được chọn xuất hiện ngay. Không có bước xem trước/đồng ý lưu, không tạo bản sao ảnh.

Tài liệu dành cho người đã biết Kotlin cơ bản. Đọc [giải thích chi tiết](CODE_WALKTHROUGH_VI.md) khi cần hiểu tại sao một đoạn code được tổ chức như vậy; dùng [phụ lục source đầy đủ](SOURCE_REFERENCE_VI.md) để đối chiếu implementation.

## 1. Cách dùng tài liệu

Thực hiện theo thứ tự các bước. Ở mỗi bước, tạo đúng package/file, đọc code tương ứng trong phụ lục và hoàn thành tiêu chí kiểm tra trước khi sang bước tiếp theo.

Phụ lục chứa đầy đủ source Kotlin, cấu hình build, Manifest, strings và theme cửa sổ. Ảnh launcher, vector icon và Gradle wrapper dạng binary không được chép thành văn bản trong tài liệu; giữ các file đó từ dự án hoặc template Android ban đầu.

## 2. Chuẩn bị skeleton và build

Mở bản dự án trong `C:\Users\cuong.bui1\IdeaProjects\MiniGalleryCompose` để thực hành, hoặc tạo một Empty Activity Compose mới trong một thư mục riêng. Không ghi đè bản tham chiếu khi luyện viết từ đầu.

Nếu dùng skeleton mới, cần đồng bộ các thông số với bản tham chiếu:

- JDK 21, Gradle wrapper 9.3.1, AGP 9.1.1.
- namespace `com.example.androidtrainingexample`, applicationId `com.example.minigallerycompose`.
- minSdk=29, compileSdk=36 với minor=1, targetSdk=36.
- Compose compiler plugin 2.2.10 và Compose BOM 2026.03.01.
- Activity Compose, Lifecycle ViewModel/runtime/Compose, Coil Compose và coroutines.

Đối chiếu lần lượt settings.gradle.kts, build.gradle.kts ở gốc, gradle/libs.versions.toml và app/build.gradle.kts trong phụ lục. Copy đúng repository/plugin block, không ghép ngẫu nhiên build script của template khác phiên bản AGP.

`local.properties` chỉ cấu hình SDK máy bạn. Không sao chép đường dẫn SDK của người khác một cách cứng nhắc; Android Studio có thể sinh file này. Không đưa thông tin máy cá nhân vào phụ lục source.

**Hoàn thành khi:** Gradle Sync thành công và Empty Activity chạy được trước khi thêm logic thư viện ảnh.

## 3. Tạo model

Tạo package `com.example.androidtrainingexample.minigallery.model` với ba file:

1. MediaImage.kt: data class URI/tên/MIME/size/ngày thêm và hai getter định dạng.
2. PermissionState.kt: sealed interface có Denied, GrantedFull, GrantedPartial.
3. SortOrder.kt: bốn giá trị ngày giảm/tăng và tên tăng/giảm.

Chép định nghĩa đầy đủ từ phụ lục, rồi tự giải thích các điểm: vì sao Uri không phải file path, vì sao dateAddedSeconds phải nhân 1000 trước khi tạo Date, vì sao URI là key tốt hơn id=0 cho nguồn picker.

**Hoàn thành khi:** test formatter đơn giản cho 1024 byte và một timestamp dương cho kết quả hợp lý. Không tạo ImportItem hoặc trạng thái chờ lưu.

## 4. Tạo UiState

Tạo `minigallery.ui.MiniGalleryUiState`. Giữ allImages, displayedImages, searchQuery, sortOrder, isLoading, permissionState, isAddingPhotos, userMessage và hai getter count.

Không đặt Composable, Activity, Bitmap hay Dialog vào state. allImages là dữ liệu hợp nhất; displayedImages là dữ liệu người dùng đang thấy sau filter/sort.

**Hoàn thành khi:** tạo MiniGalleryUiState mặc định, totalCount/displayedCount bằng 0, state.copy tạo state mới mà không sửa state cũ.

## 5. Khai báo repository interface

Tạo `minigallery.data.MediaRepository` với observeImages, queryImages và getMediaInfo trả MediaImage. Ba hàm đều phục vụ đọc dữ liệu.

Interface là điểm để thay nguồn thật bằng fake repository trong unit test. Không để ViewModel gọi trực tiếp ContentResolver.

**Hoàn thành khi:** có thể viết một fake implementation phát list ảnh và trả metadata từ một URI giả mà ViewModel không cần biết query SQL.

## 6. Viết MediaStoreRepositoryImpl

Viết theo thứ tự:

1. Constructor nhận ContentResolver và application Context theo chữ ký hiện có.
2. queryImages dùng withContext(IO), projection, selection IS_PENDING=0 và Cursor.use.
3. Tạo URI bằng ContentUris.withAppendedId, không lấy _data.
4. observeImages chuyển ContentObserver thành callbackFlow.
5. Phát tín hiệu ban đầu, unregister trong awaitClose và thêm conflate/debounce/map/flowOn.
6. getMediaInfo mở/đóng input stream kiểm tra nguồn, query OpenableColumns, lấy MIME rồi trả MediaImage giữ nguyên URI.

Nguồn picker dùng id=0 vì không có ID MediaStore đáng tin cậy dùng chung mọi provider. timestamp là thời điểm thêm ảnh vào danh sách. Metadata thiếu có thể fallback; nguồn không đọc được hoặc quyền bị thu hồi phải báo lỗi.

**Hoàn thành khi:** query được ảnh app có quyền, observer nhận thay đổi, getMediaInfo trả đúng URI nguồn. Không có insert/update/delete/openOutputStream trong production code.

## 7. Viết PermissionHelper và Manifest

Tạo `minigallery.util.PermissionHelper`. Implement getRequiredPermissions và checkPermissionState theo API 29–32, 33, 34+. Trên API 34+, kiểm tra Full trước Partial.

Thêm quyền tương ứng vào Manifest và giới hạn READ_EXTERNAL_STORAGE bằng maxSdkVersion=32. Giữ cấu hình launcher Activity, resource app và metadata service backport như bản tham chiếu.

**Hoàn thành khi:** Denied hiển thị banner nhưng người dùng vẫn có thể mở Photo Picker. Quyền để query toàn thư viện không trở thành điều kiện bắt buộc cho việc chọn một vài ảnh.

## 8. Viết ViewModel theo nguồn dữ liệu

Tạo ViewModel với MutableStateFlow private, StateFlow public, observeJob, deviceImages và pickedImages.

Viết các hàm nhỏ trước: applyFilterAndSort, setSearchQuery, setSortOrder, clearUserMessage và Factory. Sau đó viết publishImages:

```kotlin
val images = (pickedImages + deviceImages).distinctBy { it.uri.toString() }
```

Trong publishImages, tính query mới nếu clearSearch=true, rồi copy cùng lúc allImages, searchQuery, displayedImages. Không cập nhật count thủ công.

Tiếp theo viết startObservingMedia: hủy/join job cũ, bật loading, collect flow, thay deviceImages và publish; lỗi tạo message, cancellation ném lại.

Viết updatePermissionState: Full/Partial quan sát kho ảnh; Denied chỉ xóa deviceImages rồi publish. Đây là điểm tránh làm ảnh picker biến mất khi quay lại từ màn hình chọn ảnh.

**Hoàn thành khi:** fake MediaStore phát list mới không xóa pickedImages; thu hồi quyền không giữ lại ảnh chỉ đến từ MediaStore, nhưng giữ ảnh nguồn picker.

## 9. Viết onPhotosSelected

Guard danh sách rỗng và isAddingPhotos. Bật cờ trước launch. Trong coroutine, take(10), loại URI trùng và đọc từng nguồn:

```kotlin
val image = repository.getMediaInfo(uri)
pickedImages = (pickedImages + image).distinctBy { it.uri.toString() }
publishImages(clearSearch = true)
```

Đặt try/catch theo từng URI để một lỗi không mất các ảnh đọc được. Không nuốt CancellationException. Cuối batch phát message nếu có lỗi/đã giới hạn và dùng finally mở khóa nút Thêm.

**Hoàn thành khi:** một ảnh đọc được xuất hiện ngay trong displayedImages; chọn lại cùng URI không tạo ô trùng; từ khóa cũ được xóa khi thêm thành công; chọn URI lỗi không giữ spinner mãi.

## 10. Viết giao diện Compose

Tạo `minigallery.ui.MiniGalleryScreen` và `minigallery.ui.theme.MiniGalleryTheme`.

Thứ tự nên làm:

1. Theme sáng/tối theo hệ thống.
2. Scaffold, TopAppBar, nút Thêm và insets.
3. OutlinedTextField điều khiển từ state.searchQuery.
4. Menu SortOrder và count.
5. LazyVerticalGrid ba cột, key=URI string, Card và Coil AsyncImage.
6. PermissionBanner, empty state và loading state.
7. rememberSaveable cho URI ảnh chi tiết; ImageDetailDialog, Metadata và GalleryDialog.
8. Preview bằng state mẫu, callbacks rỗng.

Screen nhận bốn callbacks: search, sort, xin quyền, thêm ảnh. Không truyền callback lưu hoặc dismiss import.

**Hoàn thành khi:** preview hiển thị được, click ô ảnh mở chi tiết, Thêm bị khóa chỉ trong lúc isAddingPhotos. Chưa cần launcher hoạt động ở bước preview.

## 11. Nối MainActivity

Kế thừa ComponentActivity. Tạo ViewModel bằng Factory, đăng ký RequestMultiplePermissions và PickMultipleVisualMedia(10) làm thuộc tính Activity.

Trong onCreate: enableEdgeToEdge → setContent → MiniGalleryTheme → collectAsStateWithLifecycle → MiniGalleryScreen. Nối callback picker đến onPhotosSelected; onAddPhotos launch PickVisualMediaRequest(ImageOnly).

Thêm LaunchedEffect để hiện/xóa message và onResume để refresh quyền. Không gọi query từ thân composable.

**Hoàn thành khi:** chọn ảnh từ picker xong về app thấy ảnh trong lưới ngay, không có bước xác nhận. Banner Denied có thể vẫn hiện vì chưa được quyền đọc thư viện rộng hơn.

## 12. Chạy test và tự kiểm tra

```powershell
.\gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug
.\gradlew.bat :app:connectedDebugAndroidTest
```

Test ViewModel cần chứng minh add khi Denied, giữ ảnh khi refresh, loại trùng URI, giới hạn 10, filter/sort, clear search và tiếp tục khi một URI lỗi. Test repository trên thiết bị chứng minh metadata giữ URI nguồn, byte không đổi và không tạo ảnh mới.

Test UI cần chứng minh ô ảnh xuất hiện mà không có dialog nhập. Callback search trong test phải cập nhật state giả để TextField có giá trị mới, giống vòng phản hồi ViewModel thật.

Kịch bản kiểm tra bằng tay khi emulator không bị một tác vụ khác sử dụng:

| Thao tác | Kết quả mong đợi |
|---|---|
| Từ chối quyền thư viện, chọn 2 ảnh qua Thêm | 2 ô ảnh hiển thị, banner quyền vẫn có thể hiện |
| Bấm Thêm rồi hủy picker | Danh sách hiện tại giữ nguyên |
| Chọn lại cùng URI | Không tạo ô trùng |
| Thêm ở lượt tiếp theo | Ảnh cũ và ảnh mới cùng có trong list |
| Đang search một tên không khớp rồi thêm ảnh | Query được xóa để ảnh mới hiển thị |
| Xoay màn hình | State trong ViewModel còn, URI chi tiết có thể khôi phục |
| Thu hồi quyền đọc thư viện | Nguồn thư viện được xóa khỏi state, nguồn picker còn |
| Dừng process và mở app mới | Không kỳ vọng danh sách picker được lưu bền ở phiên bản hiện tại |

## 13. Lỗi thường gặp khi viết lại

| Hiện tượng | Nguyên nhân nên kiểm tra |
|---|---|
| Ảnh picker xuất hiện rồi mất | Flow MediaStore hoặc nhánh Denied ghi đè toàn bộ allImages |
| Chọn ảnh mà không thấy dù không lỗi | Search cũ chưa được xóa, hoặc URI không được publish |
| Gõ vào TextField rồi nội dung reset | Callback chưa cập nhật state.value/searchQuery |
| Chọn nhiều ảnh nhưng chỉ thấy một | Key/dedup dùng id=0 thay vì URI |
| Giao diện đứng khi đọc ảnh | ContentResolver/stream chạy main thread |
| Observer phát nhiều lần hoặc rò rỉ | Thiếu awaitClose unregister hoặc hủy/join job cũ |
| Nút Thêm luôn khóa sau lỗi | Thiếu finally đặt isAddingPhotos=false |
| Catalog có alias nhưng Gradle không tải thư viện | Chưa khai báo implementation trong module app |

## 14. Giới hạn có chủ đích

Danh sách URI picker đang nằm trong bộ nhớ ViewModel, chưa lưu bền và chưa persist quyền URI. Đây là luồng thêm để hiển thị trong phiên app, không phải tạo một thư viện lưu riêng trên đĩa. Muốn giữ qua lần mở lại cần lưu danh sách URI và quản lý quyền/nguồn không còn tồn tại như một tính năng riêng.

Xem [CODE_WALKTHROUGH_VI.md](CODE_WALKTHROUGH_VI.md) để biết ý nghĩa từng hàm, lifecycle, các giới hạn và vị trí mở rộng.