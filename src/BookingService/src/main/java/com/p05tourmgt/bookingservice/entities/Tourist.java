package com.p05tourmgt.bookingservice.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="tourist")
public class Tourist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tourist_id")
    private int touristId;

    @Column(name = "dob", nullable = false)
    private Date dob;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "fname")
    private String fname;

    @Column(name = "lname")
    private String lname;

    @OneToMany(mappedBy = "tourist", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    // Foreign Key to User
    @Column(name = "uid")
    private int uid; 

	public Tourist() {
		super();
	}

	public Tourist(int touristId, Date dob, String address, String fname, String lname, List<Booking> bookings,
			int uid) {
		super();
		this.touristId = touristId;
		this.dob = dob;
		this.address = address;
		this.fname = fname;
		this.lname = lname;
		this.bookings = bookings;
		this.uid = uid;
	}

	public int getTouristId() {
		return touristId;
	}

	public void setTouristId(int touristId) {
		this.touristId = touristId;
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

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
    
    
}
