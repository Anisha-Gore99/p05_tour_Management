package com.p05tourmgmt.adminservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p05tourmgmt.adminservice.entities.TourPackage;
import com.p05tourmgmt.adminservice.services.TourPackageService;

@RestController
@RequestMapping("/api/admin/tourpackages")
public class TourPackageController {
	 @Autowired
	    private TourPackageService tourPackageService;

	    @GetMapping
	    public ResponseEntity<List<TourPackage>> getAllTourPackages() {
	        return ResponseEntity.ok(tourPackageService.getAllTourPackages());
	    }
}
