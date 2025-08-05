package com.p05tourmgmt.tourservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p05tourmgmt.tourservice.entities.TourSchedule;
import com.p05tourmgmt.tourservice.services.TourScheduleService;

@RestController
@RequestMapping("/api/schedules")
public class TourScheduleController {
	 @Autowired
	    private TourScheduleService tourScheduleService;

	    @PostMapping
	    public TourSchedule createSchedule(@RequestBody TourSchedule schedule) {
	        return tourScheduleService.createSchedule(schedule);
	    }

	    @GetMapping
	    public List<TourSchedule> getAllSchedules() {
	        return tourScheduleService.getAllSchedules();
	    }

	    @GetMapping("/{id}")
	    public TourSchedule getScheduleById(@PathVariable int id) {
	        return tourScheduleService.getScheduleById(id);
	    }

	    @DeleteMapping("/{id}")
	    public void deleteSchedule(@PathVariable int id) {
	        tourScheduleService.deleteSchedule(id);
	    }
}
