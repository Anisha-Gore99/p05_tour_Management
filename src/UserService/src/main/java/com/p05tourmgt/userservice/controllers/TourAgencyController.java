package com.p05tourmgt.userservice.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.p05tourmgt.userservice.entities.TourAgency;
import com.p05tourmgt.userservice.entities.User;
import com.p05tourmgt.userservice.services.TourAgencyService;
import com.p05tourmgt.userservice.services.UserServices;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/touragency")
public class TourAgencyController {
	    @Autowired
	    private TourAgencyService tservice;

	    @Autowired
	    private UserServices uservice;
	    
	 // Retrieves a list of all tour agencies.- GET
	    //http://localhost:8081/touragency/getalltouragency
	    @GetMapping("/getalltouragency")
	    public List<TourAgency> getAllAgencies() {
	        return tservice.getAllAgencies();
	    }

	    // Retrieves a tour agency by its associated User ID.- GET
	    //http://localhost:8081/touragency/getByUserId?uid={uid}
	    @GetMapping("/getByUserId")
	    public ResponseEntity<TourAgency> getTourAgencyByUserId(@RequestParam("uid") int uid) {
	        User u = uservice.getById(uid);
	        if (u != null) {
	            TourAgency agency = tservice.getTourAgency(u);
	            if (agency != null) {
	                return ResponseEntity.ok(agency);
	            }
	        }
	        return ResponseEntity.notFound().build();
	    }

	    //POST - register a new tour agency:
	    //http://localhost:8081/touragency/register
	    @PostMapping("/register")
	    public TourAgency registerTourAgency(@RequestBody TourAgency tourAgency) {
	        return tservice.registerTourAgency(tourAgency);
	    }

	    //POST - log in as a tour agency:
	    //http://localhost:8081/touragency/login
	    @PostMapping("/login")
	    public ResponseEntity<?> loginAgency(@RequestBody Map<String, String> credentials) {
	        String username = credentials.get("username");
	        String password = credentials.get("password");

	        TourAgency agency = tservice.loginAgency(username, password);
	        if (agency != null) {
	            return ResponseEntity.ok(agency);
	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password, or not a tour agency");
	        }
	    }
	    

	    // Retrieves a specific tour agency by its unique ID.- GET
	    // http://localhost:8081/touragency/{id}
	    @GetMapping("/{id}")
	    public ResponseEntity<TourAgency> getAgencyById(@PathVariable Integer id) {
	        Optional<TourAgency> agency = tservice.getAgencyById(id);
	        return agency.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }

//	    // Updates an existing tour agency by its ID.- PUT
//	    //http://localhost:8081/touragency/{id}
//	    @PutMapping("/{id}")
//	    public ResponseEntity<TourAgency> updateAgency(@PathVariable Integer id, @RequestBody TourAgency updatedAgency) {
//	        return tservice.getAgencyById(id)
//	            .map(existingAgency -> {
//	                existingAgency.setTour_agency_name(updatedAgency.getTour_agency_name());
//	                existingAgency.setPhone_no(updatedAgency.getPhone_no());
//	                existingAgency.setAgency_email(updatedAgency.getAgency_email());
//	                existingAgency.setAddress(updatedAgency.getAddress());
//	                existingAgency.setLicense_number(updatedAgency.getLicense_number());
//	                // If you intend to update user details via agency update, handle it here
//	                // For example: existingAgency.getUid().setEmail(updatedAgency.getUid().getEmail());
//	                return ResponseEntity.ok(tservice.saveOrUpdateAgency(existingAgency));
//	            })
//	            .orElseGet(() -> ResponseEntity.notFound().build());
//	    }
	    
	    
	    @PutMapping("/{id}")
	    public ResponseEntity<TourAgency> updateAgency(@PathVariable Integer id, @RequestBody TourAgency updatedAgency) {
	        // Use the service to get the existing agency by ID.
	        // The .map() function handles the case where the agency is found.
	        return tservice.getAgencyById(id)
	            .map(existingAgency -> {
	                // Only update a field if the corresponding value is not null in the request body.
	                // This allows for partial updates.
	                if (updatedAgency.getTour_agency_name() != null) {
	                    existingAgency.setTour_agency_name(updatedAgency.getTour_agency_name());
	                }
	                if (updatedAgency.getPhone_no() != null) {
	                    existingAgency.setPhone_no(updatedAgency.getPhone_no());
	                }
	                if (updatedAgency.getAgency_email() != null) {
	                    existingAgency.setAgency_email(updatedAgency.getAgency_email());
	                }
	                if (updatedAgency.getAddress() != null) {
	                    existingAgency.setAddress(updatedAgency.getAddress());
	                }
	                if (updatedAgency.getLicense_number() != null) {
	                    existingAgency.setLicense_number(updatedAgency.getLicense_number());
	                }

	                // If user details are provided in the update, handle them here.
	                if (updatedAgency.getUser() != null) {
	                    // Assuming a one-to-one relationship and the user object is already loaded.
	                    if (updatedAgency.getUser().getUname() != null) {
	                         existingAgency.getUser().setUname(updatedAgency.getUser().getUname());
	                    }
	                    if (updatedAgency.getUser().getPhone_no() != null) {
	                        existingAgency.getUser().setPhone_no(updatedAgency.getUser().getPhone_no());
	                    }
	                    if (updatedAgency.getUser().getEmail() != null) {
	                        existingAgency.getUser().setEmail(updatedAgency.getUser().getEmail());
	                    }
	                    // Note: You would not typically allow a password to be updated this way without
	                    // proper security checks, like re-encoding the password.
	                    // if (updatedAgency.getUser().getPassword() != null) {
	                    //      String encodedPassword = passwordEncoder.encode(updatedAgency.getUser().getPassword());
	                    //      existingAgency.getUser().setPassword(encodedPassword);
	                    // }
	                }

	                // Save the updated agency object to the database.
	                TourAgency savedAgency = tservice.saveOrUpdateAgency(existingAgency);
	                return ResponseEntity.ok(savedAgency);
	            })
	            // If getAgencyById returns an empty Optional, return a 404 Not Found.
	            .orElseGet(() -> ResponseEntity.notFound().build());
	    }
	    
	    
	    // Deletes a tour agency by its ID.- DELETE
	    //http://localhost:8081/touragency/{id}
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteAgency(@PathVariable Integer id) {
	        tservice.deleteAgency(id);
	        return ResponseEntity.noContent().build();
	    }
	    
	    

}
