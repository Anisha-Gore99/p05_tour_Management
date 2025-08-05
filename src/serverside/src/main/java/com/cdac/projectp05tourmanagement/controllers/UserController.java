package com.cdac.projectp05tourmanagement.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.projectp05tourmanagement.entities.LoginCheck;
import com.cdac.projectp05tourmanagement.entities.User;
import com.cdac.projectp05tourmanagement.repositories.UserRepository;
import com.cdac.projectp05tourmanagement.services.UserService;



@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/user") 
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

	 //in postman->//http://localhost:8080/api/user/getbyid?id=id from database
	  @GetMapping("/getbyid")
	  public User getOne(@RequestParam("id")int id) {
		  return uservice.getOne(id);
	  }
	  //in postman->http://localhost:8080/api/user/save
	@PostMapping("/save") 
	  public User saveuser(@RequestBody User u) {
		  return uservice.save(u);
	  }
	@PutMapping("/update/{id}")
	public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
	    User user = uservice.updateUser(id, updatedUser);
	    return ResponseEntity.ok(user);
	}


}
