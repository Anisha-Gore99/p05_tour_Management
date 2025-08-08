package com.p05tourmgt.bookingservice.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="cancellation")
public class Cancellation {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cancellation_id")
    private int cancellationId;

    @Column(name = "cancel_date")
    private Date cancelDate;

    @Column(name = "cancel_reason", nullable = false, length = 255)
    private String cancelReason;

    @Column(name = "cancellation_status", length = 255)
    private String cancellationStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

	public Cancellation() {
		super();
	}

	public Cancellation(int cancellationId, Date cancelDate, String cancelReason, String cancellationStatus,
			Booking booking) {
		super();
		this.cancellationId = cancellationId;
		this.cancelDate = cancelDate;
		this.cancelReason = cancelReason;
		this.cancellationStatus = cancellationStatus;
		this.booking = booking;
	}

	public int getCancellationId() {
		return cancellationId;
	}

	public void setCancellationId(int cancellationId) {
		this.cancellationId = cancellationId;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getCancellationStatus() {
		return cancellationStatus;
	}

	public void setCancellationStatus(String cancellationStatus) {
		this.cancellationStatus = cancellationStatus;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}
    
    
    
}
