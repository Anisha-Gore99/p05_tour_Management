package com.p05tourmgmt.adminservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p05tourmgmt.adminservice.entities.Booking;
import com.p05tourmgmt.adminservice.services.BookingService;

@RestController
@RequestMapping("/api/admin/bookings")
public class BookingController {
	 @Autowired
	    private BookingService bookingService;

	    @GetMapping
	    public ResponseEntity<List<Booking>> getAllBookings() {
	        return ResponseEntity.ok(bookingService.getAllBookings());
	    }
}
