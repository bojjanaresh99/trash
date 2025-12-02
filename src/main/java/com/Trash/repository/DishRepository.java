package com.Trash.repository;

import com.Trash.entity.Dish;
import com.Trash.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findByAdmin(Admin admin);
}
