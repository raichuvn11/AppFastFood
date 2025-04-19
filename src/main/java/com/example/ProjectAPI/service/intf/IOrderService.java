package com.example.ProjectAPI.service.intf;

import com.example.ProjectAPI.DTO.MenuItemDTO;
import com.example.ProjectAPI.DTO.OrderDTO;
import com.example.ProjectAPI.DTO.RecentOrderItemDTO;
import com.example.ProjectAPI.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IOrderService {
    ResponseEntity<?> createOrder(OrderDTO orderDTO);
    ResponseEntity<?> getOrderByOrderId(Long orderId);
    ResponseEntity<?> updateOrder(Long orderId, String orderStatus, int rating, String review);
    ResponseEntity<?> deleteOrder(Long orderId);
    User findUserByOrderId(Long orderId);
    ResponseEntity<?> getOrdersByStatus(String status, Long userId);
    List<MenuItemDTO> getRecentItemsByUserId(Long userId);
}
