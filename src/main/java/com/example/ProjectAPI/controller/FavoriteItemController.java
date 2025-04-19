package com.example.ProjectAPI.controller;

import com.example.ProjectAPI.model.FavoriteItem;
import com.example.ProjectAPI.service.IFavoriteItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteItemController {

    @Autowired
    private IFavoriteItemService favoriteItemService;

    // Thêm sản phẩm yêu thích
    @PostMapping("/{userId}/{menuItemId}")
    public ResponseEntity<FavoriteItem> addFavoriteItem(@PathVariable Long userId, @PathVariable Long menuItemId) {
        FavoriteItem favoriteItem = favoriteItemService.addFavoriteItem(userId, menuItemId);
        return ResponseEntity.ok(favoriteItem);
    }

    // Xóa sản phẩm yêu thích
    @DeleteMapping("/{userId}/{menuItemId}")
    public ResponseEntity<Void> removeFavoriteItem(@PathVariable Long userId, @PathVariable Long menuItemId) {
        favoriteItemService.removeFavoriteItem(userId, menuItemId);
        return ResponseEntity.noContent().build();
    }
}
