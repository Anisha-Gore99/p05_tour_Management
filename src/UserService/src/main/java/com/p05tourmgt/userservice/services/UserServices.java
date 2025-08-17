package com.p05tourmgt.userservice.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.p05tourmgt.userservice.entities.User;
import com.p05tourmgt.userservice.repositories.UserRepository;

@Service
public class UserServices {

    private final UserRepository urepo;
    private final PasswordEncoder passwordEncoder;

    public UserServices(UserRepository urepo, PasswordEncoder passwordEncoder) {
        this.urepo = urepo;
        this.passwordEncoder = passwordEncoder;
    }

    /** Get all users */
    public List<User> getAll() {
        return urepo.findAll();
    }

    /**
     * Authenticate by username OR email + raw password.
     * Returns the User if credentials are valid, else null.
     */
    public User getLogin(String unameOrEmail, String rawPassword) {
        // Try by username first, then by email
        User user = urepo.findByUname(unameOrEmail);
        if (user == null) {
            user = urepo.findByEmail(unameOrEmail);
        }
        if (user == null) return null;

        // Compare raw vs encoded
        boolean ok = passwordEncoder.matches(rawPassword, user.getPassword());
        return ok ? user : null;
    }

    /** Get by id or null if not found */
    public User getById(int uid) {
        return urepo.findById(uid).orElse(null);
    }

    /** Alias to getById (avoid Optional.get) */
    public User getOne(int id) {
        return urepo.findById(id).orElse(null);
    }

    /** Save without forcing encode (use saveUser for proper encoding) */
    public User save(User u) {
        return urepo.save(u);
    }

    /** Delete by id – returns true if deleted, false if not found */
    public boolean deleteUser(int id) {
        if (urepo.existsById(id)) {
            urepo.deleteById(id);
            return true;
        }
        return false;
    }

    public User findByEmail(String email) {
        return urepo.findByEmail(email);
    }

    public User findByUname(String uname) {
        return urepo.findByUname(uname);
    }
    
    public User findByPhoneNo(String phone_no) {
        return urepo.findByPhoneNo(phone_no);
    }


    /**
     * Create or update a user with password encoding.
     * If the password looks already encoded, we keep it.
     */
    public User saveUser(User user) {
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            // Heuristic: encode only if it doesn't look like a bcrypt hash (60 chars, starts with $2)
            String pwd = user.getPassword();
            boolean looksEncoded = pwd.length() >= 60 && pwd.startsWith("$2");
            if (!looksEncoded) {
                user.setPassword(passwordEncoder.encode(pwd));
            }
        }
        return urepo.save(user);
    }
}
