package com.p05tourmgt.bookingservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p05tourmgt.bookingservice.entities.Cancellation;

@Repository
public interface CancellationRepository extends JpaRepository<Cancellation, Integer> {
 
}
