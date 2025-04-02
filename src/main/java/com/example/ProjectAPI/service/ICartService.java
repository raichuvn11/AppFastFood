package com.example.ProjectAPI.service;

import com.example.ProjectAPI.DTO.CartItemDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICartService {
    public ResponseEntity<?> getCartItems(int userId);
}
