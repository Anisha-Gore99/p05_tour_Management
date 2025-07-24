package com.cdac.projectp05tourmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.projectp05tourmanagement.entities.AppUser;
import com.cdac.projectp05tourmanagement.repositories.AppUserRepository;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {
	
	@Autowired
    private AppUserRepository appUserRepository;

    // Retrieve all users
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    // Retrieve a user by UID
    public Optional<AppUser> getUserByUid(Integer uid) { // Changed Long to Integer
        return appUserRepository.findById(uid);
    }

    // Save a new user or update an existing one
    public AppUser saveUser(AppUser user) {
        // In a real application, you would hash the password here before saving
        return appUserRepository.save(user);
    }

    // Delete a user by UID
    public void deleteUser(Integer uid) { // Changed Long to Integer
        appUserRepository.deleteById(uid);
    }


}
