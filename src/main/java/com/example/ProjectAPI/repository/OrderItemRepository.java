package com.example.ProjectAPI.repository;

import com.example.ProjectAPI.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT SUM(o.quantity) FROM OrderItem o WHERE o.menuItem.id = :menuItemId")
    int countSoldQuantityByMenuItemId(Long menuItemId);
}
