package com.p05tourmgmt.userservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p05tourmgmt.userservice.entities.Tourist;
import com.p05tourmgmt.userservice.repositories.TouristRepository;

import java.util.Optional;

@Service
public class TouristService {

    @Autowired
    private TouristRepository trepo;

    // Registration
    public Tourist registerTourist(Tourist tourist) {
        return trepo.save(tourist);
    }

    // Login
    public Tourist loginTourist(String uname, String password) {
        Optional<Tourist> touristOpt = trepo.findByUnameAndPassword(uname, password);
        return touristOpt.orElse(null);
    }

    public Tourist getTouristByUid(int uid) {
        return trepo.getTouristByUid(uid);
    }
}
