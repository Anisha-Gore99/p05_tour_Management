package com.p05tourmgmt.tourservice.services;

import java.util.List;

import com.p05tourmgmt.tourservice.entities.TourPackage;

public interface TourPackageService {

	 TourPackage createTourPackage(TourPackage tourPackage);
	    List<TourPackage> getAllTourPackages();
	    TourPackage getTourPackageById(int id);
	    void deleteTourPackage(int id);
}
