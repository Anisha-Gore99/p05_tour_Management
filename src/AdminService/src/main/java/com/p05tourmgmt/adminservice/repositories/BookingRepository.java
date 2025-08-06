package com.p05tourmgmt.adminservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p05tourmgmt.adminservice.entities.Booking;
@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

}
