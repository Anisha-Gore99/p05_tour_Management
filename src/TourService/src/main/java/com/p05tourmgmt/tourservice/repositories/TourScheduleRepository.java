package com.p05tourmgmt.tourservice.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.p05tourmgmt.tourservice.entities.TourSchedule;


@Repository
public interface TourScheduleRepository extends JpaRepository<TourSchedule, Integer> {

    List<TourSchedule> findAllByTourPackagePackageId(int packageId);

    @Modifying
    @Transactional
    @Query("UPDATE TourSchedule ts SET ts.availableBookings = ts.availableBookings - :count " +
           "WHERE ts.scheduleId = :scheduleId AND ts.availableBookings >= :count")
    int reserveSeats(@Param("scheduleId") int scheduleId, @Param("count") int count);
}