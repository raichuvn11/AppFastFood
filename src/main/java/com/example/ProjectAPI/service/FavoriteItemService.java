package com.example.ProjectAPI.service;

import com.example.ProjectAPI.model.FavoriteItem;
import com.example.ProjectAPI.model.MenuItem;
import com.example.ProjectAPI.model.User;
import com.example.ProjectAPI.repository.FavoriteItemRepository;
import com.example.ProjectAPI.repository.MenuItemRepository;
import com.example.ProjectAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
}
