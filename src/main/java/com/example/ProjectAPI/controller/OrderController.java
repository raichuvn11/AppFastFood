package com.example.ProjectAPI.controller;

import com.example.ProjectAPI.DTO.OrderDTO;
import com.example.ProjectAPI.model.User;
import com.example.ProjectAPI.repository.UserRepository;
import com.example.ProjectAPI.service.NotificationService;
import com.example.ProjectAPI.service.OrderServiceIml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderServiceIml orderService;

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/create")
    public ResponseEntity<?> addOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateOrder(@RequestParam Long orderId, @RequestParam String orderStatus) {
        //
        User user = orderService.findUserByOrderId(orderId);
        if (user != null && user.getDeviceToken() != null) {
            notificationService.sendPushNotification(user.getDeviceToken(), orderId);
        }
        return orderService.updateOrder(orderId, orderStatus);
    }

    @GetMapping("")
    public ResponseEntity<?> getOrderByOrderId(@RequestParam Long orderId) {
        return orderService.getOrderByOrderId(orderId);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteOrder(@RequestParam Long orderId) {
        return orderService.deleteOrder(orderId);
    }
}
