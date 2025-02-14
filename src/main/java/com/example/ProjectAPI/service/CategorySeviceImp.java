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
    public List<Product> getProductsByCategoryId(int categoryId){
        Optional<Category> category = categoryRepository.findById(categoryId);
        return category.map(Category::getProductList).orElseThrow(() ->
                new RuntimeException("Không tìm thấy id: " + categoryId)
        );
    }
}
