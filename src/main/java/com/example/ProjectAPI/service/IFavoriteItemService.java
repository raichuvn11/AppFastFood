package com.example.ProjectAPI.service;

import com.example.ProjectAPI.model.FavoriteItem;
import org.springframework.http.ResponseEntity;

public interface IFavoriteItemService {
    FavoriteItem addFavoriteItem(Long userId, Long menuItemId);
    void removeFavoriteItem(Long userId, Long menuItemId);
    ResponseEntity<?> getFavoriteItems(Long userId);
}
