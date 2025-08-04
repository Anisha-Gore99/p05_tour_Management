package com.cdac.projectp05tourmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.projectp05tourmanagement.entities.TourAgency;
import com.cdac.projectp05tourmanagement.entities.User;
import com.cdac.projectp05tourmanagement.services.TourAgencyService;
import com.cdac.projectp05tourmanagement.services.UserService;

@CrossOrigin(origins="http://localhost:3000")
@RestController
public class TourAgencyController {

	@Autowired
	TourAgencyService tservice;
	
	@Autowired
	UserService uservice;
	
	@GetMapping("/getTourAgency")
	public TourAgency getTourAgency(@RequestParam("uid") int uid)
	{
		User u=uservice.getById(uid);
		return tservice.getTourAgency(u);
	}
}
