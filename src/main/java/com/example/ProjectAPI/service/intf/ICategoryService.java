package com.example.ProjectAPI.service.intf;

import com.example.ProjectAPI.model.Category;
import com.example.ProjectAPI.model.CategoryType;
import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<Category> getAllCategories();
    Optional<Category> getCategoryByType(CategoryType categoryName);
    Optional<Category> getCategoryById(int categoryId);
    <S extends Category> S save(S entity);
}
