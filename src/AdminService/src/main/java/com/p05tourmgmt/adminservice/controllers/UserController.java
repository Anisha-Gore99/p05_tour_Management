package com.p05tourmgmt.adminservice.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p05tourmgmt.adminservice.entities.User;

import com.p05tourmgmt.adminservice.services.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/admin/users")
public class UserController {
	 @Autowired
	    private UserService userService;

	    @GetMapping
	    public ResponseEntity<List<User>> getAllUsers() {
	        return ResponseEntity.ok(userService.getAllUsers());
	    }

	    
	    @DeleteMapping("/{uid}")
	    public ResponseEntity<?> deleteUser(@PathVariable int uid) {
	        try {
	            userService.deleteUser(uid);
	            return ResponseEntity.noContent().build();  // 204 Success
	        } catch (RuntimeException e) {
	            return ResponseEntity
	                    .status(400)
	                    .body(e.getMessage());  // return meaningful error to React
	        }
	    }


	   
}
