package com.example.ProjectAPI.controller;

import com.example.ProjectAPI.service.CartServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartServiceImp cartService;

    @GetMapping("/item")
    public ResponseEntity<?> getCartItems(@RequestParam int userId) {
        return cartService.getCartItems(userId);
    }
}
