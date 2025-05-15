package com.example.ProjectAPI.repository;

import com.example.ProjectAPI.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    OrderDetail findByOrderDetailId(long orderId);
}
