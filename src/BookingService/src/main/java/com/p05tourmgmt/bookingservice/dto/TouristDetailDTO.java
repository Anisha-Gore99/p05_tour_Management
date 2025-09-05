package com.p05tourmgmt.bookingservice.dto;

public class TouristDetailDTO {
    private String fname;
    private String lname;
    private int age;
    private String gender;

    public TouristDetailDTO() {}

    public TouristDetailDTO(String fname, String lname, int age, String gender) {
        this.fname = fname;
        this.lname = lname;
        this.age = age;
        this.gender = gender;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

    
}