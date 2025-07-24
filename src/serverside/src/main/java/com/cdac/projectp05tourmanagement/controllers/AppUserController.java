package com.cdac.projectp05tourmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cdac.projectp05tourmanagement.entities.AppUser;
import com.cdac.projectp05tourmanagement.services.AppUserService;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class AppUserController {
	@Autowired
    private AppUserService appUserService; // Inject the service layer

    @GetMapping("/users") 
    public ResponseEntity<List<AppUser>> getAllUsers() {
        List<AppUser> users = appUserService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        }
        return new ResponseEntity<>(users, HttpStatus.OK); // 200 OK
    }

    @GetMapping("/users/{uid}") 
    public ResponseEntity<AppUser> getUserByUid(@PathVariable("uid") Integer uid) { // Changed Long to Integer
        Optional<AppUser> userData = appUserService.getUserByUid(uid);
        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    @PostMapping("/users") 
    public ResponseEntity<AppUser> createUser(@RequestBody AppUser user) {
        try {
            AppUser savedUser = appUserService.saveUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED); // 201 Created
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/users/{uid}") // Endpoint to delete a user
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("uid") Integer uid) { // Changed Long to Integer
        try {
            appUserService.deleteUser(uid);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
