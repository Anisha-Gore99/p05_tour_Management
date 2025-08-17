// src/main/java/com/p05tourmgt/bookingservice/controllers/BookingController.java
package com.p05tourmgt.bookingservice.controllers;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.p05tourmgt.bookingservice.entities.Booking;
import com.p05tourmgt.bookingservice.entities.TouristDetails;
import com.p05tourmgt.bookingservice.services.BookingServices;

import jakarta.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingServices bookingService;

    public BookingController(BookingServices bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBooking(@RequestBody Map<String, Object> payload) {
        try {
            int touristId  = requiredInt(payload.get("touristId"),  "touristId");
            int scheduleId = requiredInt(payload.get("scheduleId"), "scheduleId");
            int modeId     = requiredInt(payload.get("modeId"),     "modeId");

            Object detailsRaw = payload.get("touristDetails");
            if (!(detailsRaw instanceof List)) return ResponseEntity.badRequest().body("touristDetails must be an array");

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> detailsMaps =
                ((List<?>) detailsRaw).stream().map(x -> (Map<String, Object>) x).collect(Collectors.toList());

            if (detailsMaps.isEmpty()) return ResponseEntity.badRequest().body("touristDetails must not be empty");

            List<TouristDetails> detailEntities = detailsMaps.stream().map(m -> {
                TouristDetails d = new TouristDetails();
                d.setFname(asString(m.get("fname")));
                d.setLname(asString(m.get("lname")));
                d.setAge(requiredInt(m.get("age"), "age"));
                d.setGender(asString(m.get("gender")));
                d.setIdProof(asString(m.get("idProof")));
                return d;
            }).collect(Collectors.toList());

            Booking booking = bookingService.createBooking(touristId, scheduleId, detailEntities, modeId);

            Map<String, Object> out = new LinkedHashMap<>();
            out.put("bookingId", booking.getBookingId());
            // You can also include "availableAfter" if you pass it back from the service.
            return ResponseEntity.ok(out);

        } catch (IllegalArgumentException | ClassCastException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Unexpected error");
        }
    }

    // ===== helpers =====
    private static int requiredInt(Object v, String field) {
        if (v == null) throw new IllegalArgumentException(field + " is required");
        if (v instanceof Number) return ((Number) v).intValue();
        try { return Integer.parseInt(v.toString().trim()); }
        catch (NumberFormatException ex) { throw new IllegalArgumentException(field + " must be a number"); }
    }
    private static String asString(Object v) { return v == null ? null : v.toString(); }
    
    @PostMapping("/{bookingId}/confirm")
    public ResponseEntity<?> confirmBooking(@PathVariable int bookingId) {
        bookingService.setStatus(bookingId, "CONFIRMED");
        return ResponseEntity.noContent().build();
    }

}
