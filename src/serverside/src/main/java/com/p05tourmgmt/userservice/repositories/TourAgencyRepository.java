package com.p05tourmgmt.userservice.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.p05tourmgmt.userservice.entities.TourAgency;
import com.p05tourmgmt.userservice.entities.User;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p05tourmgmt.userservice.entities.TourAgency;

@Repository
public interface TourAgencyRepository extends JpaRepository<TourAgency, Integer> {
    Optional<TourAgency> findByUidUnameAndUidPassword(String uname, String password);
    @Query("select t from TourAgency t where t.uid.uid =:uid")
    public TourAgency getTourAgencyByUid(@Param("uid") int uid);


}

