package com.p05tourmgmt.tourservice.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "tour_schedule")
public class TourSchedule {
 
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "schedule_id")
	 private int scheduleId;
	 
	 @Column(name = "start_date")
	 private LocalDate start_date;
	
	 @Column(name = "end_date")
	 private LocalDate end_date;
	 
	 @Column(name = "duration")
	 int duration;
	 
	 @Column(name = "cost")
	 BigDecimal cost;
	 
	 @Column(name = "available_bookings")
	 int availableBookings;
	 
	 @ManyToOne
	 @JoinColumn(name = "package_id")
	 private TourPackage tourPackage;
	 

	 public TourSchedule() {
			super();
	}

	public TourSchedule(int scheduleId, LocalDate start_date, LocalDate end_date, int duration, BigDecimal cost,
			int availableBookings, TourPackage tourPackage) {
		super();
		this.scheduleId = scheduleId;
		this.start_date = start_date;
		this.end_date = end_date;
		this.duration = duration;
		this.cost = cost;
		this.availableBookings = availableBookings;
		this.tourPackage = tourPackage;
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public LocalDate getStart_date() {
		return start_date;
	}

	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}

	public LocalDate getEnd_date() {
		return end_date;
	}

	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public int getAvailableBookings() {
		return availableBookings;
	}

	public void setAvailableBookings(int availableBookings) {
		this.availableBookings = availableBookings;
	}

	public TourPackage getTourPackage() {
		return tourPackage;
	}

	public void setTourPackage(TourPackage tourPackage) {
		this.tourPackage = tourPackage;
	}

	
	 
	 

	
	

	
}
