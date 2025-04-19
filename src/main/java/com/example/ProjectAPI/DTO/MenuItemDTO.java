package com.example.ProjectAPI.DTO;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuItemDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int soldQuantity;
    private LocalDate createDate;
    private String imgMenuItem;
    private Long categoryId;
    private List<Long> userFavoriteIds;
}

