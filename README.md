# 🍔 **OmniFoodAPI – Hệ thống đặt đồ ăn nhanh**

**OmniFoodAPI** là hệ thống **RESTful API** hỗ trợ người dùng đặt món ăn trực tuyến, tích hợp **thanh toán VNPAY**, **thông báo đẩy trên Android (FCM)**, và đầy đủ chức năng quản lý đơn hàng, tài khoản cá nhân, giỏ hàng và trạng thái vận chuyển.

---

## 📌 **Danh sách chức năng**

### **2.1. 🔐 Đăng nhập**
- Cho phép người dùng đăng nhập bằng **email và mật khẩu** (mã hóa SHA512)
- Hỗ trợ đăng nhập bằng **Google**

### **2.2. 📝 Đăng ký**
- Người dùng tạo tài khoản với các thông tin cơ bản: **tên, email/số điện thoại, mật khẩu**
- Xác thực bằng **OTP**

### **2.3. 🔁 Quên mật khẩu**
- Gửi **mã OTP** về email để khôi phục mật khẩu
- Xác minh và cập nhật mật khẩu mới

### **2.4. 🍱 Quản lý sản phẩm (Xem, Thêm giỏ hàng, Yêu thích)**
- Hiển thị danh sách sản phẩm theo loại, chi tiết sản phẩm  
- Thêm/xoá món ăn khỏi **giỏ hàng** và **danh sách yêu thích**

### **2.5. 🛒 Đặt hàng**
- Tạo đơn hàng từ giỏ hàng  
- Lưu **lịch sử đơn hàng**

### **2.6. 💳 Thanh toán bằng VNPAY**
- Tích hợp **cổng thanh toán VNPAY**
- Tự động cập nhật **trạng thái thanh toán** sau phản hồi từ VNPAY

### **2.7. 📦 Theo dõi trạng thái đơn hàng**
- Cập nhật trạng thái:  
  `Đang xử lý → Đang giao → Hoàn tất → Đã hủy`  
- Người dùng có thể xem trạng thái **theo thời gian thực**

### **2.8. 🔔 Gửi nhận thông báo Android**
- Sử dụng **Firebase Cloud Messaging (FCM)**  
- Gửi thông báo khi:
  - Đơn hàng được tạo
  - Trạng thái đơn hàng thay đổi
  - Có khuyến mãi mới

### **2.9. 🙍‍♂️ Quản lý thông tin cá nhân**
- Xem và chỉnh sửa thông tin tài khoản  
- Đổi mật khẩu  
- Cập nhật ảnh đại diện

---

## 🛠 **Công nghệ sử dụng**

- **Backend**: Spring Boot (Java)  
- **Database**: MySQL  
- **ORM**: Spring Data JPA  
- **Xác thực**: Spring Security + JWT + SHA512  
- **Thanh toán**: VNPAY REST API  
- **Thông báo**: Firebase Cloud Messaging (FCM)  
- **IDE**: IntelliJ IDEA

---


