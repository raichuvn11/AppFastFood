package com.example.ProjectAPI.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private String description;
    private LocalDate createDate;
    private int soldQuantity;

    private String imgMenuItem;
    @ManyToOne
    @JsonBackReference

    private Category category;
    @ManyToMany(mappedBy = "items")
    private List<Cart> carts;

    @OneToMany(mappedBy = "menuItem")
    @JsonManagedReference(value = "menuitem-favorites")

    private List<FavoriteItem> favoriteItems;
}

