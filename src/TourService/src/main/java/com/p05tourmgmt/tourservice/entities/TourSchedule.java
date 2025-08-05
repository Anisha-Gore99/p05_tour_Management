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

@Entity
@Table(name = "tour_schedule")
public class TourSchedule {
 
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "schedule_id")
	int schedule_id;
	 
	 @Column(name = "start_date")
	LocalDate start_date;
	
	 @Column(name = "end_date")
	LocalDate end_date;
	 
	 @Column(name = "duration")
	int duration;
	 
	 @Column(name = "cost")
	BigDecimal cost;
	 
	 @Column(name = "available_bookings")
	int available_bookings;
	 
	 @ManyToOne
	    @JoinColumn(name = "package_id")
	    private TourPackage tourPackage;

	public TourSchedule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TourSchedule(int schedule_id, LocalDate start_date, LocalDate end_date, int duration, BigDecimal cost,
			int available_bookings, TourPackage tourPackage) {
		super();
		this.schedule_id = schedule_id;
		this.start_date = start_date;
		this.end_date = end_date;
		this.duration = duration;
		this.cost = cost;
		this.available_bookings = available_bookings;
		this.tourPackage = tourPackage;
	}

	public int getSchedule_id() {
		return schedule_id;
	}

	public void setSchedule_id(int schedule_id) {
		this.schedule_id = schedule_id;
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

	public int getAvailable_bookings() {
		return available_bookings;
	}

	public void setAvailable_bookings(int available_bookings) {
		this.available_bookings = available_bookings;
	}

	public TourPackage getTourPackage() {
		return tourPackage;
	}

	public void setTourPackage(TourPackage tourPackage) {
		this.tourPackage = tourPackage;
	}
	 
	 
}
