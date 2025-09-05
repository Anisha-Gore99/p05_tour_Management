package com.p05tourmgt.bookingservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p05tourmgt.bookingservice.entities.Booking;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    /**
     * Finds bookings by internal UID (if used separately from touristId).
     */
    List<Booking> findByTouristUid(int uid);

    /**
     * Finds all bookings for a given tourist ID.
     * Equivalent to: SELECT b FROM Booking b WHERE b.tourist.touristId = ?1
     */
    List<Booking> findByTouristTouristId(int touristId);

    /**
     * Finds bookings for a tourist with a specific status (e.g., CANCELLED).
     */
    List<Booking> findByTouristTouristIdAndStatus(int touristId, String status);

    /**
     * Finds all bookings by the ID of the tour agency associated with the tour package.
     * Traverses: Booking → TourSchedule → TourPackage → TourAgency
     */
    List<Booking> findByTourScheduleTourPackageTourAgencyTourAgencyId(int tourAgencyId);
}

