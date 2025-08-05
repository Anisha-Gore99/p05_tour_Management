package com.p05tourmgmt.userservice.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "tourist")
public class Tourist {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	int tourist_id;
	
	Date dob;
	
	String address;
	
	@OneToOne
	@JoinColumn(name = "uid")
	User uid;
	
	String fname;
	String lname;
	public int getTourist_id() {
		return tourist_id;
	}
	public void setTourist_id(int tourist_id) {
		this.tourist_id = tourist_id;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@OneToOne
	public User getUid() {
		return uid;
	}
	public void setUid(User uid) {
		this.uid = uid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	
	

}
