package com.example.ProjectAPI.repository;

import com.example.ProjectAPI.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByProductNameContaining(String name);
    Page<Product> findByProductNameContaining(String name,Pageable pageable);

    @Query("select p from Product p where p.category.categoryId = :categoryId")
    List<Product> findByCategoryId(int categoryId);

    Optional<Product> findByProductName(String name);
    Optional<Product> findByCreateDate(Date createAt);

    //top 10 sản phẩm bán chạy nhất
    @Query("select p from Product p order by p.quantitySold desc limit 10")
    List<Product> findTop10BestSellingProducts();

    //top 10 sản phẩm được tạo trong vòng 7 ngày
    @Query("select p from Product p where p.createDate <= :daysAgo order by p.createDate desc limit 10")
    List<Product> findTop10LatestCreatedProducts(LocalDateTime daysAgo);
}
