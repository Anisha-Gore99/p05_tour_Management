package com.p05tourmgmt.tourservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.p05tourmgmt.tourservice.entities.TourSchedule;

public interface TourScheduleRepository extends JpaRepository<TourSchedule, Integer> {

}
