package com.p05tourmgmt.tourservice.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.p05tourmgmt.tourservice.entities.TourSchedule;
import com.p05tourmgmt.tourservice.repositories.TourScheduleRepository;
import com.p05tourmgmt.tourservice.services.TourScheduleService;

import jakarta.transaction.Transactional;

@Service
public class TourScheduleServiceImpl implements TourScheduleService {

    @Autowired
    private TourScheduleRepository tourScheduleRepository;

    @Override
    public TourSchedule createSchedule(TourSchedule schedule) {
        return tourScheduleRepository.save(schedule);
    }

    @Override
    public List<TourSchedule> getAllSchedules() {
        return tourScheduleRepository.findAll();
    }

    @Override
    public TourSchedule getScheduleById(int id) {
        return tourScheduleRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteSchedule(int id) {
        tourScheduleRepository.deleteById(id);
    }
    @Override
    @Transactional
    public void decrementAvailableBookings(int scheduleId, int count) {
        TourSchedule schedule = tourScheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new RuntimeException("Tour Schedule not found"));

        if (schedule.getAvailableBookings() < count) {
            throw new RuntimeException("Not enough available bookings");
        }

        schedule.setAvailableBookings(schedule.getAvailableBookings() - count);

        // Allow the exception to be thrown by JPA, so Spring can handle it.
        tourScheduleRepository.save(schedule);
    }
}
