package com.p05tourmgt.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p05tourmgt.bookingservice.entities.TourPackage;

@Repository
public interface TourPackageRepository extends JpaRepository<TourPackage, Integer> {

}
