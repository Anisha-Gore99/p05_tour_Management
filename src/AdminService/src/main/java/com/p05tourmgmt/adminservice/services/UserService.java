package com.p05tourmgmt.adminservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p05tourmgmt.adminservice.entities.User;
import com.p05tourmgmt.adminservice.repositories.UserRepository;

@Service
public class UserService {
	  @Autowired
	    private UserRepository userRepository;

	    public List<User> getAllUsers() {
	        return userRepository.findAll();
	    }

//	    public void deleteUser(int uid) {
//	        userRepository.deleteById(uid);
//	    }
}
