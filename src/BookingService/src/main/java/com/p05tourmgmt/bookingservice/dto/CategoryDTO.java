package com.p05tourmgmt.bookingservice.dto;

public class CategoryDTO {
	
	private int catId;
    private String cname;
	public CategoryDTO() {
		super();
	}
	public CategoryDTO(int catId, String cname) {
		super();
		this.catId = catId;
		this.cname = cname;
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
	
}
