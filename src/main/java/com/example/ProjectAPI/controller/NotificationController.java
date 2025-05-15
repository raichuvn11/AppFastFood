package com.example.ProjectAPI.controller;

import com.example.ProjectAPI.DTO.request.SendNotificationRequest;
import com.example.ProjectAPI.service.impl.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/noti")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send-noti")
    public ResponseEntity<?> sendPromotionNotification(@RequestBody SendNotificationRequest notification){
        return notificationService.sendPromotionNotification(notification);
    }
}
