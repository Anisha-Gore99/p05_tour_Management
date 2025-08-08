package com.p05tourmgmt.tourservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p05tourmgmt.tourservice.entities.TourPackage;
import com.p05tourmgmt.tourservice.services.TourPackageService;
@CrossOrigin(origins = "http://localhost:3000")
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
}
