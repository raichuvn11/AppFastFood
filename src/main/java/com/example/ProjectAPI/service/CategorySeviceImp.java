package com.example.ProjectAPI.service;

import com.example.ProjectAPI.model.Category;
import com.example.ProjectAPI.model.Product;
import com.example.ProjectAPI.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorySeviceImp implements ICategoryService{
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    @Override
    public <S extends Category> S save(S entity) {
        return categoryRepository.save(entity);
    }

    @Override
    public Optional<Category> getCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId);
    }
}
