package com.p05tourmgt.userservice.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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

import com.p05tourmgt.userservice.entities.LoginCheck;
import com.p05tourmgt.userservice.entities.User;
import com.p05tourmgt.userservice.services.UserServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	    @Autowired
	    private UserServices uservice;

	    // Retrieves a list of all users.- GET
	    // http://localhost:8081/api/user/all
	    @GetMapping("/all")
	    public ResponseEntity<List<User>> getAllUsers() {
	        List<User> users = uservice.getAll();
	        return ResponseEntity.ok(users);
	    }

	    @PostMapping("/createuser")
	    public ResponseEntity<?> createUser(@RequestBody User user) {
	        try {
	            // Log incoming data for debugging
	            System.out.println("Received user: " + user.getUname() + ", Role ID: " +
	                (user.getRole() != null ? user.getRole().getRid() : "null"));

	            // Manual validation
	            if (user.getUname() == null || user.getUname().isBlank() ||
	                user.getEmail() == null || user.getEmail().isBlank() ||
	                user.getPhoneNo() == null || user.getPhoneNo().isBlank()) {
	                return ResponseEntity.badRequest().body("Error: Username, email, and phone number are required.");
	            }

	            if (user.getRole() == null || user.getRole().getRid() == null) {
	                return ResponseEntity.badRequest().body("Error: Role ID is required.");
	            }

	            // Save user
	            User createdUser = uservice.saveUser(user, user.getRole().getRid());
	            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);


	        } catch (DataIntegrityViolationException e) {
	            return ResponseEntity.status(HttpStatus.CONFLICT)
	                                 .body("Error: An account with this email or phone number already exists.");
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                                 .body("Error: " + e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                                 .body("An unexpected error occurred during registration. Please try again later.");
	        }
	    }

	    
	    
	    // Checks user login credentials.-POST
	    // http://localhost:8081/api/user/chkLogin
	    @PostMapping(value = "/chkLogin", consumes = "application/json") 
	    public ResponseEntity<?> chkLogin(@RequestBody LoginCheck lcheck) {
	        if (lcheck == null || lcheck.getUname() == null || lcheck.getPassword() == null) {
	            return ResponseEntity.badRequest().body(
	                java.util.Map.of("message", "Missing uname or password")
	            );
	        }

	        User user = uservice.getLogin(lcheck.getUname(), lcheck.getPassword());
	        if (user == null) {
	            return ResponseEntity.status(401).body(
	                java.util.Map.of("message", "Invalid credentials")
	            );
	        }

	        // hide sensitive fields before returning
	        user.setPassword(null);
	        return ResponseEntity.ok(user);
	    }


	    // Retrieves a user by their ID.- GET
	    // http://localhost:8081/api/user/getbyid?id={id}
	    @GetMapping("/getbyid")
	    public ResponseEntity<User> getOne(@RequestParam("id") int id) {
	        User user = uservice.getOne(id);
	        if (user != null) {
	            return ResponseEntity.ok(user);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    // Saves a new user.- POST
	    // http://localhost:8081/api/user/save
	    @PostMapping("/save")
	    public ResponseEntity<User> saveUser(@RequestBody User u) {
	        User saved = uservice.save(u);
	        return ResponseEntity.ok(saved);
	    }

	    
	    // Deletes a user by their ID.- DELETE
	    // http://localhost:8081/api/user/{id}
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
	        boolean deleted = uservice.deleteUser(id);
	        
	        if (deleted) {
	            return ResponseEntity.noContent().build(); // 204 No Content
	        } else {
	            return ResponseEntity.notFound().build(); // 404 Not Found
	        }
	    }

}
