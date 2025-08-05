package com.p05tourmgmt.tourservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.p05tourmgmt.tourservice.entities.TourPackage;

public interface TourPackageRepository extends JpaRepository<TourPackage, Integer> {

}
