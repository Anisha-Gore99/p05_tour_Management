package com.p05tourmgmt.tourservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.p05tourmgmt.tourservice.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
