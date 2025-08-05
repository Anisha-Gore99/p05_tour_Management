package com.p05tourmgmt.tourservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p05tourmgmt.tourservice.entities.Category;
import com.p05tourmgmt.tourservice.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
	 @Autowired
	    private CategoryRepository categoryRepository;

	    public Category createCategory(Category category) {
	        return categoryRepository.save(category);
	    }

	    public List<Category> getAllCategories() {
	        return categoryRepository.findAll();
	    }

	    public Category getCategoryById(int id) {
	        return categoryRepository.findById(id).orElse(null);
	    }
}
