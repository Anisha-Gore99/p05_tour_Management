package com.p05tourmgt.userservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.p05tourmgt.userservice.entities.TourAgency;
import com.p05tourmgt.userservice.entities.User;

@Repository
public interface TourAgencyRepository extends JpaRepository<TourAgency, Integer> {
	// Finds a TourAgency by the associated User object.
	TourAgency findByUser(User user);

}