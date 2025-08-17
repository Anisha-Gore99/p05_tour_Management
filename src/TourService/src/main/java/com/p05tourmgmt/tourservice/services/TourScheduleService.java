package com.p05tourmgmt.tourservice.services;

import java.util.List;
import com.p05tourmgmt.tourservice.entities.TourSchedule;

public interface TourScheduleService {
    TourSchedule createSchedule(TourSchedule schedule);
    List<TourSchedule> getAllSchedules();
    TourSchedule getScheduleById(int id);
    void deleteSchedule(int id);
    List<TourSchedule> getSchedulesByPackage(int packageId);
    TourSchedule updateSchedule(int id, TourSchedule updated);
}
