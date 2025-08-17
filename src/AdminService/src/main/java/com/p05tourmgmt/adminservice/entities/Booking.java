package com.p05tourmgmt.adminservice.entities;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="booking")
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="booking_id")
	int booking_id;
	@Column(name="booking_date")
	Date booking_date;
	@Column(name="no_of_tourist",nullable=false)
	int no_of_tourist;
	@Column(name="status")
	String status;
	@Column(name="schedule_id")
	Integer schedule_id;
	@Column(name="tourist_id")
	Integer tourist_id;
	public Booking() {
		super();
	}
	public Booking(int booking_id, Date booking_date, int no_of_tourist, String status, Integer schedule_id,
			Integer tourist_id) {
		super();
		this.booking_id = booking_id;
		this.booking_date = booking_date;
		this.no_of_tourist = no_of_tourist;
		this.status = status;
		this.schedule_id = schedule_id;
		this.tourist_id = tourist_id;
	}
	public int getBooking_id() {
		return booking_id;
	}
	public void setBooking_id(int booking_id) {
		this.booking_id = booking_id;
	}
	public Date getBooking_date() {
		return booking_date;
	}
	public void setBooking_date(Date booking_date) {
		this.booking_date = booking_date;
	}
	public int getNo_of_tourist() {
		return no_of_tourist;
	}
	public void setNo_of_tourist(int no_of_tourist) {
		this.no_of_tourist = no_of_tourist;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getSchedule_id() {
		return schedule_id;
	}
	public void setSchedule_id(Integer schedule_id) {
		this.schedule_id = schedule_id;
	}
	public Integer getTourist_id() {
		return tourist_id;
	}
	public void setTourist_id(Integer tourist_id) {
		this.tourist_id = tourist_id;
	}
	@Override
	public String toString() {
		return "Booking [booking_id=" + booking_id + ", booking_date=" + booking_date + ", no_of_tourist="
				+ no_of_tourist + ", status=" + status + ", schedule_id=" + schedule_id + ", tourist_id=" + tourist_id
				+ "]";
	}
	
	
}
