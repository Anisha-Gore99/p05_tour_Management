package com.p05tourmgt.userservice.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.p05tourmgt.userservice.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRnameIgnoreCase(String rname);
}
