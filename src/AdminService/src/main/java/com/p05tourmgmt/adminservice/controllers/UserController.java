package com.p05tourmgmt.adminservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p05tourmgmt.adminservice.entities.User;
import com.p05tourmgmt.adminservice.services.UserService;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {
	 @Autowired
	    private UserService userService;

	    @GetMapping
	    public ResponseEntity<List<User>> getAllUsers() {
	        return ResponseEntity.ok(userService.getAllUsers());
	    }

//	    @DeleteMapping("/{uid}")
//	    public ResponseEntity<Void> deleteUser(@PathVariable int uid) {
//	        userService.deleteUser(uid);
//	        return ResponseEntity.noContent().build();
//	    }
}
