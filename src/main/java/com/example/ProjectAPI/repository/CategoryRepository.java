package com.example.ProjectAPI.repository;

import com.example.ProjectAPI.model.Category;
import com.example.ProjectAPI.model.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByType(CategoryType categoryType);
    //List<Category> findByCategoryNameContaining(String categoryName);
    //Page<Category> findByCategoryNameContaining(String categoryName, Pageable pageable);

}
