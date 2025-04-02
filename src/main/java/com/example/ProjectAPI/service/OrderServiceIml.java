package com.example.ProjectAPI.service;

import com.example.ProjectAPI.DTO.OrderDTO;
import com.example.ProjectAPI.DTO.OrderItemDTO;
import com.example.ProjectAPI.model.MenuItem;
import com.example.ProjectAPI.model.Order;
import com.example.ProjectAPI.model.OrderItem;
import com.example.ProjectAPI.model.User;
import com.example.ProjectAPI.repository.CartRepository;
import com.example.ProjectAPI.repository.MenuItemRepository;
import com.example.ProjectAPI.repository.OrderRepository;
import com.example.ProjectAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceIml implements IOrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public ResponseEntity<?> createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        Optional<User> userOptional = userRepository.findById(orderDTO.getUserId().toString());
        if (userOptional.isPresent()) {

            List<OrderItem> orderItemList = orderDTO.getOrderItemDTOS().stream().map(orderItemDTO -> {

                MenuItem menuItem = menuItemRepository.findById(orderItemDTO.getItemId().intValue()).orElse(null);

                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setMenuItem(menuItem);
                orderItem.setQuantity(orderItemDTO.getQuantity());

                return orderItem;
            }).toList();

            order.setUser(userOptional.get());
            order.setItems(orderItemList);
            order.setOrderAddress(orderDTO.getOrderAddress());
            order.setOrderTime(orderDTO.getOrderTime());
            order.setStatus(orderDTO.getOrderStatus());
            order.setOrderTotal(orderDTO.getOrderTotal());

            orderRepository.save(order);
            return ResponseEntity.ok(order.getId());
        }
        return ResponseEntity.status(400).body("Không thể tạo order!");
    }

    @Override
    public ResponseEntity<?> updateOrder(Long orderId, String orderStatus) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(orderStatus);
            orderRepository.save(order);
            return ResponseEntity.ok(order.getStatus());
        }
        return ResponseEntity.status(400).body("Không thể update order!");
    }

    @Override
    public ResponseEntity<?> deleteOrder(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            orderRepository.delete(order);
            return ResponseEntity.ok("đã xóa order!");
        }
        return ResponseEntity.status(400).body("Không thể xóa order");
    }

    @Override
    public User findUserByOrderId(Long orderId) {
        return orderRepository.findUserByOrderId(orderId);
    }

    @Override
    public ResponseEntity<?> getOrderByOrderId(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();

            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setUserId(order.getUser().getId());
            orderDTO.setOrderAddress(order.getOrderAddress());
            orderDTO.setOrderTime(order.getOrderTime());
            orderDTO.setOrderStatus(order.getStatus());
            orderDTO.setOrderTotal(order.getOrderTotal());

            List<OrderItemDTO> orderItemDTOList = order.getItems().stream().map(orderItem -> {
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                orderItemDTO.setQuantity(orderItem.getQuantity());
                orderItemDTO.setItemId(orderItem.getMenuItem().getId());
                orderItemDTO.setName(orderItem.getMenuItem().getName());
                orderItemDTO.setPrice(orderItem.getMenuItem().getPrice());
                orderItemDTO.setImg(orderItem.getMenuItem().getImgMenuItem());
                return orderItemDTO;
            }).toList();
            orderDTO.setOrderItemDTOS(orderItemDTOList);

            return ResponseEntity.ok(orderDTO);
        }
        return ResponseEntity.status(404).body("Order not found!");
    }



}
