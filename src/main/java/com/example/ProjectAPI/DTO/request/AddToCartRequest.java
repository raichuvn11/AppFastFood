package com.example.ProjectAPI.DTO.request;

import lombok.Data;

@Data
public class AddToCartRequest {
    private Long userId;
    private Long menuItemId;
}