package com.p05tourmgmt.adminservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.p05tourmgmt.adminservice.entities.Booking;
import com.p05tourmgmt.adminservice.entities.User;
@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
	 @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE b.tourist_id = :touristId")
	    boolean existsByTouristId(@Param("touristId") Integer touristId);


}
