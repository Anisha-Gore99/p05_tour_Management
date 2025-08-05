package com.p05tourmgmt.userservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.p05tourmgmt.userservice.entities.Tourist;
import com.p05tourmgmt.userservice.services.TouristService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/tourist")
public class TouristController {

    @Autowired
    private TouristService tservice;

    // Register new tourist
    @PostMapping("/register")
    public Tourist registerTourist(@RequestBody Tourist tourist) {
        return tservice.registerTourist(tourist);
    }

    // Login tourist
    @PostMapping("/login")
    public ResponseEntity<?> loginTourist(@RequestBody Map<String, String> credentials) {
        String uname = credentials.get("username");
        String password = credentials.get("password");

        Tourist tourist = tservice.loginTourist(uname, password);
        if (tourist != null) {
            return ResponseEntity.ok(tourist);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    // Get by UID
    @GetMapping("/getByUserId")
    public Tourist getByUserId(@RequestParam("uid") int uid) {
        return tservice.getTouristByUid(uid);
    }
}
