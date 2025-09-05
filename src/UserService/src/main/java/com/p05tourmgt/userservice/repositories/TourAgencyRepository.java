package com.p05tourmgt.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.p05tourmgt.userservice.entities.TourAgency;
import com.p05tourmgt.userservice.entities.User;

public interface TourAgencyRepository extends JpaRepository<TourAgency, Integer> {
    TourAgency findByUser(User user);
}
