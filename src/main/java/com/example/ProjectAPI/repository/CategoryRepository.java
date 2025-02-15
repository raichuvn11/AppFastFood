package com.example.ProjectAPI.repository;

import com.example.ProjectAPI.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByCategoryName(String categoryName);
    List<Category> findByCategoryNameContaining(String categoryName);
    Page<Category> findByCategoryNameContaining(String categoryName, Pageable pageable);

}
