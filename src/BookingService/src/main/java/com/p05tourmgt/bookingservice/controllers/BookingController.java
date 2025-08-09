package com.p05tourmgt.bookingservice.controllers;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p05tourmgt.bookingservice.entities.Booking;
import com.p05tourmgt.bookingservice.entities.TouristDetails;
import com.p05tourmgt.bookingservice.services.BookingServices;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingServices bookingService;

    //Creates a new tour booking and a corresponding payment record.- POST
    //http://localhost:8083/api/bookings
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Map<String, Object> payload) {
        int touristId = (Integer) payload.get("touristId");
        int scheduleId = (Integer) payload.get("scheduleId");
        int modeId = (Integer) payload.get("modeId");

        // Retrieve the list of LinkedHashMap from the payload
        List<Map<String, Object>> touristDetailsMaps = (List<Map<String, Object>>) payload.get("touristDetails");

        // Manually map each LinkedHashMap to a TouristDetails object
        List<TouristDetails> touristDetailsList = touristDetailsMaps.stream()
                .map(touristMap -> {
                    TouristDetails detail = new TouristDetails();
                    detail.setFname((String) touristMap.get("fname"));
                    detail.setLname((String) touristMap.get("lname"));
                    detail.setAge((Integer) touristMap.get("age"));
                    detail.setGender((String) touristMap.get("gender"));
                    detail.setIdProof((String) touristMap.get("idProof"));
                    return detail;
                })
                .collect(Collectors.toList());

        try {
            Booking booking = bookingService.createBooking(touristId, scheduleId, touristDetailsList, modeId);
            return ResponseEntity.ok(booking);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Retrieves all bookings made by a specific tourist.
    //GET - http://localhost:8083/api/bookings/tourist/{touristId}
    @GetMapping("/tourist/{touristId}")
    public ResponseEntity<List<Booking>> getTouristBookings(@PathVariable int touristId) {
        List<Booking> bookings = bookingService.getTouristBookings(touristId);
        return ResponseEntity.ok(bookings);
    }

    //Updates the details of an existing booking
    //PUT http://localhost:8083/api/bookings/{bookingId}
    @PutMapping("/{bookingId}")
    public ResponseEntity<Booking> updateBooking(@PathVariable int bookingId, @RequestBody Booking updatedBooking) {
        try {
            Booking booking = bookingService.updateBooking(bookingId, updatedBooking);
            return ResponseEntity.ok(booking);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Deletes a booking from the system.
    //DELETE http://localhost:8083/api/bookings/{bookingId}
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable int bookingId) {
        try {
            bookingService.deleteBooking(bookingId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}