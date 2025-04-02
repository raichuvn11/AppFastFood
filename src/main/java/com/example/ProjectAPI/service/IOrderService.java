package com.example.ProjectAPI.service;

import com.example.ProjectAPI.DTO.OrderDTO;
import com.example.ProjectAPI.model.User;
import org.springframework.http.ResponseEntity;

public interface IOrderService {
    public ResponseEntity<?> createOrder(OrderDTO orderDTO);
    public ResponseEntity<?> getOrderByOrderId(Long orderId);
    public ResponseEntity<?> updateOrder(Long orderId, String orderStatus);
    public ResponseEntity<?> deleteOrder(Long orderId);
    public User findUserByOrderId(Long orderId);
}
