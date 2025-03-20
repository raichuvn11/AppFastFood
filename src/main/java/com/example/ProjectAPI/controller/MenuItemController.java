package com.example.ProjectAPI.controller;

import com.example.ProjectAPI.model.Category;
import com.example.ProjectAPI.model.MenuItem;
import com.example.ProjectAPI.service.CategoryServiceImp;
import com.example.ProjectAPI.service.MenuItemServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/item")
public class MenuItemController {

    @Autowired
    private MenuItemServiceImp MenuItemService;
    @Autowired
    private CategoryServiceImp categoryServiceImp;

    @GetMapping("/list-menu-item")
    public ResponseEntity<List<MenuItem>> getMenuItemsByCategoryId(@RequestParam("categoryId") int categoryId) {
        List<MenuItem> MenuItemList = MenuItemService.getMenuItemsByCategoryId(categoryId);
        if (MenuItemList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(MenuItemList);
    }

    @GetMapping("/top10-bestselling")
    public ResponseEntity<List<MenuItem>> getTop10BestSellingMenuItems() {
        List<MenuItem> MenuItemList = MenuItemService.getTop10BestSellingMenuItems();
        if (MenuItemList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(MenuItemList);
    }

    @GetMapping("/latest-created")
    public ResponseEntity<List<MenuItem>> getLastedCreatedMenuItems() {
        LocalDate daysAgo = LocalDate.now().minusDays(7);
        List<MenuItem> MenuItemList = MenuItemService.getTop10LatestCreatedMenuItems(daysAgo);
        System.out.println(MenuItemList);
        if (MenuItemList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(MenuItemList);
    }

    @PostMapping("/add-menu-item")
    public ResponseEntity<?> createMenuItem(@RequestParam("name") String name, @RequestParam("price") double price, @RequestParam("categoryId") int categoryId) {
        Optional<Category> category = categoryServiceImp.getCategoryById(categoryId);
        if(category.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category không tồn tại");
        }
        MenuItem MenuItem = new MenuItem();
        MenuItem.setName(name);
        MenuItem.setPrice(price);
        MenuItem.setCreateDate(LocalDate.now());
        MenuItem.setCategory(category.get());
        MenuItemService.save(MenuItem);

        return ResponseEntity.status(HttpStatus.CREATED).body(MenuItem);
    }
}
