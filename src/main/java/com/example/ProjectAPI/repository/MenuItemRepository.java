package com.example.ProjectAPI.repository;

import com.example.ProjectAPI.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

    //List<MenuItem> findByMenuItemNameContaining(String name);
    //Page<MenuItem> findByMenuItemNameContaining(String name,Pageable pageable);
    Optional<MenuItem> findById(Long id);

    @Query("select i from MenuItem i where i.category.id = :categoryId")
    List<MenuItem> findByCategoryId(int categoryId);

    //Optional<MenuItem> findByMenuItemName(String name);
    //Optional<MenuItem> findByCreateDate(Date createAt);

    //top 10 sản phẩm bán chạy nhất
    @Query("select i from MenuItem i order by i.soldQuantity desc limit 10")
    List<MenuItem> findTop10BestSellingMenuItems();

    //top 10 sản phẩm được tạo trong vòng 7 ngày
    @Query("select i from MenuItem i where i.createDate >= :daysAgo order by i.createDate desc limit 10")
    List<MenuItem> findTop10LatestCreatedMenuItems(LocalDate daysAgo);
}
