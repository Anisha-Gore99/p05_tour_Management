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
	    //http://localhost:8080/touragency/getalltouragency
	    @GetMapping("/getalltouragency")
	    public List<TourAgency> getAllAgencies() {
	        return tservice.getAllAgencies();
	    }

	    // Retrieves a tour agency by its associated User ID.- GET
	    //http://localhost:8080/touragency/getByUserId?uid={uid}
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

	    @PostMapping("/register")
	    public TourAgency registerTourAgency(@RequestBody TourAgency tourAgency) {
	        return tservice.registerTourAgency(tourAgency);
	    }

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
	    // http://localhost:8080/touragency/{id}
	    @GetMapping("/{id}")
	    public ResponseEntity<TourAgency> getAgencyById(@PathVariable Integer id) {
	        Optional<TourAgency> agency = tservice.getAgencyById(id);
	        return agency.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }

	    // Updates an existing tour agency by its ID.- PUT
	    //http://localhost:8080/touragency/{id}
	    @PutMapping("/{id}")
	    public ResponseEntity<TourAgency> updateAgency(@PathVariable Integer id, @RequestBody TourAgency updatedAgency) {
	        return tservice.getAgencyById(id)
	            .map(existingAgency -> {
	                existingAgency.setTour_agency_name(updatedAgency.getTour_agency_name());
	                existingAgency.setPhone_no(updatedAgency.getPhone_no());
	                existingAgency.setAgency_email(updatedAgency.getAgency_email());
	                existingAgency.setAddress(updatedAgency.getAddress());
	                existingAgency.setLicense_number(updatedAgency.getLicense_number());
	                // If you intend to update user details via agency update, handle it here
	                // For example: existingAgency.getUid().setEmail(updatedAgency.getUid().getEmail());
	                return ResponseEntity.ok(tservice.saveOrUpdateAgency(existingAgency));
	            })
	            .orElseGet(() -> ResponseEntity.notFound().build());
	    }
	    
	    // Deletes a tour agency by its ID.- DELETE
	    //http://localhost:8080/touragency/{id}
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteAgency(@PathVariable Integer id) {
	        tservice.deleteAgency(id);
	        return ResponseEntity.noContent().build();
	    }
	    
	    

}
