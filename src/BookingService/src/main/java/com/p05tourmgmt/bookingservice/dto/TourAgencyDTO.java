package com.p05tourmgmt.bookingservice.dto;

public class TourAgencyDTO {
	private int agencyId;
    private String tourAgencyName;
    private String agencyEmail;
    private String phoneNo;
    private String address;
    
    
	public TourAgencyDTO() {
		super();
	}


	public TourAgencyDTO(int agencyId, String tourAgencyName, String agencyEmail, String phoneNo, String address) {
		super();
		this.agencyId = agencyId;
		this.tourAgencyName = tourAgencyName;
		this.agencyEmail = agencyEmail;
		this.phoneNo = phoneNo;
		this.address = address;
	}


	public int getAgencyId() {
		return agencyId;
	}


	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}


	public String getTourAgencyName() {
		return tourAgencyName;
	}


	public void setTourAgencyName(String tourAgencyName) {
		this.tourAgencyName = tourAgencyName;
	}


	public String getAgencyEmail() {
		return agencyEmail;
	}


	public void setAgencyEmail(String agencyEmail) {
		this.agencyEmail = agencyEmail;
	}


	public String getPhoneNo() {
		return phoneNo;
	}


	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}
    
    
    

}
