package com.p05tourmgt.bookingservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p05tourmgt.bookingservice.entities.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
	List<Booking> findByTouristTouristId(int touristId);
}
