package com.p05tourmgt.bookingservice.controllers;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p05tourmgt.bookingservice.entities.Booking;
import com.p05tourmgt.bookingservice.entities.Category;
import com.p05tourmgt.bookingservice.entities.TourAgency;
import com.p05tourmgt.bookingservice.entities.TourPackage;
import com.p05tourmgt.bookingservice.entities.TourSchedule;
import com.p05tourmgt.bookingservice.entities.TouristDetails;
import com.p05tourmgt.bookingservice.services.BookingServices;

import jakarta.persistence.EntityNotFoundException;


import com.p05tourmgmt.bookingservice.dto.BookingDTO;
import com.p05tourmgmt.bookingservice.dto.CategoryDTO;
import com.p05tourmgmt.bookingservice.dto.TourAgencyDTO;
import com.p05tourmgmt.bookingservice.dto.TourPackageDTO;
import com.p05tourmgmt.bookingservice.dto.TourScheduleDTO;
import com.p05tourmgmt.bookingservice.dto.TouristDetailDTO;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingServices bookingService;

    public BookingController(BookingServices bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/tourist/{touristId}/cancelled")
    public ResponseEntity<List<BookingDTO>> getCancelledBookingsForTourist(@PathVariable int touristId) {
        List<BookingDTO> dtoList = bookingService.getCancelledBookingsDTOByTourist(touristId);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/tourist/{touristId}")
    public ResponseEntity<List<BookingDTO>> getBookingsForTourist(@PathVariable int touristId) {
        List<BookingDTO> dtoList = bookingService.getTouristBookings(touristId);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/by-agency/{agencyId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByAgencyId(@PathVariable int agencyId) {
        List<BookingDTO> dtoList = bookingService.getAgencyBookingsDTO(agencyId);
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBooking(@RequestBody Map<String, Object> payload) {
        try {
            int touristId = requiredInt(payload.get("touristId"), "touristId");
            int scheduleId = requiredInt(payload.get("scheduleId"), "scheduleId");

            Object detailsRaw = payload.get("touristDetails");
            if (!(detailsRaw instanceof List)) {
                return ResponseEntity.badRequest().body("touristDetails must be an array");
            }

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> detailsMaps = ((List<?>) detailsRaw).stream()
                .map(x -> (Map<String, Object>) x)
                .collect(Collectors.toList());

            int count = detailsMaps.size();
            if (count == 0 || count > 7) {
                return ResponseEntity.badRequest().body("Invalid number of companions. Must be between 1 and 7.");
            }

            List<TouristDetails> detailEntities = detailsMaps.stream().map(m -> {
                TouristDetails d = new TouristDetails();
                d.setFname(asString(m.get("fname")));
                d.setLname(asString(m.get("lname")));
                d.setAge(requiredInt(m.get("age"), "age"));
                d.setGender(asString(m.get("gender")));
                d.setIdProof(asString(m.get("idProof")));
                String nationality = asString(m.get("nationality"));
                if (nationality == null || nationality.trim().isEmpty()) {
                    throw new IllegalArgumentException("Nationality is required for each companion.");
                }
                d.setNationality(nationality);
                return d;
            }).collect(Collectors.toList());

            BookingDTO dto = bookingService.createBookingDTO(touristId, scheduleId, detailEntities);
            return ResponseEntity.ok(dto);

        } catch (IllegalArgumentException | ClassCastException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Entity not found: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Unexpected error");
        }
    }

    @PostMapping("/{bookingId}/confirm")
    public ResponseEntity<String> confirmBooking(@PathVariable int bookingId) {
        try {
            bookingService.confirmBooking(bookingId);
            return ResponseEntity.ok("Booking confirmed successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Booking not found with ID: " + bookingId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to confirm booking: " + e.getMessage());
        }
    }

    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable int bookingId) {
        try {
            bookingService.cancelBooking(bookingId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to cancel booking: " + e.getMessage());
        }
    }

    @PostMapping("/{bookingId}/restore")
    public ResponseEntity<?> restoreBooking(@PathVariable int bookingId) {
        bookingService.restoreBooking(bookingId);
        return ResponseEntity.ok("Booking restored");
    }

    // Helper methods for payload parsing
    private static int requiredInt(Object v, String field) {
        if (v == null) throw new IllegalArgumentException(field + " is required");
        if (v instanceof Number) return ((Number) v).intValue();
        try { return Integer.parseInt(v.toString().trim()); }
        catch (NumberFormatException ex) { throw new IllegalArgumentException(field + " must be a number"); }
    }

    private static String asString(Object v) {
        return v == null ? null : v.toString();
    }
}