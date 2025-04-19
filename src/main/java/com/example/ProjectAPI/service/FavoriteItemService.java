package com.example.ProjectAPI.service;

import com.example.ProjectAPI.DTO.FavoriteItemDTO;
import com.example.ProjectAPI.model.FavoriteItem;
import com.example.ProjectAPI.model.MenuItem;
import com.example.ProjectAPI.model.User;
import com.example.ProjectAPI.repository.FavoriteItemRepository;
import com.example.ProjectAPI.repository.MenuItemRepository;
import com.example.ProjectAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriteItemService implements IFavoriteItemService {

    @Autowired
    private FavoriteItemRepository favoriteItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public FavoriteItem addFavoriteItem(Long userId, Long menuItemId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<MenuItem> menuItem = menuItemRepository.findById(menuItemId);

        if (user.isPresent() && menuItem.isPresent()) {
            FavoriteItem favoriteItem = new FavoriteItem();
            favoriteItem.setUser(user.get());
            favoriteItem.setMenuItem(menuItem.get());
            favoriteItem.setDateAdded(LocalDate.now());
            return favoriteItemRepository.save(favoriteItem);
        } else {
            throw new RuntimeException("User or MenuItem not found");
        }
    }

    @Override
    public void removeFavoriteItem(Long userId, Long menuItemId) {
        favoriteItemRepository.deleteByUserIdAndMenuItemId(userId, menuItemId);
    }

    @Override
    public ResponseEntity<?> getFavoriteItems(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<FavoriteItem> favoriteItems = favoriteItemRepository.findByUserId(userId);
            List<FavoriteItemDTO> favoriteItemDTOs = favoriteItems.stream().map(favoriteItem ->{
                FavoriteItemDTO favoriteItemDTO = new FavoriteItemDTO();
                favoriteItemDTO.setId(favoriteItem.getId());
                favoriteItemDTO.setUserId(favoriteItem.getUser().getId());
                favoriteItemDTO.setName(favoriteItem.getMenuItem().getName());
                favoriteItemDTO.setPrice(favoriteItem.getMenuItem().getPrice());
                favoriteItemDTO.setImg(favoriteItem.getMenuItem().getImgMenuItem());

                return favoriteItemDTO;
            }).toList();
            return ResponseEntity.ok(favoriteItemDTOs);
        }
        return ResponseEntity.notFound().build();
    }
}
