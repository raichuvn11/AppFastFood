package com.example.ProjectAPI.service;

import com.example.ProjectAPI.model.Category;
import com.example.ProjectAPI.model.Product;

import java.time.LocalDateTime;
import java.util.List;

public interface IProductService {
    public List<Product> getTop10BestSellingProducts();
    public List<Product> getTop10LatestCreatedProducts(LocalDateTime daysAgo);
    public List<Product> getProductsByCategoryId(int categoryId);
    <S extends Product> S save(S entity);
}
