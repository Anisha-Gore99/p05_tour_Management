package com.p05tourmgmt.tourservice.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p05tourmgmt.tourservice.entities.Category;
import com.p05tourmgmt.tourservice.entities.TourPackage;
import com.p05tourmgmt.tourservice.repositories.CategoryRepository;
import com.p05tourmgmt.tourservice.repositories.TourPackageRepository;

@Service
public class TourPackageServiceImpl implements TourPackageService {

	@Autowired
    private TourPackageRepository tourPackageRepository;
	
	@Autowired
    private CategoryRepository categoryRepository;

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

    public List<TourPackage> searchPackages(String destination, Integer categoryId) {
        Category category = null;
        if (categoryId != null) {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
            if (optionalCategory.isPresent()) {
                category = optionalCategory.get();
            }
        }

        if (destination != null && category != null) {
            return tourPackageRepository.findByDestinationContainingIgnoreCaseAndCategory(destination, category);
        } else if (destination != null) {
            return tourPackageRepository.findByDestinationContainingIgnoreCase(destination);
        } else if (category != null) {
            return tourPackageRepository.findByCategory(category);
        } else {
            return tourPackageRepository.findAll();
        }
    }
    
    @Override
    public List<TourPackage> getPackagesByCategoryId(int categoryId) {
        return tourPackageRepository.findByCategoryCatId(categoryId); // Corrected the call to the repository
    }    

}
