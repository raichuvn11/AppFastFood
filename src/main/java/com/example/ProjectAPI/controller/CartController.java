package com.example.ProjectAPI.controller;

import com.example.ProjectAPI.service.impl.CartServiceImp;
import com.example.ProjectAPI.DTO.request.AddToCartRequest;
import com.example.ProjectAPI.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartServiceImp cartService;

    @GetMapping("/item")
    public ResponseEntity<?> getCartItems(@RequestParam int userId) {
        return cartService.getCartItems(userId);
    }
    @PostMapping("/add")
    public ResponseEntity<Object> addToCart(@RequestBody AddToCartRequest request) {
        try {
            // Gọi service để thêm sản phẩm vào giỏ và nhận dữ liệu trả về
            Map<String, Object> response = cartService.addToCart(request.getUserId(), request.getMenuItemId());

            // Kiểm tra nếu sản phẩm đã tồn tại
            boolean success = !response.containsKey("success") || (boolean) response.get("success");
            String message = success
                    ? "Item added to cart successfully"
                    : "Product already exists in the cart";

            // Xoá key "success" trong response nếu có (tránh lặp lại)
            response.remove("success");
            response.remove("message");

            // Tạo response cuối cùng
            Map<String, Object> result = new HashMap<>();
            result.put("success", success);
            result.put("message", message);
            result.put("data", response);

            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            // Trả về lỗi nếu có exception khác (user/menu item không tồn tại)
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            error.put("data", null);

            return ResponseEntity.badRequest().body(error);
        }
    }

}
