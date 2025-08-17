package com.p05tourmgt.userservice.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.p05tourmgt.userservice.entities.User;
import com.p05tourmgt.userservice.repositories.UserRepository;

@Service
public class UserServices {

    private final UserRepository userRepo;
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

    /**
     * Create or update a user with password encoding.
     * If the password already looks like BCrypt, it’s kept as-is.
     */
    public User saveUser(User user) {
        String pwd = user.getPassword();
        if (pwd != null && !pwd.isBlank()) {
            boolean looksBcrypt = pwd.startsWith("$2a$") || pwd.startsWith("$2b$") || pwd.startsWith("$2y$");
            if (!looksBcrypt) {
                user.setPassword(passwordEncoder.encode(pwd));
            }
        }
        return userRepo.save(user);
    }
}
