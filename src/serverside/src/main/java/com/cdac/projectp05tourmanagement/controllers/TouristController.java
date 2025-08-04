package com.cdac.projectp05tourmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.projectp05tourmanagement.entities.Tourist;
import com.cdac.projectp05tourmanagement.entities.User;
import com.cdac.projectp05tourmanagement.services.TouristService;
import com.cdac.projectp05tourmanagement.services.UserService;

@CrossOrigin(origins ="http://localhost:3000")
@RestController
public class TouristController {

	@Autowired
	TouristService touristservice;
	
	@Autowired
	UserService uservice;
	
	
	@GetMapping("/getTourist")
	public Tourist getTourist(@RequestParam("uid") int uid) {
	    return touristservice.getTourist(uid);
	}

}
