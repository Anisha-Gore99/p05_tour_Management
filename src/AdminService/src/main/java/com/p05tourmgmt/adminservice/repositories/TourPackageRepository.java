package com.p05tourmgmt.adminservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.p05tourmgmt.adminservice.entities.TourPackage;
@Repository
public interface TourPackageRepository extends JpaRepository<TourPackage, Integer> {
	boolean existsByTourAgencyId(Integer tourAgencyId);

}
