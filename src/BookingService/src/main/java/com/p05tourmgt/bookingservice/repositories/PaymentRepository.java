package com.p05tourmgt.bookingservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p05tourmgt.bookingservice.entities.Booking;
import com.p05tourmgt.bookingservice.entities.Payment;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	Optional<Payment> findByBooking(Booking booking);
}
