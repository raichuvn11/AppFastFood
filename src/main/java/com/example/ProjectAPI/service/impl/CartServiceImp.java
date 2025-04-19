package com.example.ProjectAPI.service.impl;

import com.example.ProjectAPI.DTO.CartItemDTO;
import com.example.ProjectAPI.model.Cart;
import com.example.ProjectAPI.model.MenuItem;
import com.example.ProjectAPI.model.User;
import com.example.ProjectAPI.repository.CartRepository;
import com.example.ProjectAPI.service.intf.ICartService;
import com.example.ProjectAPI.repository.MenuItemRepository;
import com.example.ProjectAPI.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartServiceImp implements ICartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> getCartItems(int userId) {
        Cart cart = cartRepository.findByUserId((long) userId);
        List<MenuItem> menuItemList = cartRepository.findByCartId(cart.getId());
        List<CartItemDTO> menuItemDTOList = menuItemList.stream().map(menuItem -> {
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setItemId(menuItem.getId());
            cartItemDTO.setName(menuItem.getName());
            cartItemDTO.setQuantity(1);
            cartItemDTO.setPrice(menuItem.getPrice());
            cartItemDTO.setImg(menuItem.getImgMenuItem());
            return cartItemDTO;
        }).toList();
        return ResponseEntity.ok(menuItemDTOList);
    }

    @Override
    @Transactional
    public Map<String, Object> addToCart(Long userId, Long menuItemId) {
        // Tìm user và menu item
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        MenuItem item = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        // Tìm giỏ hàng của user, nếu chưa có thì tạo mới
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setItems(new ArrayList<>());
                    return newCart;
                });

        // Đảm bảo danh sách items không phải là null
        if (cart.getItems() == null) {
            cart.setItems(new ArrayList<>());
        }

        // Kiểm tra xem sản phẩm đã có trong giỏ chưa
        boolean alreadyExists = cart.getItems().stream()
                .anyMatch(existingItem -> existingItem.getId().equals(item.getId()));

        Map<String, Object> response = new HashMap<>();

        if (alreadyExists) {
            // Nếu sản phẩm đã tồn tại trong giỏ hàng
            response.put("success", false);
            response.put("message", "Product already exists in the cart.");
            response.put("userId", userId);
            response.put("menuItemId", menuItemId);
        } else {
            // Nếu sản phẩm chưa có trong giỏ, thêm sản phẩm vào giỏ hàng
            cart.getItems().add(item);
            cartRepository.save(cart);  // Lưu giỏ hàng sau khi thêm sản phẩm

            response.put("userId", userId);
            response.put("menuItemId", menuItemId);
        }

        return response;
    }

}
