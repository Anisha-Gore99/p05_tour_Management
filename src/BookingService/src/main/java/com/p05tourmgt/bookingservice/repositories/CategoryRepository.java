package com.p05tourmgt.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p05tourmgt.bookingservice.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
