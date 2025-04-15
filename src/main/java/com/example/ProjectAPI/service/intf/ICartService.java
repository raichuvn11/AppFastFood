package com.example.ProjectAPI.service.intf;

import org.springframework.http.ResponseEntity;

public interface ICartService {
    public ResponseEntity<?> getCartItems(int userId);
}
