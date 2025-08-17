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

    public Tourist getTourist(User user) {
        return touristRepo.findByUid(user);
    }

    public Optional<Tourist> getTouristById(int id) {
        // For shared-PK: id == user.uid
        return touristRepo.findById(id);
    }

    public void deleteTourist(int id) {
        touristRepo.deleteById(id);
    }

    @Transactional
    public Tourist registerTourist(Tourist t) {
        if (t == null || t.getUid() == null) {
            throw new IllegalArgumentException("Tourist and nested User (uid) are required");
        }

        User u = t.getUid();

        // 1) Ensure role exists (Optional<Role> handling)
        Role role = roleRepo.findByRname("TOURIST")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setRname("TOURIST");
                    return roleRepo.save(r);
                });
        u.setRid(role);

        // 2) Hash password if provided
        if (u.getPassword() != null && !u.getPassword().isBlank()) {
            u.setPassword(passwordEncoder.encode(u.getPassword()));
        }

        // 3) Save the User first
        User savedUser = userRepo.save(u);

        // 4) Link back & share the same PK for Tourist
        t.setUid(savedUser);

        // If you are using shared-PK with @MapsId and Tourist has getId/setId:
        // (If your Tourist uses a separate @Id like 'tid', remove the next line.)
        try {
            // Only call this if your Tourist entity has 'id' as PK mirroring user.uid
            Tourist.class.getMethod("setId", Integer.class); // reflection check to be safe at runtime
            t.getClass().getMethod("setId", Integer.class).invoke(t, savedUser.getUid());
        } catch (Exception ignored) {
            // You are likely using a separate PK (tid); no action needed.
        }

        // 5) Save Tourist
        return touristRepo.save(t);
    }
}
