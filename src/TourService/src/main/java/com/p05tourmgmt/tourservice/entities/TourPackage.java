package com.p05tourmgmt.tourservice.entities;

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

import java.util.List;

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
	    
	 // Add a new column for the image URL
     //	@Column(name = "image_url")
    // private String imageUrl;
	    
	 // Use @Transient to define a field that will not be mapped to a column
    //	@Transient
   //	private String imageUrl;


	    @ManyToOne
	    @JoinColumn(name = "cat_id")
	    private Category category;
	    
	    
	    @OneToMany(mappedBy = "tourPackage", fetch = FetchType.EAGER)
	    private List<TourSchedule> schedules;


		public TourPackage() {
			super();
			// TODO Auto-generated constructor stub
		}


		public TourPackage(int packageId, String pname, String description, String destination, int tourAgencyId,
				Category category, List<TourSchedule> schedules) {
			super();
			this.packageId = packageId;
			this.pname = pname;
			this.description = description;
			this.destination = destination;
			this.tourAgencyId = tourAgencyId;
			this.category = category;
			this.schedules = schedules;
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


		public List<TourSchedule> getSchedules() {
			return schedules;
		}


		public void setSchedules(List<TourSchedule> schedules) {
			this.schedules = schedules;
		}


//		// Getter and Setter for the transient field
//	    public String getImageUrl() { return imageUrl; }
//	    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
//		


		
}
