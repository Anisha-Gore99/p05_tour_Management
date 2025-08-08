package com.p05tourmgt.userservice.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tour_agency")
public class TourAgency {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_agency_id")
    private int tour_agency_id;

    @Column(name = "tour_agency_name", nullable = false,length = 255)
    private String tour_agency_name;

    @Column(name = "phone_no",nullable = false, length = 255)
    private String phone_no;

    @Column(name = "agency_email",nullable = false, length = 255)
    private String agency_email;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "license_number", nullable = false, length = 255)
    private String license_number;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private User user;
    

	public TourAgency() {
		super();
	}


	public TourAgency(int tour_agency_id, String tour_agency_name, String phone_no, String agency_email, String address,
			String license_number, User user) {
		super();
		this.tour_agency_id = tour_agency_id;
		this.tour_agency_name = tour_agency_name;
		this.phone_no = phone_no;
		this.agency_email = agency_email;
		this.address = address;
		this.license_number = license_number;
		this.user = user;
	}


	public int getTour_agency_id() {
		return tour_agency_id;
	}


	public void setTour_agency_id(int tour_agency_id) {
		this.tour_agency_id = tour_agency_id;
	}


	public String getTour_agency_name() {
		return tour_agency_name;
	}


	public void setTour_agency_name(String tour_agency_name) {
		this.tour_agency_name = tour_agency_name;
	}


	public String getPhone_no() {
		return phone_no;
	}


	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}


	public String getAgency_email() {
		return agency_email;
	}


	public void setAgency_email(String agency_email) {
		this.agency_email = agency_email;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getLicense_number() {
		return license_number;
	}


	public void setLicense_number(String license_number) {
		this.license_number = license_number;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	
}
