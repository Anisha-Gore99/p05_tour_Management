package com.p05tourmgmt.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.p05tourmgmt.userservice.entities.Tourist;
import com.p05tourmgmt.userservice.entities.User;
import com.p05tourmgmt.userservice.services.TouristService;
import com.p05tourmgmt.userservice.services.UserService;

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
