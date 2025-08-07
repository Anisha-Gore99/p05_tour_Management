package com.p05tourmgt.userservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p05tourmgt.userservice.entities.User;
import com.p05tourmgt.userservice.repositories.UserRepository;


@Service
public class UserServices {
	
	@Autowired
	UserRepository urepo;
	
	public List<User> getAll(){
		return urepo.findAll();
	}
	
	public User getLogin(String uname,String password)
	{
		User u;
		Optional<User> ol = urepo.getLogin(uname, password);
		try
		{
			u=ol.get();
		}
		catch(Exception e)
		{
			u=null;
		}
		return u;
	}

	
	public User getById(int uid)
	{
		return urepo.findById(uid).get();
	}


	public User getOne(int id) {
		Optional<User>soptional=urepo.findById(id);
		return soptional.get();
	}
	
	public User save(User u) {
		urepo.save(u);
		return u;
	}
	public User updateUser(int id, User updatedUser) {
        // Find the existing user by ID
        Optional<User> existingUserOptional = urepo.findById(id);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // Conditionally update each field only if it's provided in the request
            if (updatedUser.getUname() != null) {
                existingUser.setUname(updatedUser.getUname());
            }
            if (updatedUser.getPassword() != null) {
                existingUser.setPassword(updatedUser.getPassword());
            }
            if (updatedUser.getPhone_no() != null) {
                existingUser.setPhone_no(updatedUser.getPhone_no());
            }
            // Note: The email field is deliberately NOT updated here to avoid
            // the 'Duplicate entry' error and to keep the original email.

            // Save the existing user with its partially updated fields
            return urepo.save(existingUser);
        } else {
            // User with the given ID was not found
            return null;
        }
    }
	
	public boolean deleteUser(int id) {
	    if (urepo.existsById(id)) {
	        urepo.deleteById(id);
	        return true; // Deletion was successful
	    }
	    return false; // User was not found
	}
	
	public User findByEmail(String email) {
        return urepo.findByEmail(email);
    }
    
    public User findByUname(String uname) {
        return urepo.findByUname(uname);
    }
    
 // Create or update a user
    public User saveUser(User user) {
        return urepo.save(user);
    }


}
