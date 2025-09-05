package com.p05tourmgt.userservice.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.p05tourmgt.userservice.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    User findByUname(String uname);

    User findByPhoneNo(String phoneNo);
	

}
