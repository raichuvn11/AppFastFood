package com.example.ProjectAPI.repository;

import com.example.ProjectAPI.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory(String category);
    List<Product> findByProductNameContaining(String productName);
    Page<Product> findByProductNameContaining(String name, Pageable pageable);
    Optional<Product> findByProductId(int productId);
    Optional<Product> findByProductName(String productName);
    Optional<Product> findByCreateDate(String createDate);
}
