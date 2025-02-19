package com.example.ProjectAPI.service;

import com.example.ProjectAPI.model.Category;
import com.example.ProjectAPI.model.Product;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<Category> getAllCategories();
    Optional<Category> getCategoryByName(String categoryName);
    Optional<Category> getCategoryById(int categoryId);
    <S extends Category> S save(S entity);
}
