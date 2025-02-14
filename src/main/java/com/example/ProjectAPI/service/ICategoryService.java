package com.example.ProjectAPI.service;

import com.example.ProjectAPI.model.Category;
import com.example.ProjectAPI.model.Product;

import java.util.List;

public interface ICategoryService {
    public List<Category> getAllCategories();
    public List<Product> getProductsByCategoryId(int categoryId);
}
