package com.example.ProjectAPI.controller;

import com.example.ProjectAPI.DTO.MenuItemDTO;
import com.example.ProjectAPI.model.Category;
import com.example.ProjectAPI.model.MenuItem;
import com.example.ProjectAPI.service.impl.CategoryServiceImp;
import com.example.ProjectAPI.service.impl.MenuItemServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<MenuItemDTO>> getTop10BestSellingMenuItems() {
        List<MenuItem> menuItemList = MenuItemService.getTop10BestSellingMenuItems();

        if (menuItemList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Chuyển đổi từ MenuItem sang MenuItemDTO
        List<MenuItemDTO> menuItemDTOList = menuItemList.stream()
                .map(item -> new MenuItemDTO(
                        item.getId(),
                        item.getName(),
                        item.getPrice(),
                        item.getSoldQuantity(),
                        item.getCreateDate(),
                        item.getImgMenuItem(),
                        item.getCategory().getId()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(menuItemDTOList);
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/latest-created")
    public ResponseEntity<List<MenuItemDTO>> getLastedCreatedMenuItems() {
        LocalDate daysAgo = LocalDate.now().minusDays(7);
        List<MenuItem> menuItemList = MenuItemService.getTop10LatestCreatedMenuItems(daysAgo);
        System.out.println(menuItemList);

        if (menuItemList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<MenuItemDTO> menuItemDTOList = menuItemList.stream()
                .map(item -> new MenuItemDTO(
                        item.getId(),
                        item.getName(),
                        item.getPrice(),
                        item.getSoldQuantity(),
                        item.getCreateDate(),
                        item.getImgMenuItem(),
                        item.getCategory().getId()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(menuItemDTOList);
    }

    @PostMapping("/add-menu-item")
    public ResponseEntity<?> createMenuItem(@RequestParam("name") String name, @RequestParam("price") double price, @RequestParam("categoryId") int categoryId, @RequestParam("imgMenuItem") String imgMenuItem) {
        Optional<Category> category = categoryServiceImp.getCategoryById(categoryId);
        if(category.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category không tồn tại");
        }
        MenuItem menuItem = new MenuItem();
        menuItem.setName(name);
        menuItem.setPrice(price);
        menuItem.setImgMenuItem(imgMenuItem);
        menuItem.setCreateDate(LocalDate.now());
        menuItem.setCategory(category.get());
        MenuItemService.save(menuItem);

        // Trả về response chứa thông báo thành công và thông tin
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Thêm món ăn thành công");
        response.put("name", menuItem.getName());
        response.put("price", menuItem.getPrice());
        response.put("categoryId", menuItem.getCategory().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
