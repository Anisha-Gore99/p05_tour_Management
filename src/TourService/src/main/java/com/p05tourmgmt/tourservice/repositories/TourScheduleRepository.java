package com.p05tourmgmt.tourservice.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.p05tourmgmt.tourservice.entities.TourSchedule;

public interface TourScheduleRepository extends JpaRepository<TourSchedule, Integer> {
    List<TourSchedule> findAllByTourPackagePackageId(int packageId);
}
