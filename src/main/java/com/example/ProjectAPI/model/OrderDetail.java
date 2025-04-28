package com.example.ProjectAPI.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDetail {
    @Id
    private Long orderDetailId;
    private String userName;
    private String userPhone;
    private String orderAddress;
    private LocalDate orderTime;
    private String status;
    private int rating;
    private String review;
    private double orderAmount;
    private double orderTotal;
    @OneToMany(mappedBy = "orderDetail", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<OrderItemDetail> itemDetails;
}
