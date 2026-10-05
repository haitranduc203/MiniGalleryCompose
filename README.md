# MiniGalleryCompose

MiniGallery dùng Jetpack Compose. Nút **Thêm ảnh** mở Photo Picker; ảnh được chọn hiển thị ngay trong lưới từ URI nguồn. Không có hộp thoại xem trước nhập ảnh hoặc thao tác lưu bản sao.

## Mở dự án

Mở thư mục MiniGalleryCompose bằng Android Studio hoặc IntelliJ IDEA có Android plugin, dùng JDK 21 và chờ Gradle Sync. minSdk=29, compileSdk=36.1, targetSdk=36.

applicationId là `com.example.minigallerycompose`; namespace/package Kotlin là `com.example.androidtrainingexample`, cho phép cài song song với bản XML.

## Tài liệu chi tiết bằng tiếng Việt

- [Hướng dẫn tự viết lại dự án](docs/REBUILD_GUIDE_VI.md): các bước tạo project, model, repository, ViewModel, Compose UI và tiêu chí kiểm tra.
- [Giải thích từng lớp và hàm](docs/CODE_WALKTHROUGH_VI.md): cấu trúc, luồng dữ liệu, ghép nguồn picker/MediaStore, quyền, lifecycle và test.
- [Phụ lục source đầy đủ](docs/SOURCE_REFERENCE_VI.md): code và cấu hình tại thời điểm viết tài liệu để đối chiếu.
- [Đối chiếu kiến thức Android Basic và Content Provider](docs/ANDROID_BASIC_COMPONENTS_MAPPING_VI.md): kiến thức đã áp dụng, luồng query MediaStore/Photo Picker và mức đáp ứng Bài 5.
- [Kết quả kiểm tra](VERIFICATION.md).

## Hành vi

Lưới ba cột, tìm kiếm tên, bốn chế độ sắp xếp, chi tiết metadata, quyền ảnh Android 10/13/14+ và Photo Picker tối đa 10 ảnh mỗi lượt. Ảnh đã chọn được giữ khi refresh thư viện/quyền trong phiên ViewModel; thêm thành công xóa từ khóa cũ để ảnh không bị ẩn bởi bộ lọc.

Coil đọc URI trực tiếp. Không có layout XML, ViewBinding hay RecyclerView. XML còn dùng cho Manifest, chuỗi, icon, theme cửa sổ và backup. Danh sách picker chưa được lưu qua process restart; source không bị sao chép hoặc thay đổi.

## Kiểm tra

```powershell
.\gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug
.\gradlew.bat :app:connectedDebugAndroidTest
```

APK hiện tại: `outputs/MiniGalleryCompose-debug.apk`.

Thư mục outputs chứa APK và báo cáo kiểm tra cục bộ, không được commit lên Git. Sau khi clone, build APK bằng Gradle; file APK được tạo ở app/build/outputs/apk/debug/app-debug.apk.
