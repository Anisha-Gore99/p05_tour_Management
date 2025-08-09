package com.p05tourmgt.bookingservice.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tour_schedule")
public class TourSchedule {
 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private int scheduleId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "duration", nullable=false)
    private int duration;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "available_bookings")
    private int availableBookings;

    @ManyToOne 
    @JoinColumn(name = "package_id")
    private TourPackage tourPackage;

    @OneToMany(mappedBy = "tourSchedule", cascade = CascadeType.ALL)
    private List<Booking> bookings;

	public TourSchedule() {
		super();
	}

	public TourSchedule(int scheduleId, LocalDate startDate, LocalDate endDate, int duration, BigDecimal cost,
			int availableBookings, TourPackage tourPackage, List<Booking> bookings) {
		super();
		this.scheduleId = scheduleId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.duration = duration;
		this.cost = cost;
		this.availableBookings = availableBookings;
		this.tourPackage = tourPackage;
		this.bookings = bookings;
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

	public TourPackage getTourPackage() {
		return tourPackage;
	}

	public void setTourPackage(TourPackage tourPackage) {
		this.tourPackage = tourPackage;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	
}
