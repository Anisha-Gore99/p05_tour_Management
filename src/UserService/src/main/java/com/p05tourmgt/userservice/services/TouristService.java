package com.p05tourmgt.userservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.p05tourmgt.userservice.entities.Role;
import com.p05tourmgt.userservice.entities.Tourist;
import com.p05tourmgt.userservice.entities.User;
import com.p05tourmgt.userservice.repositories.RoleRepository;
import com.p05tourmgt.userservice.repositories.TouristRepository;
import com.p05tourmgt.userservice.repositories.UserRepository;

@Service
public class TouristService {

    @Autowired
    private TouristRepository touristRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    TouristService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    // 1. Get a Tourist by its associated User
    public Tourist getTourist(User user) {
        return touristRepository.findByUid(user);
    }

 // 1. Register a new Tourist (Create operation)
    public Tourist registerTourist(Tourist tourist) {
        // Ensure the 'tourist' role exists in your database and is assigned
        Role touristRole = roleRepository.findByRname("tourist");
        if (touristRole == null) {
            // Handle case where role doesn't exist, perhaps create it or throw an exception
            // For now, let's assume it exists or create a placeholder
            touristRole = new Role(0, "tourist"); // Placeholder, ensure proper ID if creating
            roleRepository.save(touristRole); // Save the role if it doesn't exist
        }

        User user = tourist.getUid();
        
        // 🛑 Hash the password before saving for security
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        user.setRid(touristRole); // Set the role for the user

        User savedUser = userRepository.save(user); // Save the user first
        tourist.setUid(savedUser); // Link the saved user to the tourist
        
        return touristRepository.save(tourist); // Save the tourist
    }

    // 2. Login for a tourist
    public Tourist loginTourist(String username, String password) {
        User user = userRepository.findByUname(username); // Find user by username
        
        // Corrected password comparison using matches()
        if (user != null && passwordEncoder.matches(password, user.getPassword()) && user.getRid().getRname().equals("tourist")) {
            return touristRepository.findByUid(user); // Return tourist if credentials and role match
        }
        
        return null; // Return null for invalid credentials or incorrect role
    }

    // 4. Get all tourists (Read operation)
    public List<Tourist> getAllTourists() {
        return touristRepository.findAll();
    }

    // 5. Get a tourist by their ID (Read operation)
    public Optional<Tourist> getTouristById(int id) {
        return touristRepository.findById(id);
    }
    
    // 6. Save or update a tourist (Update operation)
    public Tourist saveOrUpdateTourist(Tourist tourist) {
        return touristRepository.save(tourist);
    }

    // 7. Delete a tourist by their ID (Delete operation)
    public void deleteTourist(int id) {
        touristRepository.deleteById(id);
    }
}