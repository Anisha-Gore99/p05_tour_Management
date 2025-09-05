package com.p05tourmgmt.adminservice.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.p05tourmgmt.adminservice.entities.PaymentMode;
import com.p05tourmgmt.adminservice.repositories.PaymentModeRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/admin")
public class PaymentModeController {

    @Autowired
    private PaymentModeRepository paymentModeRepository;

    @GetMapping("/payment-modes")
    public ResponseEntity<List<PaymentMode>> getAllModes() {
        return ResponseEntity.ok(paymentModeRepository.findAll());
    }
}
