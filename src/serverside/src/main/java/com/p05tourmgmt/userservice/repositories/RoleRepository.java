package com.p05tourmgmt.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p05tourmgmt.userservice.entities.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
