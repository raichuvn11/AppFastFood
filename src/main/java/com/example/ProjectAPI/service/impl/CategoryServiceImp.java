package com.example.ProjectAPI.service.impl;

import com.example.ProjectAPI.model.Category;
import com.example.ProjectAPI.model.CategoryType;
import com.example.ProjectAPI.repository.CategoryRepository;
import com.example.ProjectAPI.service.intf.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements ICategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryByType(CategoryType categoryName) {
        return categoryRepository.findByType(categoryName);
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
