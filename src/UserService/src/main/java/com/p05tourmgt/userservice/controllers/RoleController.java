package com.p05tourmgt.userservice.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p05tourmgt.userservice.entities.Role;
import com.p05tourmgt.userservice.services.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    //Retrieves a list of all roles.- GET
    //http://localhost:8080/api/roles/getallroles
    @GetMapping("/getallroles")
    public List<Role> getAllRoles() {
        return roleService.findAllRoles();
    }

    //Retrieves a role by its unique ID.- GET
    //http://localhost:8080/api/roles/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Integer id) {
        Optional<Role> role = roleService.findRoleById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

//    //Creates a new role. - POST
//    //http://localhost:8080/api/roles
//    @PostMapping
//    public Role createRole(@RequestBody Role role) {
//        return roleService.saveRole(role);
//    }
//
//    //Updates an existing role by its ID- PUT
//    //http://localhost:8080/api/roles/{id}
//    @PutMapping("/{id}")
//    public ResponseEntity<Role> updateRole(@PathVariable Integer id, @RequestBody Role roleDetails) {
//        return roleService.findRoleById(id)
//            .map(role -> {
//                role.setRname(roleDetails.getRname());
//                Role updatedRole = roleService.saveRole(role);
//                return ResponseEntity.ok(updatedRole);
//            })
//            .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    //Deletes a role by its ID.- DELETE
//    // http://localhost:8080/api/roles/{id}
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteRole(@PathVariable Integer id) {
//        roleService.deleteRole(id);
//        return ResponseEntity.noContent().build();
//    }
}
