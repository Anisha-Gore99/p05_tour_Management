package com.p05tourmgt.bookingservice.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.p05tourmgt.bookingservice.entities.TourSchedule;


@Repository
public interface TourScheduleRepository extends JpaRepository<TourSchedule, Integer> {
	

	@Modifying
	  @Query("""
	    update TourSchedule s
	       set s.availableBookings = s.availableBookings - :count
	     where s.scheduleId = :scheduleId
	       and s.availableBookings >= :count
	  """)
	  int reserveSeats(@Param("scheduleId") int scheduleId, @Param("count") int count);
}
