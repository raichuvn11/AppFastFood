package com.example.ProjectAPI.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItemDetail {
    @Id
    private Long itemId;
    private String itemName;
    private int quantity;
    private double itemPrice;
    private double itemAmount;
    @ManyToOne
    private OrderDetail orderDetail;
}
