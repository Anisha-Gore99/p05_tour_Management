package com.p05tourmgt.userservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p05tourmgt.userservice.entities.Role;
import com.p05tourmgt.userservice.repositories.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> findRoleById(Integer id) {
        return roleRepository.findById(id);
    }

//    public Role saveRole(Role role) {
//        return roleRepository.save(role);
//    }
//
//    public void deleteRole(Integer id) {
//        roleRepository.deleteById(id);
//    }
//    public Role findByRname(String rname) {
//        return roleRepository.findByRname(rname);
//    }
}