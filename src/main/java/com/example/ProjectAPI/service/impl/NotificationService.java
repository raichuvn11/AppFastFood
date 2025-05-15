package com.example.ProjectAPI.service.impl;

import com.example.ProjectAPI.DTO.DeviceTokenDTO;
import com.example.ProjectAPI.DTO.request.SendNotificationRequest;
import com.example.ProjectAPI.model.User;
import com.example.ProjectAPI.repository.UserRepository;
import com.google.firebase.messaging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class NotificationService {

    @Autowired
    private UserRepository userRepository;

    public void sendOrderStatusNotification(String deviceToken, Long orderId, String orderStatus) {
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
        try {
            sendNotification(title, body, deviceToken, "order-update");
        } catch (Exception e) {
            System.out.println("Có lỗi khi gửi thông báo: " + e);
        }
    }

    public ResponseEntity<?> sendPromotionNotification(SendNotificationRequest notificationRequest) {
        String title = notificationRequest.getTitle();
        String body = notificationRequest.getBody();

        List<User> userList = userRepository.findAll();

        Set<String> uniqueDeviceTokens = new HashSet<>();
        for (User user : userList) {
            String token = user.getDeviceToken();
            if (token != null && !token.trim().isEmpty()) {
                uniqueDeviceTokens.add(token.trim());
            }
        }
        for (String deviceToken : uniqueDeviceTokens) {
            try {
                sendNotification(title, body, deviceToken, "promotion");
            } catch (FirebaseMessagingException  e) {
                if (e.getMessagingErrorCode() == MessagingErrorCode.UNREGISTERED) {
                    System.out.println("Token đã hết hạn hoặc không còn hợp lệ: " + deviceToken);
                    // xóa token đã hết hạn
                    List<User> usersWithToken = userRepository.findByDeviceToken(deviceToken);
                    for (User u : usersWithToken) {
                        u.setDeviceToken(null);
                        userRepository.save(u);
                    }
                }
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Có lỗi trong quá trình gửi thông báo: " + e.getMessage());
            }
        }
        return ResponseEntity.ok("Đã gửi thông báo thành công cho người dùng.");
    }

    public void sendNotification(String title, String body, String deviceToken, String type) throws FirebaseMessagingException {
        if (deviceToken == null || deviceToken.trim().isEmpty()) {
            throw new IllegalArgumentException("Device token is null or empty!");
        }

        AndroidConfig androidConfig = AndroidConfig.builder()
                .setPriority(AndroidConfig.Priority.HIGH)
                .build();

        Message message = Message.builder()
                .setToken(deviceToken)
                .setAndroidConfig(androidConfig)
                .putData("title", title)
                .putData("body", body)
                .putData("type", type)
                .build();

        String response = FirebaseMessaging.getInstance().send(message);
        System.out.println("Gửi thông báo thành công. Response: " + response);
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

