package com.example.ProjectAPI.service.impl;

import com.example.ProjectAPI.DTO.OrderDTO;
import com.example.ProjectAPI.DTO.OrderItemDTO;
import com.example.ProjectAPI.DTO.OrderStatusDTO;
import com.example.ProjectAPI.model.*;
import com.example.ProjectAPI.repository.*;
import com.example.ProjectAPI.service.intf.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private PaymentRepository paymentRepository;

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

            saveOrderDetail(order); // lưu thông tin chi tiết hóa đơn
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
            } else if(orderStatus.equals("confirmed") && order.getPayment()!=null){
                order.getPayment().setStatus("checked-out");
            }

            orderRepository.save(order);

            updateOrderDetail(order);
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
        Optional<OrderDetail> orderOptional = orderDetailRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            OrderDetail order = orderOptional.get();

            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setUserName(order.getUserName());
            orderDTO.setUserPhone(order.getUserPhone());
            orderDTO.setOrderAddress(order.getOrderAddress());
            orderDTO.setOrderTime(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(order.getOrderTime()));
            orderDTO.setOrderStatus(order.getStatus());
            orderDTO.setOrderTotal(order.getOrderTotal());
            orderDTO.setRating(order.getRating());
            orderDTO.setReview(order.getReview());

            List<OrderItemDTO> orderItemDTOList = order.getItemDetails().stream().map(orderItem -> {
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                orderItemDTO.setQuantity(orderItem.getQuantity());
                orderItemDTO.setItemId(orderItem.getItemId());
                orderItemDTO.setName(orderItem.getItemName());
                orderItemDTO.setPrice(orderItem.getItemPrice());
                orderItemDTO.setImg(orderItem.getItemImg());
                return orderItemDTO;
            }).toList();
            orderDTO.setOrderItemDTOS(orderItemDTOList);

            return ResponseEntity.ok(orderDTO);
        }
        return ResponseEntity.status(404).body("Order not found!");
    }

    @Override
    public ResponseEntity<?> getOrdersByStatus(String status, Long userId) {
        List<Order> orderList = orderRepository.findByStatusAndUserIdOrderByIdDesc(status, userId);
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

    private void saveOrderDetail(Order order){
        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setOrderDetailId(order.getId());
        orderDetail.setUserName(order.getUser().getUsername());
        orderDetail.setUserPhone(order.getUser().getPhone());
        orderDetail.setOrderAddress(order.getOrderAddress());
        orderDetail.setOrderTime(order.getOrderTime());
        orderDetail.setStatus(order.getStatus());
        orderDetail.setRating(order.getRating());
        orderDetail.setReview(order.getReview());
        orderDetail.setOrderTotal(order.getOrderTotal());

        double amount = 0;
        List<OrderItemDetail> orderItemDetails = new ArrayList<>();
        for(OrderItem item : order.getItems()){
            OrderItemDetail orderItemDetail = new OrderItemDetail();

            orderItemDetail.setItemId(item.getId());
            orderItemDetail.setItemImg(item.getMenuItem().getImgMenuItem());
            orderItemDetail.setItemName(item.getMenuItem().getName());
            orderItemDetail.setItemPrice(item.getMenuItem().getPrice());
            orderItemDetail.setQuantity(item.getQuantity());
            orderItemDetail.setItemAmount(orderItemDetail.getQuantity() * orderItemDetail.getItemPrice());
            orderItemDetail.setOrderDetail(orderDetail);
            amount += orderItemDetail.getItemAmount();
            orderItemDetails.add(orderItemDetail);
        }
        orderDetail.setOrderAmount(amount);
        orderDetail.setItemDetails(orderItemDetails);
        orderDetailRepository.save(orderDetail);
    }

    private void updateOrderDetail(Order order){
        OrderDetail orderDetail = orderDetailRepository.findByOrderDetailId(order.getId());

        orderDetail.setStatus(order.getStatus());
        orderDetail.setRating(order.getRating());
        orderDetail.setReview(order.getReview());
        orderDetailRepository.save(orderDetail);
    }
}
