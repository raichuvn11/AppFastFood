package com.example.ProjectAPI.DTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuItemDTO {
    private Long id;
    private String name;
    private double price;
    private int soldQuantity;
    private LocalDate createDate;
    private Long categoryId;
}

