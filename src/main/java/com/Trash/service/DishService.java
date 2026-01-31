package com.Trash.service;

import com.Trash.dto.DishRequest;
import com.Trash.entity.Admin;
import com.Trash.entity.Dish;
import com.Trash.repository.AdminRepository;
import com.Trash.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepo;

    @Autowired
    private AdminRepository adminRepo;

    // Create Dish under Admin
    public Dish createDish(Long adminId, DishRequest req) {
        Admin admin = adminRepo.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + adminId));

        Dish dish = new Dish();
        dish.setDishName(req.getDishName());
        dish.setImageUrl(req.getImageUrl());
        dish.setPrice(req.getPrice());
        dish.setAdmin(admin);

        return dishRepo.save(dish);
    }

    // Get all dishes created by this admin
    public List<Dish> getAdminDishes(Long adminId) {
        Admin admin = adminRepo.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + adminId));

        return dishRepo.findByAdmin(admin);
    }

    // Get single dish
    public Dish getDish(Long dishId) {
        return dishRepo.findById(dishId)
                .orElseThrow(() -> new RuntimeException("Dish not found with id: " + dishId));
    }

    // Update dish (admin must own dish — ownership check optional)
    public Dish updateDish(Long dishId, DishRequest req) {
        Dish dish = dishRepo.findById(dishId)
                .orElseThrow(() -> new RuntimeException("Dish not found with id: " + dishId));

        if (req.getDishName() != null) dish.setDishName(req.getDishName());
        if (req.getImageUrl() != null) dish.setImageUrl(req.getImageUrl());
        if (req.getPrice() != null) dish.setPrice(req.getPrice());

        return dishRepo.save(dish);
    }

    // Delete dish
    public void deleteDish(Long id) {
        if (!dishRepo.existsById(id)) {
            throw new RuntimeException("Dish not found with id: " + id);
        }
        dishRepo.deleteById(id);
    }
}
