package com.p05tourmgt.userservice.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.p05tourmgt.userservice.entities.TourAgency;
import com.p05tourmgt.userservice.entities.User;
import com.p05tourmgt.userservice.services.TourAgencyService;
import com.p05tourmgt.userservice.services.UserServices;

@RestController
@RequestMapping("api/touragency")
public class TourAgencyController {

    private final TourAgencyService tservice;
    private final UserServices uservice;

    public TourAgencyController(TourAgencyService tservice, UserServices uservice) {
        this.tservice = tservice;
        this.uservice = uservice;
    }

    // CREATE (registration)
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody TourAgency agency,
                                      org.springframework.validation.BindingResult br) {
        if (br.hasErrors()) {
            Map<String,String> errors = new LinkedHashMap<>();
            br.getFieldErrors().forEach(fe -> errors.put(fe.getField(), fe.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        User u = agency.getUser();
        if (u == null) return ResponseEntity.badRequest().body("Account details (user) are required");

        // Normalize user phone for uniqueness
        if (u.getPhoneNo() != null) {
            u.setPhoneNo(u.getPhoneNo().replaceAll("\\D", ""));
        }

        // Uniqueness checks on User (not agency_email/phone_no)
        if (uservice.findByEmail(u.getEmail()) != null)
            return ResponseEntity.badRequest().body("Email already exists");
        if (uservice.findByUname(u.getUname()) != null)
            return ResponseEntity.badRequest().body("Username already exists");
        if (uservice.findByPhoneNo(u.getPhoneNo()) != null)
            return ResponseEntity.badRequest().body("Phone number already exists");

        TourAgency saved = tservice.registerAgency(agency);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // READs
    @GetMapping("/getalltouragency")
    public List<TourAgency> getAllAgencies() { return tservice.getAllAgencies(); }

    @GetMapping("/getByUserId")
    public ResponseEntity<TourAgency> getTourAgencyByUserId(@RequestParam("uid") int uid) {
        User u = uservice.getById(uid);
        if (u != null) {
            TourAgency agency = tservice.getTourAgency(u);
            if (agency != null) return ResponseEntity.ok(agency);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourAgency> getAgencyById(@PathVariable Integer id) {
        Optional<TourAgency> agency = tservice.getAgencyById(id);
        return agency.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgency(@PathVariable Integer id) {
        tservice.deleteAgency(id);
        return ResponseEntity.noContent().build();
    }
}

