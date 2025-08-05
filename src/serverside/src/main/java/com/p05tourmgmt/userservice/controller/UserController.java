package com.p05tourmgmt.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.p05tourmgmt.userservice.entities.LoginCheck;
import com.p05tourmgmt.userservice.entities.User;
import com.p05tourmgmt.userservice.services.UserService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService uservice;

    // Get all users
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = uservice.getAll();
        return ResponseEntity.ok(users);
    }

    // Login check
    @PostMapping("/chkLogin")
    public ResponseEntity<User> chkLogin(@RequestBody LoginCheck lcheck) {
        User user = uservice.getLogin(lcheck.getUname(), lcheck.getPassword());
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).body(null); // Unauthorized
        }
    }

    // Get user by ID
    @GetMapping("/getbyid")
    public ResponseEntity<User> getOne(@RequestParam("id") int id) {
        User user = uservice.getOne(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Save user
    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody User u) {
        User saved = uservice.save(u);
        return ResponseEntity.ok(saved);
    }

    // Update user
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        User user = uservice.updateUser(id, updatedUser);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
