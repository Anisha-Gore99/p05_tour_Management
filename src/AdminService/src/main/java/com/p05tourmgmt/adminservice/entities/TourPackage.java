package com.p05tourmgmt.adminservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tour_package")
public class TourPackage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="package_id")
	int package_id;
	@Column(name="pname")
	String pname;
	@Column(name="description")
	String description;
	@Column(name="destination")
	String destination;
	@Column(name="tour_agency_id")
	Integer tour_agency_id;
	@Column(name="cat_id")
	Integer cat_id;
	public TourPackage() {
		super();
	}
	public TourPackage(int package_id, String pname, String description, String destination, Integer tour_agency_id,
			Integer cat_id) {
		super();
		this.package_id = package_id;
		this.pname = pname;
		this.description = description;
		this.destination = destination;
		this.tour_agency_id = tour_agency_id;
		this.cat_id = cat_id;
	}
	public int getPackage_id() {
		return package_id;
	}
	public void setPackage_id(int package_id) {
		this.package_id = package_id;
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
	public Integer getTour_agency_id() {
		return tour_agency_id;
	}
	public void setTour_agency_id(Integer tour_agency_id) {
		this.tour_agency_id = tour_agency_id;
	}
	public Integer getCat_id() {
		return cat_id;
	}
	public void setCat_id(Integer cat_id) {
		this.cat_id = cat_id;
	}
	@Override
	public String toString() {
		return "TourPackage [package_id=" + package_id + ", pname=" + pname + ", description=" + description
				+ ", destination=" + destination + ", tour_agency_id=" + tour_agency_id + ", cat_id=" + cat_id + "]";
	}
	
	
	
}
