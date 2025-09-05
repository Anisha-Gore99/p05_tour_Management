package com.p05tourmgt.bookingservice.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.p05tourmgt.bookingservice.entities.TourSchedule;


@Repository
public interface TourScheduleRepository extends JpaRepository<TourSchedule, Integer> {
		
	@Modifying
	@Transactional
	@Query("UPDATE TourSchedule ts SET ts.availableBookings = ts.availableBookings - :count WHERE ts.scheduleId = :id AND ts.availableBookings >= :count")
	int reserveSeats(@Param("id") int id, @Param("count") int count);

}
