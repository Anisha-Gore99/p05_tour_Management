package com.p05tourmgt.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p05tourmgt.userservice.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    // Custom query methods can be added here if needed
	
	Role findByRname(String rname);
}