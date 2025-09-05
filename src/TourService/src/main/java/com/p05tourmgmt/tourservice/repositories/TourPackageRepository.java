package com.p05tourmgmt.tourservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.p05tourmgmt.tourservice.entities.Category;
import com.p05tourmgmt.tourservice.entities.TourPackage;

@Repository
public interface TourPackageRepository extends JpaRepository<TourPackage, Integer> {
    java.util.List<com.p05tourmgmt.tourservice.entities.TourPackage> findAllByTourAgencyId(int tourAgencyId);
    
    List<TourPackage> findByDestinationContainingIgnoreCase(String destination);
    List<TourPackage> findByCategory(Category category);
    List<TourPackage> findByDestinationContainingIgnoreCaseAndCategory(String destination, Category category);
   
    
   // Custom method to find all tour packages by a given category ID
    List<TourPackage> findByCategoryCatId(int categoryId); // Corrected the method name to use cat_id


}
