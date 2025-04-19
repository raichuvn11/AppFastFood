package com.example.ProjectAPI.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FavoriteItemDTO {
    private Long id;
    private Long userId;
    private String name;
    private double price;
    private String img;
}
