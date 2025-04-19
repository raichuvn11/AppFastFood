package com.example.ProjectAPI.controller;

import com.example.ProjectAPI.DTO.MenuItemDTO;
import com.example.ProjectAPI.DTO.OrderDTO;
import com.example.ProjectAPI.DTO.RecentOrderItemDTO;
import com.example.ProjectAPI.model.User;
import com.example.ProjectAPI.service.impl.CouponServiceImp;
import com.example.ProjectAPI.service.impl.NotificationService;
import com.example.ProjectAPI.service.impl.OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderServiceImp orderService;

    @Autowired
    private CouponServiceImp couponService;

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/create")
    public ResponseEntity<?> addOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateOrder(@RequestParam Long orderId, @RequestParam String status, @RequestParam int rating, @RequestParam String review) {
        //
        User user = orderService.findUserByOrderId(orderId);
        if (user != null && user.getDeviceToken() != null && review.isEmpty()) {
            notificationService.sendPushNotification(user.getDeviceToken(), orderId, status);
        }
        return orderService.updateOrder(orderId, status, rating, review);
    }

    @GetMapping("")
    public ResponseEntity<?> getOrderByOrderId(@RequestParam Long orderId) {
        return orderService.getOrderByOrderId(orderId);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteOrder(@RequestParam Long orderId) {
        return orderService.deleteOrder(orderId);
    }

    @GetMapping("/list-status")
    public ResponseEntity<?> getOrdersByStatus(@RequestParam String status, @RequestParam Long userId) {
        return orderService.getOrdersByStatus(status, userId);
    }

    @GetMapping("/apply-code")
    public ResponseEntity<?> applyCouponCode(@RequestParam String code) {
        return couponService.applyCoupon(code);
    }
    @GetMapping("/recent-items/{userId}")
    public ResponseEntity<?> getRecentOrderItems(@PathVariable Long userId) {
        try {
            List<MenuItemDTO> items = orderService.getRecentItemsByUserId(userId);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch recent order items.");
        }
    }
}
