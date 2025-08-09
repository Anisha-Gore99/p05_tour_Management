package com.p05tourmgt.userservice.controllers;

import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.p05tourmgt.userservice.entities.Tourist;
import com.p05tourmgt.userservice.entities.User;
import com.p05tourmgt.userservice.services.TouristService;
import com.p05tourmgt.userservice.services.UserServices;

@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/tourist")
public class TouristController {
    
    @Autowired
    private TouristService tservice;

    @Autowired
    private UserServices uservice;
    
  //Retrieves a list of all tourists.- GET
    //http://localhost:8080/tourist/getalltourist
    @GetMapping("/getalltourist")
    public List<Tourist> getAllTourists() {
        return tservice.getAllTourists();
    }

    // Retrieves a tourist by their associated User ID. - GET
    // http://localhost:8080/tourist/getTouristByUserId?uid={uid}
    @GetMapping("/getTouristByUserId")
    public ResponseEntity<Tourist> getTouristByUserId(@RequestParam("uid") int uid) {
        User u = uservice.getById(uid);
        if (u != null) {
            Tourist tourist = tservice.getTourist(u);
            if (tourist != null) {
                return ResponseEntity.ok(tourist);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // Registers a new tourist account.- POST
    //http://localhost:8080/tourist/registertourist
    @PostMapping("/registertourist")
    public ResponseEntity<?> registerTourist(@RequestBody Tourist tourist) {
        String email = tourist.getUid().getEmail();
        String uname = tourist.getUid().getUname();

        // Check email duplicate
        if (uservice.findByEmail(email) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Email already exists");
        }

        // Check username duplicate
        if (uservice.findByUname(uname) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Username already exists");
        }

        // Save new tourist
        tservice.registerTourist(tourist);
        return ResponseEntity.ok("Registration successful");
    }



    // Login for a tourist - POST
    //http://localhost:8080/tourist/logintourist
    @PostMapping("/logintourist")
    public ResponseEntity<?> loginTourist(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Tourist tourist = tservice.loginTourist(username, password);
        if (tourist != null) {
            return ResponseEntity.ok(tourist);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password, or not a tourist");
        }
    }

    

    //Retrieves a specific tourist by their unique ID.- GET
    // http://localhost:8080/tourist/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Tourist> getTouristById(@PathVariable int id) {
        Optional<Tourist> tourist = tservice.getTouristById(id);
        return tourist.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

 // Updates an existing tourist by their ID.- PUT
 // http://localhost:8080/tourist/{id}
 @PutMapping("/{id}")
 public ResponseEntity<Tourist> updateTourist(@PathVariable int id, @RequestBody Tourist updatedTourist) {
     return tservice.getTouristById(id)
         .map(existingTourist -> {
             // Update only the fields that are provided in the request body
             if (updatedTourist.getFname() != null) {
                 existingTourist.setFname(updatedTourist.getFname());
             }
             if (updatedTourist.getLname() != null) {
                 existingTourist.setLname(updatedTourist.getLname());
             }
             if (updatedTourist.getAddress() != null) {
                 existingTourist.setAddress(updatedTourist.getAddress());
             }
             if (updatedTourist.getDob() != null) {
                 existingTourist.setDob(updatedTourist.getDob());
             }

             // 🛑 CRITICAL: Update the nested User details as well
             User existingUser = existingTourist.getUid();
             User updatedUser = updatedTourist.getUid();
             
             if (updatedUser != null) {
                 if (updatedUser.getUname() != null) {
                     existingUser.setUname(updatedUser.getUname());
                 }
                 if (updatedUser.getEmail() != null) {
                     existingUser.setEmail(updatedUser.getEmail());
                 }
                 if (updatedUser.getPhone_no() != null) {
                     existingUser.setPhone_no(updatedUser.getPhone_no());
                 }
             }

             return ResponseEntity.ok(tservice.saveOrUpdateTourist(existingTourist));
         })
         .orElseGet(() -> ResponseEntity.notFound().build());
 }
    
    // Deletes a tourist by their ID.- DELETE
    // http://localhost:8080/tourist/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourist(@PathVariable int id) {
        tservice.deleteTourist(id);
        return ResponseEntity.noContent().build();
    }
}