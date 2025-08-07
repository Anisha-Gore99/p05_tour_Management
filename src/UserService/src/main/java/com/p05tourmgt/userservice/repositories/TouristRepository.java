package com.p05tourmgt.userservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.p05tourmgt.userservice.entities.Tourist;
import com.p05tourmgt.userservice.entities.User;

@Repository
public interface TouristRepository extends JpaRepository<Tourist, Integer> {
    // Custom query methods can be added here if needed
	// Finds a Tourist by the associated User object.
    Tourist findByUid(User uid);

    // Example of a custom query using @Query annotation
    // This query finds all tourists whose first name starts with a given prefix.
    @Query("SELECT t FROM Tourist t WHERE t.fname LIKE :prefix%")
    List<Tourist> findByFnameStartingWith(String prefix);
}
