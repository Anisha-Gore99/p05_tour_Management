package com.cdac.projectp05tourmanagement.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.projectp05tourmanagement.entities.User;
import com.cdac.projectp05tourmanagement.repositories.UserRepository;


@Service
public class UserService {


	@Autowired
	UserRepository urepo;
	
	public List<User> getAll(){
		return urepo.findAll();
	}
	
	public User getLogin(String uname,String password)
	{
		User u;
		Optional<User> ol = urepo.getLogin(uname, password);
		try
		{
			u=ol.get();
		}
		catch(Exception e)
		{
			u=null;
		}
		return u;
	}
}
