package com.p05tourmgt.userservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.p05tourmgt.userservice.entities.Role;
import com.p05tourmgt.userservice.entities.Tourist;
import com.p05tourmgt.userservice.entities.User;
import com.p05tourmgt.userservice.repositories.RoleRepository;
import com.p05tourmgt.userservice.repositories.TouristRepository;
import com.p05tourmgt.userservice.repositories.UserRepository;

@Service
public class TouristService {

    private final TouristRepository touristRepo;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public TouristService(
        TouristRepository touristRepo,
        UserRepository userRepo,
        RoleRepository roleRepo,
        PasswordEncoder passwordEncoder
    ) {
        this.touristRepo = touristRepo;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public Tourist getTourist(User user) {
        return touristRepo.findByUid(user);
    }

    @Transactional
    public Tourist registerTourist(Tourist tourist) {
        if (tourist == null || tourist.getUid() == null) {
            throw new IllegalArgumentException("Tourist and nested User must not be null");
        }

        // Attach MANAGED 'tourist' role (case-insensitive)
        Role touristRole = roleRepo.findByRnameIgnoreCase("tourist")
            .orElseThrow(() -> new IllegalStateException("Role 'tourist' not found. Seed roles first."));

        User user = tourist.getUid();
        user.setRid(touristRole);

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // Save User first, then Tourist
        User savedUser = userRepo.save(user);
        tourist.setUid(savedUser);

        return touristRepo.save(tourist);
    }

    public List<Tourist> getAllTourists() { return touristRepo.findAll(); }

    public Optional<Tourist> getTouristById(int id) { return touristRepo.findById(id); }

    public Tourist saveOrUpdateTourist(Tourist tourist) { return touristRepo.save(tourist); }

    public void deleteTourist(int id) { touristRepo.deleteById(id); }
}
