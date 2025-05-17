🍔 OmniFoodAPI – Hệ thống đặt đồ ăn nhanh
OmniFoodAPI là hệ thống RESTful API hỗ trợ người dùng đặt món ăn trực tuyến, tích hợp thanh toán VNPAY, thông báo đẩy trên thiết bị Android, và đầy đủ chức năng quản lý đơn hàng, tài khoản cá nhân. Đồng thời cho có thể quản lý sản phẩm, và quán lý trạng thái các đơn hàng
📌 Danh sách chức năng
2.1. 🔐 Đăng nhập
Cho phép người dùng đăng nhập bằng email và mật khẩu (mã hóa bằng SHA512), hoặc đăng nhập bằng google

2.2. 📝 Đăng ký
Người dùng tạo tài khoản với thông tin cơ bản: tên, email/số điện thoại, mật khẩu.
Xác thực bằng OTP

2.3. 🔁 Quên mật khẩu
Cho phép gửi mã OTP về email khôi phục mật khẩu.
Xác minh và cập nhật mật khẩu mới.

2.4. 🍱 Quản lý sản phẩm (Xem, Thêm giỏ hàng, Yêu thích)
Hiển thị danh sách sản phẩm theo loại, chi tiết sản phẩm.
Thêm/xoá món ăn khỏi giỏ hàng và danh sách yêu thích.

2.5. 🛒 Đặt hàng
Tạo đơn hàng từ giỏ hàng.
Lưu lịch sử đơn hàng.

2.6. 💳 Thanh toán bằng VNPAY
Tích hợp cổng thanh toán VNPAY.
Tự động cập nhật trạng thái thanh toán sau phản hồi từ VNPAY.

2.7. 📦 Theo dõi trạng thái đơn hàng
Cập nhật trạng thái đơn: Đang xử lý → Đang giao → Hoàn tất và Đã hủy.
Người dùng xem trạng thái theo thời gian thực.

2.8. 🔔 Gửi nhận thông báo Android
Sử dụng Firebase Cloud Messaging (FCM).
Gửi thông báo khi đơn hàng được tạo, trạng thái thay đổi, khuyến mãi.

2.11. 🙍‍♂️ Quản lý thông tin cá nhân
Xem và chỉnh sửa thông tin cá nhân.
Đổi mật khẩu, cập nhật avatar.

🛠 Công nghệ sử dụng
🔹 Backend: Spring Boot (Java)

🔹 Database: MySQL

🔹 Thanh toán: VNPAY REST API

🔹 Thông báo: Firebase Cloud Messaging (FCM)
