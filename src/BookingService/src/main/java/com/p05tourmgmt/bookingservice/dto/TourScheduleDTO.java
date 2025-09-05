package com.p05tourmgmt.bookingservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TourScheduleDTO {
	
	private int scheduleId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;
    private BigDecimal cost;
    private int availableBookings;
    private TourPackageDTO tourPackage;
    
	public TourScheduleDTO() {
		super();
	}

	public TourScheduleDTO(int scheduleId, LocalDate startDate, LocalDate endDate, int duration, BigDecimal cost,
			int availableBookings, TourPackageDTO tourPackage) {
		super();
		this.scheduleId = scheduleId;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
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

	public TourPackageDTO getTourPackage() {
		return tourPackage;
	}

	public void setTourPackage(TourPackageDTO tourPackage) {
		this.tourPackage = tourPackage;
	}

	

}
