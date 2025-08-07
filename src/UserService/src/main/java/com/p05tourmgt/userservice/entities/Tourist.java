package com.p05tourmgt.userservice.entities;

import java.util.Date;

import jakarta.persistence.Column;
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
    @Column(name = "tourist_id")
    private int tourist_id;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "fname", length = 255)
    private String fname;

    @Column(name = "lname", length = 255)
    private String lname;

    @OneToOne
    @JoinColumn(name = "uid")
    private User uid;

	public Tourist() {
		super();
	}

	public Tourist(int tourist_id, Date dob, String address, String fname, String lname, User uid) {
		super();
		this.tourist_id = tourist_id;
		this.dob = dob;
		this.address = address;
		this.fname = fname;
		this.lname = lname;
		this.uid = uid;
	}

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

	public User getUid() {
		return uid;
	}

	public void setUid(User uid) {
		this.uid = uid;
	}

	
}
