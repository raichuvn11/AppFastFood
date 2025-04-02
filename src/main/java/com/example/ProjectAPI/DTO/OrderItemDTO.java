package com.example.ProjectAPI.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItemDTO {
    private Long itemId;
    private String name;
    private int quantity;
    private double price;
    private String img;
}
