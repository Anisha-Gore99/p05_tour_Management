package com.p05tourmgmt.userservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.p05tourmgmt.userservice.entities.TourAgency;
import com.p05tourmgmt.userservice.entities.User;
import com.p05tourmgmt.userservice.services.TourAgencyService;
import com.p05tourmgmt.userservice.services.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/agency")
public class TourAgencyController {

    @Autowired
    private TourAgencyService tservice;

    @Autowired
    private UserService uservice;
    
    @GetMapping("/ping")
    public String test() {
        return "Agency service is running";
    }


    // ✅ 1. Get TourAgency by user ID
    @GetMapping("/getByUserId")
    public TourAgency getTourAgency(@RequestParam("uid") int uid) {
        User u = uservice.getById(uid);
        return tservice.getTourAgency(u);
    }

    // ✅ 2. Register new agency
    @PostMapping("/register")
    public TourAgency registerAgency(@RequestBody TourAgency agency) {
        return tservice.registerAgency(agency);
    }

    // ✅ 3. Login agency (basic, no JWT)
    @PostMapping("/login")
    public ResponseEntity<?> loginAgency(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        TourAgency agency = tservice.loginAgency(username, password);
        if (agency != null) {
            return ResponseEntity.ok(agency);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
