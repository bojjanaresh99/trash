package com.Trash.controller;

import com.Trash.dto.DishRequest;
import com.Trash.entity.Dish;
import com.Trash.service.DishService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dishes")
@SecurityRequirement(name = "bearerAuth")  // Swagger lock icon enabled
public class DishController {

    @Autowired
    private DishService dishService;

    // CREATE DISH → Only Admin
    @PostMapping("/create/{adminId}")
    public Dish createDish(
            @PathVariable Long adminId,
            @RequestBody DishRequest req
    ) {
        return dishService.createDish(adminId, req);
    }

    // LIST ALL DISHES OF ADMIN
    @GetMapping("/list/{adminId}")
    public List<Dish> getAllDishes(@PathVariable Long adminId) {
        return dishService.getAdminDishes(adminId);
    }

    // GET ONE DISH
    @GetMapping("/{dishId}")
    public Dish getDish(@PathVariable Long dishId) {
        return dishService.getDish(dishId);
    }

    // UPDATE DISH
    @PutMapping("/update/{dishId}")
    public Dish updateDish(
            @PathVariable Long dishId,
            @RequestBody DishRequest req
    ) {
        return dishService.updateDish(dishId, req);
    }

    // DELETE DISH
    @DeleteMapping("/delete/{dishId}")
    public String deleteDish(@PathVariable Long dishId) {
        dishService.deleteDish(dishId);
        return "Dish deleted successfully";
    }
}
