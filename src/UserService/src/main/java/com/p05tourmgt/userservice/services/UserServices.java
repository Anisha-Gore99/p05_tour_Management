package com.p05tourmgt.userservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.p05tourmgt.userservice.entities.User;
import com.p05tourmgt.userservice.entities.Role;
import com.p05tourmgt.userservice.repositories.RoleRepository;
import com.p05tourmgt.userservice.repositories.UserRepository;
import java.util.Optional;

@Service
public class UserServices {

	@Autowired
    private final UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserServices(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /** Get all users */
    public List<User> getAll() {
        return userRepo.findAll();
    }

    /**
     * Authenticate by username OR email with raw password.
     * Returns the User if credentials are valid; otherwise null.
     * - Supports BCrypt (normal)
     * - Supports legacy plaintext (auto-upgrades to BCrypt on first successful login)
     */
    public User getLogin(String unameOrEmail, String rawPassword) {
        if (unameOrEmail == null || rawPassword == null) return null;

        // Try username first, then email
        User u = userRepo.findByUname(unameOrEmail);
        if (u == null) {
            u = userRepo.findByEmail(unameOrEmail);
        }
        if (u == null) return null;

        String stored = u.getPassword();
        if (stored == null) return null;

        boolean looksBcrypt = stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$");

        if (looksBcrypt) {
            // Normal bcrypt path
            return passwordEncoder.matches(rawPassword, stored) ? u : null;
        } else {
            // Legacy plaintext path
            if (rawPassword.equals(stored)) {
                // Auto-upgrade to BCrypt and persist
                String encoded = passwordEncoder.encode(rawPassword);
                u.setPassword(encoded);
                userRepo.save(u);
                return u;
            }
            return null;
        }
    }

    /** Get by id or null if not found */
    public User getById(int uid) {
        return userRepo.findById(uid).orElse(null);
    }

    /** Alias to getById (avoid Optional.get) */
    public User getOne(int id) {
        return userRepo.findById(id).orElse(null);
    }

    /** Save as-is (no forced encoding) */
    public User save(User u) {
        return userRepo.save(u);
    }

    /** Delete by id – returns true if deleted, false if not found */
    public boolean deleteUser(int id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public User findByUname(String uname) {
        return userRepo.findByUname(uname);
    }

    public User findByPhoneNo(String phoneNo) {
        return userRepo.findByPhoneNo(phoneNo);
    }
   
    @Transactional
    public User saveUser(User user, Integer roleId) {
        // Basic field validation
        if (user.getUname() == null || user.getUname().isBlank()) {
            throw new RuntimeException("Username is required.");
        }
     // Normalize email before checking for duplicates
        user.setEmail(user.getEmail().trim().toLowerCase());
        
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new RuntimeException("Email is required.");
        }
        if (user.getPhoneNo() == null || user.getPhoneNo().isBlank()) {
            throw new RuntimeException("Phone number is required.");
        }

        // Duplicate checks
        if (userRepo.findByUname(user.getUname()) != null) {
            throw new RuntimeException("Username already exists.");
        }
        if (userRepo.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already exists.");
        }
        if (userRepo.findByPhoneNo(user.getPhoneNo()) != null) {
            throw new RuntimeException("Phone number already exists.");
        }

        // Role validation and assignment
        Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));
        user.setRole(role);

        // Password encoding
        String pwd = user.getPassword();
        if (pwd != null && !pwd.isBlank()) {
            boolean isBcrypt = pwd.startsWith("$2a$") || pwd.startsWith("$2b$") || pwd.startsWith("$2y$");
            if (!isBcrypt) {
                user.setPassword(passwordEncoder.encode(pwd));
            }
        }

        // Save and return
        return userRepo.save(user);
    }


    
    
    
}
