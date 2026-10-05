# Kết quả kiểm tra — 05/10/2026

Phiên bản hiện tại hiển thị URI picker trực tiếp trong lưới, không có bước xem trước nhập ảnh hoặc tạo bản sao.

- `:app:assembleDebug`: đạt; APK tại outputs/MiniGalleryCompose-debug.apk.
- `:app:testDebugUnitTest`: 17 test đạt (12 ViewModel, 3 formatter/SortOrder, 1 query exception, 1 test mẫu).
- `:app:lintDebug`: đạt, 0 lỗi / 23 cảnh báo; chi tiết tại outputs/verification/lint-results-debug.txt.
- `:app:connectedDebugAndroidTest`: 11 test đạt trên Pixel_6 emulator Android 15 (8 Compose UI, 2 repository, 1 app context).
- Repository test xác nhận URI nguồn và byte không đổi, không tạo bản ghi ảnh mới khi load metadata.
- ViewModel/UI test xác nhận ảnh picker hiển thị khi Denied, giữ khi refresh/revoke, không có dialog nhập và không tạo ô trùng cùng URI.
- Code production không còn ImportItem/ImportStatus, state nhập ảnh, API saveImageCopy hay resource của dialog nhập.

Lệnh kiểm tra:

```powershell
$env:JAVA_TOOL_OPTIONS = '-Djdk.net.unixdomain.tmpdir=C:/__minigallery_unavailable_socket_directory'
.\gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug :app:connectedDebugAndroidTest --console=plain --no-daemon
```

Biến môi trường tạm thời chỉ dùng trong môi trường chạy lệnh của agent để Java chuyển sang TCP loopback. Nó không được ghi vào cấu hình dự án. Build chạy trên staging; các source/build file bàn giao được đối chiếu hash với staging.

Runtime test chạy trên Android 15; chưa xác nhận từng phiên bản Android khác trong lượt này. Ảnh main-screen.png từ lượt chuyển Compose ban đầu minh họa màn hình mặc định; không phải bằng chứng thao tác picker của phiên bản mới.

Tài liệu Markdown được kiểm tra code fence, liên kết file và đối chiếu snapshot source, không chạy lại Gradle cho thay đổi chỉ ở tài liệu.