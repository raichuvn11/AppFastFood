package com.example.ProjectAPI.service;

import com.example.ProjectAPI.DTO.DeviceTokenDTO;
import com.example.ProjectAPI.model.User;
import com.example.ProjectAPI.repository.UserRepository;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private UserRepository userRepository;

    public void sendPushNotification(String deviceToken, Long orderId) {
        if (deviceToken == null || deviceToken.trim().isEmpty()) {
            System.err.println("Device token is null or empty!");
            return;
        }

        try {
            // Xây dựng thông báo
            Notification notification = Notification.builder()
                    .setTitle("Xác nhận đơn hàng")
                    .setBody("Đơn hàng với ID " + orderId + " đã được xác nhận. Vui lòng theo dõi đơn hàng!.")
                    .build();

            // Thêm cấu hình cho Android (ưu tiên cao, hiển thị ngay)
            AndroidConfig androidConfig = AndroidConfig.builder()
                    .setPriority(AndroidConfig.Priority.NORMAL)
                    .build();

            // Xây dựng message với cả notification và data
            Message message = Message.builder()
                    .setToken(deviceToken)
                    .setNotification(notification)
                    .setAndroidConfig(androidConfig)
                    .putData("orderId", String.valueOf(orderId))
                    .putData("type", "order_update")
                    .build();

            // Gửi thông báo
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Gửi thông báo thành công. Response: " + response);

        } catch (FirebaseMessagingException e) {
            // Xử lý lỗi từ Firebase
            System.err.println("Lỗi khi gửi thông báo Firebase: " + e.getMessage());
            if (e.getMessage().contains("Invalid registration token")) {
                System.err.println("Device token không hợp lệ: " + deviceToken);
            } else if (e.getMessage().contains("NotFound")) {
                System.err.println("Dự án hoặc token không tồn tại.");
            } else {
                System.err.println("Lỗi khác: " + e.getMessage());
            }
            e.printStackTrace();

        } catch (Exception e) {
            // Xử lý các lỗi khác
            System.err.println("Lỗi không xác định khi gửi thông báo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String updateDeviceToken(DeviceTokenDTO deviceTokenDTO) {

        Optional<User> userOptional = userRepository.findById(deviceTokenDTO.getUserId().toString());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setDeviceToken(deviceTokenDTO.getDeviceToken());
            userRepository.save(user);
            return "Token updated successfully!";
        }
        return "User not found!";
    }
}

