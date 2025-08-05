package com.p05tourmgmt.tourservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p05tourmgmt.tourservice.entities.TourPackage;
import com.p05tourmgmt.tourservice.repositories.TourPackageRepository;

@Service
public class TourPackageServiceImpl implements TourPackageService {

	@Autowired
    private TourPackageRepository tourPackageRepository;

    @Override
    public TourPackage createTourPackage(TourPackage tourPackage) {
        return tourPackageRepository.save(tourPackage);
    }

    @Override
    public List<TourPackage> getAllTourPackages() {
        return tourPackageRepository.findAll();
    }

    @Override
    public TourPackage getTourPackageById(int id) {
        return tourPackageRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteTourPackage(int id) {
        tourPackageRepository.deleteById(id);
    }

}
