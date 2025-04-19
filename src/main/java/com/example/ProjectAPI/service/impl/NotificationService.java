package com.example.ProjectAPI.service.impl;

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

    public void sendPushNotification(String deviceToken, Long orderId, String orderStatus) {
        if (deviceToken == null || deviceToken.trim().isEmpty()) {
            System.err.println("Device token is null or empty!");
            return;
        }

        try {
            // Xác định tiêu đề và nội dung dựa vào orderStatus
            String title = "Cập nhật đơn hàng";
            String body;

            switch (orderStatus.toLowerCase()) {
                case "confirmed":
                    body = "Đơn hàng #" + orderId + " đã được xác nhận. Cảm ơn bạn đã đặt hàng!";
                    break;
                case "shipping":
                    body = "Đơn hàng #" + orderId + " đang được giao đến bạn! Vui lòng chú ý điện thoại.";
                    break;
                case "delivered":
                    body = "Đơn hàng #" + orderId + " đã giao thành công! Chúc bạn ngon miệng.";
                    break;
                case "cancelled":
                    body = "Đơn hàng #" + orderId + " đã bị hủy.";
                    break;
                default:
                    body = "Đơn hàng #" + orderId + " đã được cập nhật trạng thái: " + orderStatus;
            }

            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            AndroidConfig androidConfig = AndroidConfig.builder()
                    .setPriority(AndroidConfig.Priority.HIGH)
                    .build();

            Message message = Message.builder()
                    .setToken(deviceToken)
                    .setNotification(notification)
                    .setAndroidConfig(androidConfig)
                    .putData("orderId", String.valueOf(orderId))
                    .putData("type", "order_update")
                    .putData("status", orderStatus)
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Gửi thông báo thành công. Response: " + response);

        } catch (FirebaseMessagingException e) {
            System.err.println("Lỗi khi gửi thông báo Firebase: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
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

