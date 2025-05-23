package com.example.ProjectAPI.repository;

import com.example.ProjectAPI.model.Order;
import com.example.ProjectAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o.user FROM Order o WHERE o.id = :orderId")
    User findUserByOrderId(@Param("orderId") Long orderId);

    List<Order> findByStatusAndUserIdOrderByIdDesc(String status, Long userId);
    List<Order> findByStatusAndUserIdOrderByOrderTimeDesc(String status, Long userId);
    List<Order> findTop3ByUserIdOrderByOrderTimeDesc(Long userId);
}
