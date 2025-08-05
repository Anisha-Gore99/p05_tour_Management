package com.cdac.projectp05tourmanagement.entities;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tour_agency")
public class TourAgency {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="tour_agency_id")
int tour_agency_id;

@Column(name="tour_agency_name")
String tour_agency_name;

@Column(name="phone_no")
String phone_no;

@Column(name="agency_email")
String agency_email;

@Column(name="address")
String address;

@Column(name="liscence_number")
String liscece_number;

@OneToOne
@JoinColumn(name="uid")
User uid;



public TourAgency() {
	super();
}



public TourAgency(int tour_agency_id, String tour_agency_name, String phone_no, String agency_email, String address,
		String liscece_number, User uid) {
	super();
	this.tour_agency_id = tour_agency_id;
	this.tour_agency_name = tour_agency_name;
	this.phone_no = phone_no;
	this.agency_email = agency_email;
	this.address = address;
	this.liscece_number = liscece_number;
	this.uid = uid;
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

public String getLiscece_number() {
	return liscece_number;
}

public void setLiscece_number(String liscece_number) {
	this.liscece_number = liscece_number;
}

public User getUid() {
	return uid;
}

public void setUid(User uid) {
	this.uid = uid;
}

}
