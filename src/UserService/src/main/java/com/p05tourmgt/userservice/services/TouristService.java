package com.p05tourmgt.userservice.services;

import com.p05tourmgt.userservice.entities.Role;
import com.p05tourmgt.userservice.entities.Tourist;
import com.p05tourmgt.userservice.entities.User;
import com.p05tourmgt.userservice.repositories.RoleRepository;
import com.p05tourmgt.userservice.repositories.TouristRepository;
import com.p05tourmgt.userservice.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TouristService {

    private final TouristRepository touristRepo;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    
    private static final String PLACEHOLDER_PASSWORD = "Temp@123"; 

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

    public List<Tourist> getAll() {
        return touristRepo.findAll();
    }

    public Tourist getTouristByUserId(int uid) {
        Optional<User> userOpt = userRepo.findById(uid);
        if (userOpt.isEmpty()) return null;

        User user = userOpt.get();
        return touristRepo.findByUid(user).orElse(null);
    }

    
    @Transactional
    public void deleteTourist(int id) {
        touristRepo.deleteById(id);
    }

    @Transactional
    public Integer registerTourist(Tourist t) {
        if (t == null || t.getUid() == null) {
            throw new IllegalArgumentException("Tourist and nested User (uid) are required");
        }

        User incomingUser = t.getUid();

        // 1) Ensure role exists
        Role role = roleRepo.findByRname("TOURIST")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setRname("TOURIST");
                    return roleRepo.save(r);
                });
        incomingUser.setRole(role);

        // 2) Check if user already exists
        User existingUser = userRepo.findById(incomingUser.getUid()).orElse(null);

        User userToLink;
        if (existingUser != null) {
            // Use existing user directly
            userToLink = existingUser;
        } else {
            // Handle password safely
            if (incomingUser.getPassword() == null || incomingUser.getPassword().isBlank() ||
                PLACEHOLDER_PASSWORD.equals(incomingUser.getPassword())) {
                incomingUser.setPassword(null);
            } else {
                incomingUser.setPassword(passwordEncoder.encode(incomingUser.getPassword()));
            }

            // Save new user
            userToLink = userRepo.save(incomingUser);
        }

        // 3) Link user to tourist
        t.setUid(userToLink);
        
        System.out.println("Saving tourist for user UID: " + userToLink.getUid());
        System.out.println("Tourist details: " + t.getFname() + ", " + t.getDob() + ", " + t.getAddress());

        // 4) Save tourist and return ID
        Tourist savedTourist = touristRepo.save(t);
        return savedTourist.getTid();
    }

    public Optional<Tourist> getTouristById(int id) {
        return touristRepo.findById(id);
    }
    
}
