package com.cdac.projectp05tourmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.projectp05tourmanagement.entities.Tourist;
import com.cdac.projectp05tourmanagement.entities.User;
import com.cdac.projectp05tourmanagement.repositories.TouristRepository;

@Service
public class TouristService {
@Autowired
TouristRepository touristrepo;

public Tourist getTourist(User u) {
	return touristrepo.getTourist(u);
}


}
