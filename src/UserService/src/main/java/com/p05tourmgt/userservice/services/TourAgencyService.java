package com.p05tourmgt.userservice.services;

import com.p05tourmgt.userservice.entities.Role;
import com.p05tourmgt.userservice.entities.TourAgency;
import com.p05tourmgt.userservice.entities.User;
import com.p05tourmgt.userservice.repositories.RoleRepository;
import com.p05tourmgt.userservice.repositories.TourAgencyRepository;
import com.p05tourmgt.userservice.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TourAgencyService {

    private final TourAgencyRepository agencyRepo;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public TourAgencyService(
            TourAgencyRepository agencyRepo,
            UserRepository userRepo,
            RoleRepository roleRepo,
            PasswordEncoder passwordEncoder
    ) {
        this.agencyRepo = agencyRepo;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public TourAgency registerAgency(TourAgency agency) {
        if (agency == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Agency payload is required");
        }

        // NOTE: If your entity field is named `uid` instead of `user`, swap to:
        // User u = agency.getUid();
        User u = agency.getUser();
        if (u == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account details are required");
        }

        // normalize phones (digits only)
        if (u.getPhoneNo() != null) {
            u.setPhoneNo(u.getPhoneNo().replaceAll("\\D", ""));
        }
        if (agency.getPhone_no() != null) {
            agency.setPhone_no(agency.getPhone_no().replaceAll("\\D", ""));
        }

        if (u.getPhoneNo() == null || u.getPhoneNo().length() < 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone must have at least 10 digits");
        }

        // Friendly duplicate checks (prevents raw constraint exceptions)
        if (u.getEmail() != null && userRepo.findByEmail(u.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        if (u.getUname() != null && userRepo.findByUname(u.getUname()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        if (u.getPhoneNo() != null && userRepo.findByPhoneNo(u.getPhoneNo()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number already exists");
        }

        // Assign role "agency" (create if missing)
        Role role = roleRepo.findByRnameIgnoreCase("agency")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setRname("agency");
                    return roleRepo.save(r);
                });
        u.setRid(role);

        // Encode password if not already encoded
        if (u.getPassword() != null && !u.getPassword().startsWith("$2a$")) {
            u.setPassword(passwordEncoder.encode(u.getPassword()));
        }

        try {
            u = userRepo.save(u);
            // NOTE: If field is `uid`, call agency.setUid(u);
            agency.setUser(u);
            return agencyRepo.save(agency);
        } catch (DataIntegrityViolationException e) {
            // e.g., DB unique constraint
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate account details", e);
        }
    }


    public List<TourAgency> getAllAgencies() { return agencyRepo.findAll(); }

    public TourAgency getTourAgency(User user) { return agencyRepo.findByUser(user); }

    public Optional<TourAgency> getAgencyById(Integer id) { return agencyRepo.findById(id); }

    public TourAgency saveOrUpdateAgency(TourAgency agency) { return agencyRepo.save(agency); }

    public void deleteAgency(Integer id) { agencyRepo.deleteById(id); }
}
