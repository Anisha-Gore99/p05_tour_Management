package com.p05tourmgt.userservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.p05tourmgt.userservice.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    User findByUname(String uname);
 // add this method
    User findByPhoneNo(String phone_no);
	

}
