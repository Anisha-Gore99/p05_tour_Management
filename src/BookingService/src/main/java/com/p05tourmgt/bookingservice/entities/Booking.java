package com.p05tourmgt.bookingservice.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="booking")
public class Booking {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private int bookingId;

    @Column(name = "booking_date")
    private Date bookingDate;

    @Column(name = "no_of_tourist", nullable = false)
    private int noOfTourist;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private TourSchedule tourSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tourist_id")
    private Tourist tourist;

//    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<TouristDetails> touristDetails;
    
 // Booking.java
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TouristDetails> touristDetails;
    
	public Booking() {
		super();
	}

	public Booking(int bookingId, Date bookingDate, int noOfTourist, String status, TourSchedule tourSchedule,
			Tourist tourist, List<TouristDetails> touristDetails) {
		super();
		this.bookingId = bookingId;
		this.bookingDate = bookingDate;
		this.noOfTourist = noOfTourist;
		this.status = status;
		this.tourSchedule = tourSchedule;
		this.tourist = tourist;
		this.touristDetails = touristDetails;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public int getNoOfTourist() {
		return noOfTourist;
	}

	public void setNoOfTourist(int noOfTourist) {
		this.noOfTourist = noOfTourist;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TourSchedule getTourSchedule() {
		return tourSchedule;
	}

	public void setTourSchedule(TourSchedule tourSchedule) {
		this.tourSchedule = tourSchedule;
	}

	public Tourist getTourist() {
		return tourist;
	}

	public void setTourist(Tourist tourist) {
		this.tourist = tourist;
	}

	public List<TouristDetails> getTouristDetails() {
		return touristDetails;
	}

	public void setTouristDetails(List<TouristDetails> touristDetails) {
		this.touristDetails = touristDetails;
	}
	
	
    
    
}
