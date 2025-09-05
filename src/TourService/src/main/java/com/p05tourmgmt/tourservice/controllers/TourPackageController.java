package com.p05tourmgmt.tourservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.p05tourmgmt.tourservice.entities.TourPackage;
import com.p05tourmgmt.tourservice.services.TourPackageService;


@RestController
@RequestMapping("/api/packages")
public class TourPackageController {
	 @Autowired
	    private TourPackageService tourPackageService;

	    @PostMapping
	    public TourPackage createTourPackage(@RequestBody TourPackage tourPackage) {
	        return tourPackageService.createTourPackage(tourPackage);
	    }

	    @GetMapping
	    public List<TourPackage> getAllTourPackages() {
	        return tourPackageService.getAllTourPackages();
	    }

	    @GetMapping("/{id}")
	    public TourPackage getTourPackageById(@PathVariable int id) {
	        return tourPackageService.getTourPackageById(id);
	    }

	    @DeleteMapping("/{id}")
	    public void deleteTourPackage(@PathVariable int id) {
	        tourPackageService.deleteTourPackage(id);
	    }
	    
	    @GetMapping("/search")
		 public List<TourPackage> searchPackages(@RequestParam(required = false) String destination,
		 @RequestParam(required = false) Integer categoryId) {
		 return tourPackageService.searchPackages(destination, categoryId);
		 }
	    
	   // get tour packages by category
	    @GetMapping("/byCategory/{categoryId}")
	    public ResponseEntity<List<TourPackage>> getPackagesByCategory(@PathVariable int categoryId) {
	        List<TourPackage> packages = tourPackageService.getPackagesByCategoryId(categoryId);
	        if (packages.isEmpty()) {
	            return ResponseEntity.noContent().build(); // Return 204 if no content is found
	        }
	        return ResponseEntity.ok(packages);
	    }
	    
}
