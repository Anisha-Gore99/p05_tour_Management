package com.p05tourmgmt.bookingservice.dto;

import java.util.List;

public class BookingDTO {
    private int bookingId;
    private String status;
    private String bookingDate;
    private String startDate;
    private String endDate;
    private String packageName;
    private String destination;
    private String agencyName;
    private String agencyEmail;
    private String agencyPhone;
    private TourScheduleDTO tourSchedule;
    private List<TouristDetailDTO> companions;
    
    
    
	public BookingDTO() {
		super();
	}



	public BookingDTO(int bookingId, String status, String bookingDate, String startDate, String endDate,
			String packageName, String destination, String agencyName, String agencyEmail, String agencyPhone,
			TourScheduleDTO tourSchedule, List<TouristDetailDTO> companions) {
		super();
		this.bookingId = bookingId;
		this.status = status;
		this.bookingDate = bookingDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.packageName = packageName;
		this.destination = destination;
		this.agencyName = agencyName;
		this.agencyEmail = agencyEmail;
		this.agencyPhone = agencyPhone;
		this.tourSchedule = tourSchedule;
		this.companions = companions;
	}



	public int getBookingId() {
		return bookingId;
	}



	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getBookingDate() {
		return bookingDate;
	}



	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}



	public String getStartDate() {
		return startDate;
	}



	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}



	public String getEndDate() {
		return endDate;
	}



	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



	public String getPackageName() {
		return packageName;
	}



	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}



	public String getDestination() {
		return destination;
	}



	public void setDestination(String destination) {
		this.destination = destination;
	}



	public String getAgencyName() {
		return agencyName;
	}



	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}



	public String getAgencyEmail() {
		return agencyEmail;
	}



	public void setAgencyEmail(String agencyEmail) {
		this.agencyEmail = agencyEmail;
	}



	public String getAgencyPhone() {
		return agencyPhone;
	}



	public void setAgencyPhone(String agencyPhone) {
		this.agencyPhone = agencyPhone;
	}



	public TourScheduleDTO getTourSchedule() {
	    return tourSchedule;
	}

	public void setTourSchedule(TourScheduleDTO tourSchedule) {
	    this.tourSchedule = tourSchedule;
	}

	public List<TouristDetailDTO> getCompanions() {
		return companions;
	}



	public void setCompanions(List<TouristDetailDTO> companions) {
		this.companions = companions;
	}



}