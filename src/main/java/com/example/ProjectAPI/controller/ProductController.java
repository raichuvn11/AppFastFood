package com.example.ProjectAPI.controller;

import com.example.ProjectAPI.model.Category;
import com.example.ProjectAPI.model.Product;
import com.example.ProjectAPI.service.CategorySeviceImp;
import com.example.ProjectAPI.service.IProductService;
import com.example.ProjectAPI.service.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prod")
public class ProductController {

    @Autowired
    private ProductServiceImp productService;
    @Autowired
    private CategorySeviceImp categorySeviceImp;

    @PostMapping("/products")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@RequestParam("categoryId") int categoryId) {
        List<Product> productList = productService.getProductsByCategoryId(categoryId);
        if (productList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/top10-bestselling")
    public ResponseEntity<List<Product>> getTop10BestSellingProducts() {
        List<Product> productList = productService.getTop10BestSellingProducts();
        if (productList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/latest-created")
    public ResponseEntity<List<Product>> getLastedCreatedProducts() {
        LocalDateTime daysAgo = LocalDateTime.now().minusDays(7);
        List<Product> productList = productService.getTop10LatestCreatedProducts(daysAgo);
        if (productList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productList);
    }

    @PostMapping("/add-product")
    public ResponseEntity<?> createProduct(@RequestParam("productName") String productName, @RequestParam("productPrice") double productPrice, @RequestParam("categoryId") int categoryId) {
        Optional<Category> category = categorySeviceImp.getCategoryById(categoryId);
        if(category.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category không tồn tại");
        }
        Product product = new Product();
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setCategory(category.get());
        productService.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}
