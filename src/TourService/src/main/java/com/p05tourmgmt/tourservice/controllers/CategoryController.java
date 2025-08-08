package com.p05tourmgmt.tourservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p05tourmgmt.tourservice.entities.Category;
import com.p05tourmgmt.tourservice.services.CategoryService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	 @Autowired
	    private CategoryService categoryService;

	    @PostMapping
	    public Category createCategory(@RequestBody Category category) {
	        return categoryService.createCategory(category);
	    }

	    @GetMapping
	    public List<Category> getAllCategories() {
	        return categoryService.getAllCategories();
	    }

	    @GetMapping("/{id}")
	    public Category getCategoryById(@PathVariable int id) {
	        return categoryService.getCategoryById(id);
	    }
}
