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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="tour_package")
public class TourPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_id")
    private int packageId;

    @Column(name = "pname", unique = true, length =255)
    private String pname;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "destination",  length = 255)
    private String destination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_agency_id")
    private TourAgency tourAgency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id")
    private Category category;

    @OneToMany(mappedBy = "tourPackage", cascade = CascadeType.ALL)
    private List<TourSchedule> tourSchedules;

	public TourPackage() {
		super();
	}

	public TourPackage(int packageId, String pname, String description, String destination, TourAgency tourAgency,
			Category category, List<TourSchedule> tourSchedules) {
		super();
		this.packageId = packageId;
		this.pname = pname;
		this.description = description;
		this.destination = destination;
		this.tourAgency = tourAgency;
		this.category = category;
		this.tourSchedules = tourSchedules;
	}

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public TourAgency getTourAgency() {
		return tourAgency;
	}

	public void setTourAgency(TourAgency tourAgency) {
		this.tourAgency = tourAgency;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<TourSchedule> getTourSchedules() {
		return tourSchedules;
	}

	public void setTourSchedules(List<TourSchedule> tourSchedules) {
		this.tourSchedules = tourSchedules;
	}
    
    
    
}
