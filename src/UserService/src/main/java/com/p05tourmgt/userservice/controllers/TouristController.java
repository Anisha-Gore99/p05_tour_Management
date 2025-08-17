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

    @GetMapping("/getTouristByUserId")
    public ResponseEntity<Tourist> getTouristByUserId(@RequestParam("uid") int uid) {
        User u = uservice.getById(uid);
        if (u == null) return ResponseEntity.notFound().build();

        Tourist tourist = tservice.getTourist(u);
        return (tourist != null) ? ResponseEntity.ok(tourist)
                                 : ResponseEntity.notFound().build();
    }

    @PostMapping("/registertourist")
    public ResponseEntity<?> registerTourist(
            @Valid @RequestBody Tourist tourist,
            BindingResult br
    ) {
        if (br.hasErrors()) {
            Map<String,String> errors = new LinkedHashMap<>();
            br.getFieldErrors().forEach(fe -> errors.put(fe.getField(), fe.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        if (tourist.getUid() == null) {
            return ResponseEntity.badRequest().body("User details (uid) are required.");
        }

        String email = tourist.getUid().getEmail();
        String uname = tourist.getUid().getUname();
        if (email == null || email.isBlank()) return ResponseEntity.badRequest().body("Email is required.");
        if (uname == null || uname.isBlank()) return ResponseEntity.badRequest().body("Username is required.");

        if (uservice.findByEmail(email) != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");

        if (uservice.findByUname(uname) != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");

        try {
            Tourist saved = tservice.registerTourist(tourist);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate or integrity constraint violation.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tourist> getTouristById(@PathVariable int id) {
        Optional<Tourist> tourist = tservice.getTouristById(id); // id == user uid
        return tourist.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourist(@PathVariable int id) {
        tservice.deleteTourist(id);
        return ResponseEntity.noContent().build();
    }
}
