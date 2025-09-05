package com.p05tourmgmt.bookingservice.dto;

public class TourPackageDTO {
	private int packageId;
    private String pname;
    private String destination;
    private String description;
    private CategoryDTO category; // if you have a category reference
    private TourAgencyDTO tourAgency;
    
	public TourPackageDTO() {
		super();
	}

	public TourPackageDTO(int packageId, String pname, String destination, String description, CategoryDTO category,
			TourAgencyDTO tourAgency) {
		super();
		this.packageId = packageId;
		this.pname = pname;
		this.destination = destination;
		this.description = description;
		this.category = category;
		this.tourAgency = tourAgency;
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

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CategoryDTO getCategory() {
	    return category;
	}


	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

	public TourAgencyDTO getTourAgency() {
		return tourAgency;
	}

	public void setTourAgency(TourAgencyDTO tourAgency) {
		this.tourAgency = tourAgency;
	}
	
}
