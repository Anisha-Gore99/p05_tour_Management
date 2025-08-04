package com.cdac.projectp05tourmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.cdac.projectp05tourmanagement.entities.Tourist;




public interface TouristRepository extends JpaRepository<Tourist, Integer> {
	@org.springframework.data.jpa.repository.Query("select t from Tourist t where t.uid.uid =:uid")
	public Tourist getTouristByUid(@Param("uid") int uid);
}
