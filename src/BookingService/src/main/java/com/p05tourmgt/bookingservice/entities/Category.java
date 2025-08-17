package com.p05tourmgt.bookingservice.entities;

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
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private int catId;

    @Column(name = "cname", nullable = false, unique = true, length = 45)
    private String cname;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<TourPackage> tourPackages;

	public Category() {
		super();
	}

	public Category(int catId, String cname, List<TourPackage> tourPackages) {
		super();
		this.catId = catId;
		this.cname = cname;
		this.tourPackages = tourPackages;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public List<TourPackage> getTourPackages() {
		return tourPackages;
	}

	public void setTourPackages(List<TourPackage> tourPackages) {
		this.tourPackages = tourPackages;
	}
    
    
}
