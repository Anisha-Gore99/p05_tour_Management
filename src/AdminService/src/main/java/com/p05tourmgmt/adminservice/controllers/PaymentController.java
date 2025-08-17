package com.p05tourmgmt.adminservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p05tourmgmt.adminservice.entities.Payment;
import com.p05tourmgmt.adminservice.services.PaymentService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/admin")
public class PaymentController {
    
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payments")
    public ResponseEntity<?> testPayments() {
        try {
            List<Payment> payments = paymentService.getAllPayments();
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            e.printStackTrace(); // Log in console
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}

