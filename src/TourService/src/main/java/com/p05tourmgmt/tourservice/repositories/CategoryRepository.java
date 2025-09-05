package com.p05tourmgmt.tourservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p05tourmgmt.tourservice.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
