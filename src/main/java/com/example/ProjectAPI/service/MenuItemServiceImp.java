package com.example.ProjectAPI.service;

import com.example.ProjectAPI.model.MenuItem;
import com.example.ProjectAPI.repository.CategoryRepository;
import com.example.ProjectAPI.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MenuItemServiceImp implements IMenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public List<MenuItem> getTop10BestSellingMenuItems(){
        return menuItemRepository.findTop10BestSellingMenuItems();
    }

    @Override
    public List<MenuItem> getTop10LatestCreatedMenuItems(LocalDate daysAgo){
        return menuItemRepository.findTop10LatestCreatedMenuItems(daysAgo);
    }

    @Override
    public List<MenuItem> getMenuItemsByCategoryId(int categoryId){
        return menuItemRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @Override
    public <S extends MenuItem> S save(S entity) {
        return menuItemRepository.save(entity);
    }
}
