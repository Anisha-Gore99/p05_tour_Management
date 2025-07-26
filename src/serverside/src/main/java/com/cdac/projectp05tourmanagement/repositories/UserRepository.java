package com.cdac.projectp05tourmanagement.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cdac.projectp05tourmanagement.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where uname=:uname and password=:password")
	public Optional<User> getLogin(String uname,String password);
}
