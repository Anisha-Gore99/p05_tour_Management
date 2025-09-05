package com.p05tourmgmt.adminservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p05tourmgmt.adminservice.entities.TourPackage;
import com.p05tourmgmt.adminservice.repositories.TourPackageRepository;

@Service
public class TourPackageService {
	 @Autowired
	    private TourPackageRepository tourPackageRepository;

	    public List<TourPackage> getAllTourPackages() {
	        return tourPackageRepository.findAll();
	    }
}
