package com.p05tourmgmt.tourservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.p05tourmgmt.tourservice.entities.TourSchedule;

import jakarta.transaction.Transactional;

public interface TourScheduleRepository extends JpaRepository<TourSchedule, Integer> {

}
