package com.example.ProjectAPI.repository;

import com.example.ProjectAPI.model.FavoriteItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteItemRepository extends JpaRepository<FavoriteItem, Long> {
    List<FavoriteItem> findByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM FavoriteItem f WHERE f.user.id = :userId AND f.menuItem.id = :menuItemId")
    void deleteByUserIdAndMenuItemId(Long userId, Long menuItemId);
}