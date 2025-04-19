package com.example.ProjectAPI.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String type;
    private String imgCategory;
    private List<MenuItemDTO> menuItems;
}