package com.p05tourmgmt.adminservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p05tourmgmt.adminservice.entities.User;
import com.p05tourmgmt.adminservice.repositories.BookingRepository;
import com.p05tourmgmt.adminservice.repositories.TourPackageRepository;
import com.p05tourmgmt.adminservice.repositories.UserRepository;

@Service
public class UserService {
	  @Autowired
	    private UserRepository userRepository;
	  
	  @Autowired
	  private BookingRepository bookingRepository;
	  
	  @Autowired
	  private TourPackageRepository tourPackageRepository;

	    public List<User> getAllUsers() {
	        return userRepository.findAll();
	    }

	    public void deleteUser(int uid) {
	        User user = userRepository.findById(uid)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	        if (bookingRepository.existsByTouristId(uid)) {
	            throw new RuntimeException("Cannot delete user: User has existing bookings.");
	        }

	        if (tourPackageRepository.existsByTourAgencyId(uid)) {
	            throw new RuntimeException("Cannot delete user: User is a tour agency with tour packages.");
	        }

	        userRepository.delete(user);
	    }

	  
	  

}
