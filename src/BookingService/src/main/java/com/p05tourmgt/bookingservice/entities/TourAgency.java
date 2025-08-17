package com.p05tourmgt.bookingservice.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tour_agency")
public class TourAgency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_agency_id")
    private int tourAgencyId;

    @Column(name = "tour_agency_name", nullable = false, length = 100)
    private String tourAgencyName;

    @Column(name = "phone_no", nullable = false, length = 255)
    private String phoneNo;

    @Column(name = "agency_email", nullable = false, length = 255)
    private String agencyEmail;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "license_number", nullable = false, length = 255)
    private String licenseNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private User user;

    @OneToMany(mappedBy = "tourAgency", cascade = CascadeType.ALL)
    private List<TourPackage> tourPackages;

	public TourAgency() {
		super();
	}

	public TourAgency(int tourAgencyId, String tourAgencyName, String phoneNo, String agencyEmail, String address,
			String licenseNumber, User user, List<TourPackage> tourPackages) {
		super();
		this.tourAgencyId = tourAgencyId;
		this.tourAgencyName = tourAgencyName;
		this.phoneNo = phoneNo;
		this.agencyEmail = agencyEmail;
		this.address = address;
		this.licenseNumber = licenseNumber;
		this.user = user;
		this.tourPackages = tourPackages;
	}

	public int getTourAgencyId() {
		return tourAgencyId;
	}

	public void setTourAgencyId(int tourAgencyId) {
		this.tourAgencyId = tourAgencyId;
	}

	public String getTourAgencyName() {
		return tourAgencyName;
	}

	public void setTourAgencyName(String tourAgencyName) {
		this.tourAgencyName = tourAgencyName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAgencyEmail() {
		return agencyEmail;
	}

	public void setAgencyEmail(String agencyEmail) {
		this.agencyEmail = agencyEmail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<TourPackage> getTourPackages() {
		return tourPackages;
	}

	public void setTourPackages(List<TourPackage> tourPackages) {
		this.tourPackages = tourPackages;
	}
    
    
}
