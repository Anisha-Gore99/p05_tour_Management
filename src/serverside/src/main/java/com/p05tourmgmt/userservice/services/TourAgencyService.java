package com.p05tourmgmt.userservice.services;

import org.springframework.stereotype.Service;

import com.p05tourmgmt.userservice.entities.TourAgency;
import com.p05tourmgmt.userservice.entities.User;
import com.p05tourmgmt.userservice.repositories.TourAgencyRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p05tourmgmt.userservice.entities.TourAgency;
import com.p05tourmgmt.userservice.entities.User;
import com.p05tourmgmt.userservice.repositories.TourAgencyRepository;

@Service
public class TourAgencyService {

    @Autowired
    private TourAgencyRepository trepo;

    // Register a new tour agency
    public TourAgency registerAgency(TourAgency agency) {
        return trepo.save(agency);
    }

    // Login using username and password
    public TourAgency loginAgency(String username, String password) {
        return trepo.findByUidUnameAndUidPassword(username, password)
                    .orElse(null); // You can throw exception instead for better handling
    }

    // Get agency by user object
    public TourAgency getTourAgency(User u) {
        return trepo.getTourAgencyByUid(u.getUid());
    }

}
