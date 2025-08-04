package com.cdac.projectp05tourmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.projectp05tourmanagement.entities.TourAgency;
import com.cdac.projectp05tourmanagement.entities.User;
import com.cdac.projectp05tourmanagement.repositories.TourAgencyRepository;

@Service
public class TourAgencyService {
@Autowired
TourAgencyRepository trepo;

public TourAgency getTourAgency(User u)
{
	return trepo.getTourAgency(u);
}
}
