package com.p05tourmgt.bookingservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;

import com.p05tourmgt.bookingservice.entities.Cancellation;
import com.p05tourmgt.bookingservice.entities.Refund;
import com.p05tourmgt.bookingservice.services.CancellationService;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/cancellations")
public class CancellationController {
    @Autowired
    private CancellationService cancellationService;

    //Creates a new cancellation request for a booking.
    //POST - http://localhost:8083/api/cancellations/createCancellationReqForBooking
    @PostMapping("createCancellationReqForBooking")
    public ResponseEntity<Cancellation> cancelBooking(@RequestBody Map<String, Object> payload) {
        int bookingId = (Integer) payload.get("bookingId");
        String cancelReason = (String) payload.get("cancelReason");

        try {
            Cancellation cancellation = cancellationService.cancelBooking(bookingId, cancelReason);
            return ResponseEntity.ok(cancellation);
        } catch (IllegalArgumentException | IllegalStateException | EntityNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //Retrieves a list of all cancellation requests.
    //GET http://localhost:8083/api/cancellations/getListOfcancellations
//    @GetMapping("getListOfcancellations")
//    public ResponseEntity<List<Cancellation>> getAllCancellations() {
//        return ResponseEntity.ok(cancellationService.getAllCancellations());
//    }

    //Retrieves the details of a specific cancellation request.
    //GET http://localhost:8083/api/cancellations/{cancellationId}
//    @GetMapping("/{cancellationId}")
//    public ResponseEntity<Cancellation> getCancellationDetails(@PathVariable int cancellationId) {
//        return cancellationService.getCancellationDetails(cancellationId)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
    
    //POST http://localhost:8083/api/cancellations/refund/{cancellationId}
    // Initiates a refund process for a specific cancellation.
    @PostMapping("/refund/{cancellationId}")
    public ResponseEntity<Refund> requestRefund(@PathVariable int cancellationId) {
        try {
            Refund refund = cancellationService.requestRefund(cancellationId);
            return ResponseEntity.ok(refund);
        } catch (IllegalArgumentException | IllegalStateException | EntityNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //Deletes a cancellation record
    //DELETE http://localhost:8083/api/cancellations/{cancellationId}
//    @DeleteMapping("/{cancellationId}")
//    public ResponseEntity<Void> deleteCancellation(@PathVariable int cancellationId) {
//        try {
//            cancellationService.deleteCancellation(cancellationId);
//            return ResponseEntity.noContent().build();
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
}