package com.p05tourmgt.userservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.p05tourmgt.userservice.entities.LoginCheck;
import com.p05tourmgt.userservice.entities.User;
import com.p05tourmgt.userservice.services.UserServices;

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
	    
	    // Create a new user -POST
	    // http://localhost:8081/api/user/createuser
	    @PostMapping("/createuser")
	    public User createUser(@RequestBody User user) {
	        return uservice.saveUser(user);
	    }

	    // Checks user login credentials.-POST
	    // http://localhost:8081/api/user/chkLogin
	    @PostMapping(value = "/chkLogin", consumes = "application/json") // omit produces => defaults to JSON via Jackson
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

	        // Optional: hide sensitive fields before returning
	        user.setPassword(null);
	        return ResponseEntity.ok(user); // JSON
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
