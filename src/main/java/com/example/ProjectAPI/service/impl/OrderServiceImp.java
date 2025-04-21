package com.example.ProjectAPI.service.impl;

import com.example.ProjectAPI.DTO.OrderDTO;
import com.example.ProjectAPI.DTO.OrderItemDTO;
import com.example.ProjectAPI.DTO.OrderStatusDTO;
import com.example.ProjectAPI.model.MenuItem;
import com.example.ProjectAPI.model.Order;
import com.example.ProjectAPI.model.OrderItem;
import com.example.ProjectAPI.model.User;
import com.example.ProjectAPI.repository.MenuItemRepository;
import com.example.ProjectAPI.repository.OrderRepository;
import com.example.ProjectAPI.repository.UserRepository;
import com.example.ProjectAPI.service.intf.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImp implements IOrderService {

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
            order.setStatus(orderDTO.getOrderStatus());
            order.setOrderTotal(orderDTO.getOrderTotal());
            order.setOrderTime(LocalDate.now());
            orderRepository.save(order);
            return ResponseEntity.ok(order.getId());
        }
        return ResponseEntity.status(400).body("Không thể tạo order!");
    }

    @Override
    public ResponseEntity<?> updateOrder(Long orderId, String orderStatus, int rating, String review) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(orderStatus);
            order.setRating(rating);
            order.setReview(review);

            if(orderStatus.equals("delivered")){
                for(OrderItem item : order.getItems()){
                    MenuItem menuItem = item.getMenuItem();
                    int currentSold = menuItem.getSoldQuantity();
                    menuItem.setSoldQuantity(currentSold + item.getQuantity());
                }
            }
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
            orderDTO.setUserName(order.getUser().getUsername());
            orderDTO.setUserPhone(order.getUser().getPhone());
            orderDTO.setOrderAddress(order.getOrderAddress());
            orderDTO.setOrderTime(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(order.getOrderTime()));
            orderDTO.setOrderStatus(order.getStatus());
            orderDTO.setOrderTotal(order.getOrderTotal());
            orderDTO.setRating(order.getRating());
            orderDTO.setReview(order.getReview());

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

    @Override
    public ResponseEntity<?> getOrdersByStatus(String status, Long userId) {
        List<Order> orderList = orderRepository.findByStatusAndUserIdOrderByOrderTimeDesc(status, userId);
        if (orderList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<OrderStatusDTO> orderStatusDTOList = orderList.stream().map(order -> {
            OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
            orderStatusDTO.setId(order.getId());
            orderStatusDTO.setStatus(order.getStatus());
            orderStatusDTO.setDate(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(order.getOrderTime()));
            orderStatusDTO.setTotal(order.getOrderTotal());
            return orderStatusDTO;
        }).toList();

        return ResponseEntity.ok(orderStatusDTOList);
    }

}
