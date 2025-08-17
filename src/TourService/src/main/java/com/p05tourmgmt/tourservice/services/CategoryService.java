package com.p05tourmgmt.tourservice.services;

import java.util.List;

import com.p05tourmgmt.tourservice.entities.Category;

public interface CategoryService {

	Category createCategory(Category category);
    List<Category> getAllCategories();
    Category getCategoryById(int id);
}
