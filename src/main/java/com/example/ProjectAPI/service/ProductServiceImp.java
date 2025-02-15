package com.example.ProjectAPI.service;

import com.example.ProjectAPI.model.Category;
import com.example.ProjectAPI.model.Product;
import com.example.ProjectAPI.repository.CategoryRepository;
import com.example.ProjectAPI.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Product> getTop10BestSellingProducts(){
        return productRepository.findTop10BestSellingProducts();
    }

    @Override
    public List<Product> getTop10LatestCreatedProducts(LocalDateTime daysAgo){
        return productRepository.findTop10LatestCreatedProducts(daysAgo);
    }

    @Override
    public List<Product> getProductsByCategoryId(int categoryId){
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public <S extends Product> S save(S entity) {
        return productRepository.save(entity);
    }
}
