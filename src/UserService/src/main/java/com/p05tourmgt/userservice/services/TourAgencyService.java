package com.p05tourmgt.userservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.p05tourmgt.userservice.entities.Role;
import com.p05tourmgt.userservice.entities.TourAgency;
import com.p05tourmgt.userservice.entities.User;
import com.p05tourmgt.userservice.repositories.RoleRepository;
import com.p05tourmgt.userservice.repositories.TourAgencyRepository;
import com.p05tourmgt.userservice.repositories.UserRepository;

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

    /** Register new agency: attach role 'tour_agency', encode password, save User -> Agency */
    @Transactional
    public TourAgency registerAgency(TourAgency agency) {
        if (agency == null || agency.getUser() == null) {
            throw new IllegalArgumentException("Agency and nested User must not be null");
        }

        Role agencyRole = roleRepo.findByRnameIgnoreCase("tour_agency")
            .orElseThrow(() -> new IllegalStateException("Role 'tour_agency' not found. Seed roles first."));

        User u = agency.getUser();
        u.setRid(agencyRole);

        if (u.getPassword() != null && !u.getPassword().isBlank()) {
            u.setPassword(passwordEncoder.encode(u.getPassword()));
        }

        // save user first, then agency
        User savedUser = userRepo.save(u);
        agency.setUser(savedUser);

        return agencyRepo.save(agency);
    }

    public List<TourAgency> getAllAgencies() { return agencyRepo.findAll(); }

    public TourAgency getTourAgency(User user) { return agencyRepo.findByUser(user); }

    public Optional<TourAgency> getAgencyById(Integer id) { return agencyRepo.findById(id); }

    public TourAgency saveOrUpdateAgency(TourAgency agency) { return agencyRepo.save(agency); }

    public void deleteAgency(Integer id) { agencyRepo.deleteById(id); }
}
