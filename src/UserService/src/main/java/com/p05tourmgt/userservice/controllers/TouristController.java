package com.p05tourmgt.userservice.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.p05tourmgt.userservice.entities.Tourist;
import com.p05tourmgt.userservice.entities.User;
import com.p05tourmgt.userservice.services.TouristService;
import com.p05tourmgt.userservice.services.UserServices;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/tourist")
public class TouristController {

    private final TouristService tservice;
    private final UserServices uservice;

    public TouristController(TouristService tservice, UserServices uservice) {
        this.tservice = tservice;
        this.uservice = uservice;
    }

    @GetMapping("/getalltourist")
    public List<Tourist> getAllTourists() {
        return tservice.getAllTourists();
    }

    @GetMapping("/getTouristByUserId")
    public ResponseEntity<Tourist> getTouristByUserId(@RequestParam("uid") int uid) {
        User u = uservice.getById(uid);
        if (u != null) {
            Tourist tourist = tservice.getTourist(u);
            if (tourist != null) return ResponseEntity.ok(tourist);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/registertourist")
    public ResponseEntity<?> registerTourist(
        @Valid @RequestBody Tourist tourist,
        org.springframework.validation.BindingResult br
    ) {
        if (br.hasErrors()) {
            Map<String,String> errors = new LinkedHashMap<>();
            br.getFieldErrors().forEach(fe -> errors.put(fe.getField(), fe.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        String email = tourist.getUid().getEmail();
        String uname = tourist.getUid().getUname();
        if (uservice.findByEmail(email) != null) return ResponseEntity.badRequest().body("Email already exists");
        if (uservice.findByUname(uname) != null) return ResponseEntity.badRequest().body("Username already exists");

        Tourist saved = tservice.registerTourist(tourist);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tourist> getTouristById(@PathVariable int id) {
        Optional<Tourist> tourist = tservice.getTouristById(id);
        return tourist.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourist(@PathVariable int id) {
        tservice.deleteTourist(id);
        return ResponseEntity.noContent().build();
    }
}
