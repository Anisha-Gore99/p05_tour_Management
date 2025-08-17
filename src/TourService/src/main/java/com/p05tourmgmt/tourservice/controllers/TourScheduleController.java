package com.p05tourmgmt.tourservice.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.p05tourmgmt.tourservice.entities.TourSchedule;
import com.p05tourmgmt.tourservice.services.TourScheduleService;

@RestController
@RequestMapping("/api/schedules")

public class TourScheduleController {

    private final TourScheduleService tourScheduleService;

    public TourScheduleController(TourScheduleService tourScheduleService) {
        this.tourScheduleService = tourScheduleService;
    }

    @PostMapping
    public ResponseEntity<TourSchedule> createSchedule(@RequestBody TourSchedule schedule) {
        TourSchedule saved = tourScheduleService.createSchedule(schedule);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<TourSchedule>> getAllSchedules() {
        return ResponseEntity.ok(tourScheduleService.getAllSchedules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getScheduleById(@PathVariable("id") int id) {
        TourSchedule ts = tourScheduleService.getScheduleById(id);
        if (ts == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Schedule not found"));
        }
        return ResponseEntity.ok(ts);
    }

    @GetMapping("/by-package/{packageId}")
    public List<TourSchedule> getSchedulesByPackage(@PathVariable("packageId") int packageId) {
        return tourScheduleService.getSchedulesByPackage(packageId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchedule(@PathVariable("id") int id, @RequestBody TourSchedule updated) {
        TourSchedule saved = tourScheduleService.updateSchedule(id, updated);
        if (saved == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Schedule not found"));
        }
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable("id") int id) {
        tourScheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
