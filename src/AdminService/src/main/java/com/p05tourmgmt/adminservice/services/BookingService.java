package com.p05tourmgmt.adminservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p05tourmgmt.adminservice.entities.Booking;
import com.p05tourmgmt.adminservice.repositories.BookingRepository;

@Service
public class BookingService {
	 @Autowired
	    private BookingRepository bookingRepository;

	    public List<Booking> getAllBookings() {
	        return bookingRepository.findAll();
	    }
}
