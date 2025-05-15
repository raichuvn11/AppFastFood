package com.example.ProjectAPI.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate orderTime;
    private String status;
    private int rating;
    private String review;
    private String orderAddress;
    private double orderTotal;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<OrderItem> items;
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;
}

