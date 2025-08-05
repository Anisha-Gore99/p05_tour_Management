package com.p05tourmgmt.userservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p05tourmgmt.userservice.entities.Tourist;
import com.p05tourmgmt.userservice.entities.User;
import com.p05tourmgmt.userservice.repositories.TouristRepository;

@Service
public class TouristService {
@Autowired
TouristRepository touristrepo;
public Tourist getTourist(int uid) {
    return touristrepo.getTouristByUid(uid);
}


}
