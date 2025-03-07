package com.example.ProjectAPI.controller;

import com.example.ProjectAPI.model.Category;
import com.example.ProjectAPI.model.CategoryType;
import com.example.ProjectAPI.service.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cate")
public class CategoryController {

    @Autowired
    private CategoryServiceImp categoryService;

    @GetMapping()
    public ResponseEntity<?> getAllCategory() {
        List<Category> categoryList = categoryService.getAllCategories();
        if (categoryList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categoryList);
    }

    @PostMapping("/add-category")
    public ResponseEntity<?> addCategory(@Validated @RequestParam("type") String type) {
        try {
            CategoryType categoryType = CategoryType.valueOf(type);

            Optional<Category> optCategory = categoryService.getCategoryByType(categoryType);
            if (optCategory.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category đã tồn tại trong hệ thống");
            } else {
                Category category = new Category();
                category.setType(categoryType);
                categoryService.save(category);
                return ResponseEntity.ok().body(category);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CategoryType không hợp lệ.");
        }
    }
}
