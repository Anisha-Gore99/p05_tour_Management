package com.p05tourmgmt.tourservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tour_package")
public class TourPackage {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "package_id")
	    private int packageId;

	    @Column(name = "pname")
	    private String pname;

	    @Column(name = "description")
	    private String description;

	    @Column(name = "destination")
	    private String destination;

	    @Column(name = "tour_agency_id")
	    private int tourAgencyId;

	    @ManyToOne
	    @JoinColumn(name = "cat_id")
	    private Category category;

		public TourPackage() {
			super();
			// TODO Auto-generated constructor stub
		}

		public TourPackage(int packageId, String pname, String description, String destination, int tourAgencyId,
				Category category) {
			super();
			this.packageId = packageId;
			this.pname = pname;
			this.description = description;
			this.destination = destination;
			this.tourAgencyId = tourAgencyId;
			this.category = category;
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

		public int getTourAgencyId() {
			return tourAgencyId;
		}

		public void setTourAgencyId(int tourAgencyId) {
			this.tourAgencyId = tourAgencyId;
		}

		public Category getCategory() {
			return category;
		}

		public void setCategory(Category category) {
			this.category = category;
		}

		
}
