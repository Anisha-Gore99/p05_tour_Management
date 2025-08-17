package com.p05tourmgt.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.p05tourmgt.userservice.entities.Tourist;
import com.p05tourmgt.userservice.entities.User;

public interface TouristRepository extends JpaRepository<Tourist, Integer> {
    Tourist findByUid(User uid);
}
