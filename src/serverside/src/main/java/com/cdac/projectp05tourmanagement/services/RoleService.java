package com.cdac.projectp05tourmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.projectp05tourmanagement.repositories.RoleRepository;

@Service
public class RoleService {

	@Autowired
	RoleRepository rrepo;
}
