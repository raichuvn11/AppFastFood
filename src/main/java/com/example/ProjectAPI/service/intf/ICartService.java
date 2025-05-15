package com.example.ProjectAPI.service.intf;

import com.example.ProjectAPI.DTO.CartItemDTO;
import com.example.ProjectAPI.model.Cart;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ICartService {
    ResponseEntity<?> getCartItems(int userId);
    Map<String, Object> addToCart(Long userId, Long menuItemId);
    ResponseEntity<Boolean> deleteCartItem(int userId, long menuItemId);
}
