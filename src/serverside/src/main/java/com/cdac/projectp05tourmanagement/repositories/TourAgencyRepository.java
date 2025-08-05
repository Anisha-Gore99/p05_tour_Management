package com.cdac.projectp05tourmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cdac.projectp05tourmanagement.entities.TourAgency;
import com.cdac.projectp05tourmanagement.entities.User;

@Repository
public interface TourAgencyRepository extends JpaRepository<TourAgency,Integer> {

	@Query("select t from TourAgency t where uid=:u")
	public TourAgency getTourAgency(User u);
	
	
}
