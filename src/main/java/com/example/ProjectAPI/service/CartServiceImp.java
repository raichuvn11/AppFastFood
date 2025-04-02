package com.example.ProjectAPI.service;

import com.example.ProjectAPI.DTO.CartItemDTO;
import com.example.ProjectAPI.DTO.MenuItemDTO;
import com.example.ProjectAPI.model.Cart;
import com.example.ProjectAPI.model.MenuItem;
import com.example.ProjectAPI.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImp implements ICartService {

    @Autowired
    private CartRepository cartRepository;

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
}
