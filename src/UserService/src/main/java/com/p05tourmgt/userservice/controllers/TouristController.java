package com.p05tourmgt.userservice.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.p05tourmgt.userservice.entities.Tourist;
import com.p05tourmgt.userservice.entities.User;
import com.p05tourmgt.userservice.services.TouristService;
import com.p05tourmgt.userservice.services.UserServices;


@RestController
@RequestMapping("api/tourist")
public class TouristController {

    private final TouristService tservice;
    private final UserServices uservice;

    public TouristController(TouristService tservice, UserServices uservice) {
        this.tservice = tservice;
        this.uservice = uservice;
    }

    @GetMapping("/getalltourist")
    public List<Tourist> getAllTourists() {
        return tservice.getAll();
    }
    
    @GetMapping("/getTouristByUser/{uid}")
    public ResponseEntity<?> getTouristByUser(@PathVariable int uid) {
        try {
            Tourist tourist = tservice.getTouristByUserId(uid);
            if (tourist == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tourist profile not found for user ID: " + uid);
            }
            return ResponseEntity.ok(tourist);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error fetching tourist profile.");
        }
    }

    @PostMapping("/registertourist")
    public ResponseEntity<?> registerTourist(
            @Valid @RequestBody Tourist tourist,
            BindingResult br
    ) {
        if (br.hasErrors()) {
            Map<String, String> errors = new LinkedHashMap<>();
            br.getFieldErrors().forEach(fe -> errors.put(fe.getField(), fe.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        User incomingUser = tourist.getUid();
        if (incomingUser == null || incomingUser.getUid() == null) {
            return ResponseEntity.badRequest().body("User details (uid) are required.");
        }

        // Check if user already exists
        User existingUser = uservice.getById(incomingUser.getUid());
        boolean isNewUser = (existingUser == null);

        if (isNewUser) {
            // Only check for duplicates if it's a new user
            if (uservice.findByEmail(incomingUser.getEmail()) != null)
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");

            if (uservice.findByUname(incomingUser.getUname()) != null)
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        try {
            //Let the service handle whether to save or reuse the user
            Integer touristId = tservice.registerTourist(tourist);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("touristId", touristId));
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Duplicate or integrity constraint violation.");
        } catch (Exception ex) {
            ex.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while registering the tourist.");
        }
    }



    @DeleteMapping("deltouristbytourist/{id}")
    public ResponseEntity<Void> deleteTourist(@PathVariable int id) {
        tservice.deleteTourist(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/profile/{touristId}")
    public ResponseEntity<Tourist> getTouristProfile(@PathVariable int touristId) {
        Optional<Tourist> tourist = tservice.getTouristById(touristId);
        return tourist.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
