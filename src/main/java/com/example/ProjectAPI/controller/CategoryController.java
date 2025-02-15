package com.example.ProjectAPI.controller;

import com.example.ProjectAPI.model.Category;
import com.example.ProjectAPI.service.CategorySeviceImp;
import com.example.ProjectAPI.service.ICategoryService;
import org.apache.catalina.connector.Response;
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
    private CategorySeviceImp categoryService;

    @GetMapping()
    public ResponseEntity<?> getAllCategory() {
        List<Category> categoryList = categoryService.getAllCategories();
        if (categoryList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categoryList);
    }

    @PostMapping( "/add-category")
    public ResponseEntity<?> addCategory(@Validated @RequestParam("categoryName")
                                         String categoryName) {
        Optional<Category> optCategory =
                categoryService.getCategoryByName(categoryName);

        if (optCategory.isPresent()) {
            return
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category đã tồn tại trong hệ thống");//return new ResponseEntity<Response>(new Response(false, "Loại sản phẩm này đã tồn tại trong hệ thống", optCategory.get()), HttpStatus.BAD_REQUEST);
        } else {
            Category category = new Category();
            category.setCategoryName(categoryName);
            categoryService.save(category);
            return ResponseEntity.ok().body(category);
        }
    }
}
