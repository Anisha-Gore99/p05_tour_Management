package com.cdac.projectp05tourmanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.projectp05tourmanagement.entities.LoginCheck;
import com.cdac.projectp05tourmanagement.entities.User;
import com.cdac.projectp05tourmanagement.services.UserService;

@CrossOrigin("http://localhost:3000")
@RestController
public class UserController {
	@Autowired
	UserService uservice;
	
	@GetMapping("/all")
	public List<User> getAllUsers()
	{
		return uservice.getAll();
	}
	
	@PostMapping("/chkLogin")
	public User chkLogin(@RequestBody LoginCheck lcheck)
	{
		return uservice.getLogin(lcheck.getUname(), lcheck.getPassword());
	}
}
