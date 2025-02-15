package com.example.ProjectAPI.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    private String productName;
    private double productPrice;
    private String productDescription;
    private String productImage;
    private int productQuantity;
    private int quantitySold;
    private LocalDateTime createDate;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    public Product(String productName, double productPrice, String productDescription, String productImage, int productQuantity, int quantitySold, LocalDateTime createDate) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.productQuantity = productQuantity;
        this.quantitySold = quantitySold;
        this.createDate = createDate;
    }
    public Product() {
        super();
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public double getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
    public String getProductDescription() {
        return productDescription;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public String getProductImage() {
        return productImage;
    }
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
    public int getProductQuantity() {
        return productQuantity;
    }
    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
    public int getQuantitySold() {
        return quantitySold;
    }
    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }
    public LocalDateTime getCreateDate() {
        return createDate;
    }
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }


}
