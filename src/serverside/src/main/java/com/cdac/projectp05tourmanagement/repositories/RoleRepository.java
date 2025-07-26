package com.cdac.projectp05tourmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.projectp05tourmanagement.entities.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
