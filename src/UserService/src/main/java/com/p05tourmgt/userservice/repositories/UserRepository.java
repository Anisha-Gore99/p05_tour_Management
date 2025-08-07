package com.p05tourmgt.userservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.p05tourmgt.userservice.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Custom query methods can be added here if needed
	
	@Query("select u from User u where uname=:uname and password=:password")
	public Optional<User> getLogin(String uname,String password);
	
	User findByEmail(String email);
    User findByUname(String uname); 
}
