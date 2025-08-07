package com.p05tourmgt.userservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.p05tourmgt.userservice.entities.Role;
import com.p05tourmgt.userservice.entities.TourAgency;
import com.p05tourmgt.userservice.entities.User;
import com.p05tourmgt.userservice.repositories.RoleRepository;
import com.p05tourmgt.userservice.repositories.TourAgencyRepository;
import com.p05tourmgt.userservice.repositories.UserRepository;

@Service
public class TourAgencyService {

    @Autowired
    private TourAgencyRepository tourAgencyRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    // 1. Get a TourAgency by its associated User
    public TourAgency getTourAgency(User user) {
        return tourAgencyRepository.findByUid(user);
    }
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public TourAgency registerTourAgency(TourAgency tourAgency) {
        // 1. Find the 'tour_agency' role from the database.
        Role tourAgencyRole = roleRepository.findByRname("tour_agency");
        if (tourAgencyRole == null) {
            throw new RuntimeException("Role 'tour_agency' not found.");
        }

        User user = tourAgency.getUid();

        // 2. Hash the user's password for security.
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 3. Set the correct role for the user.
        user.setRid(tourAgencyRole);

        // 4. Save the User first, then link it to the TourAgency.
        User savedUser = userRepository.save(user);
        tourAgency.setUid(savedUser);

        // 5. Save the new TourAgency entity.
        return tourAgencyRepository.save(tourAgency);
    }

    
    public TourAgency loginAgency(String username, String password) {
        User user = userRepository.findByUname(username);

        // Check if the user exists, password matches, and the role is 'tour_agency'.
        if (user != null && passwordEncoder.matches(password, user.getPassword()) && user.getRid().getRname().equals("tour_agency")) {
            // Find and return the tour agency associated with this user.
            return tourAgencyRepository.findByUid(user);
        }

        // If any of the conditions fail, return null.
        return null;
    }
    
    // 4. Get all tour agencies (Read operation)
    public List<TourAgency> getAllAgencies() {
        return tourAgencyRepository.findAll();
    }

    // 5. Get an agency by its ID (Read operation)
    public Optional<TourAgency> getAgencyById(Integer id) {
        return tourAgencyRepository.findById(id);
    }
    
    // 6. Save or update a tour agency (Update operation)
    public TourAgency saveOrUpdateAgency(TourAgency agency) {
        return tourAgencyRepository.save(agency);
    }

    // 7. Delete an agency by its ID (Delete operation)
    public void deleteAgency(Integer id) {
        tourAgencyRepository.deleteById(id);
    }
}