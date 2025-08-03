package com.cdac.projectp05tourmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.projectp05tourmanagement.entities.Tourist;
import com.cdac.projectp05tourmanagement.entities.User;



public interface TouristRepository extends JpaRepository<Tourist, Integer> {
	@org.springframework.data.jpa.repository.Query("select t from Tourist t where t.uid =:u")
    public Tourist getTourist(User u);
}
