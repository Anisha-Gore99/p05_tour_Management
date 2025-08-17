package com.p05tourmgmt.tourservice.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.p05tourmgmt.tourservice.entities.TourSchedule;
import com.p05tourmgmt.tourservice.repositories.TourScheduleRepository;

@Service
@Transactional
public class TourScheduleServiceImpl implements TourScheduleService {

    private final TourScheduleRepository tourScheduleRepository;

    public TourScheduleServiceImpl(TourScheduleRepository tourScheduleRepository) {
        this.tourScheduleRepository = tourScheduleRepository;
    }

    @Override
    public TourSchedule createSchedule(TourSchedule schedule) {
        return tourScheduleRepository.save(schedule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TourSchedule> getAllSchedules() {
        return tourScheduleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public TourSchedule getScheduleById(int id) {
        return tourScheduleRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteSchedule(int id) {
        tourScheduleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TourSchedule> getSchedulesByPackage(int packageId) {
        return tourScheduleRepository.findAllByTourPackagePackageId(packageId);
    }

    @Override
    public TourSchedule updateSchedule(int id, TourSchedule updated) {
        return tourScheduleRepository.findById(id).map(existing -> {
            if (updated.getStart_date() != null) {
                existing.setStart_date(updated.getStart_date());
            }
            if (updated.getEnd_date() != null) {
                existing.setEnd_date(updated.getEnd_date());
            }
            if (updated.getTourPackage() != null) {
                existing.setTourPackage(updated.getTourPackage());
            }
            return tourScheduleRepository.save(existing);
        }).orElse(null);
    }
}
