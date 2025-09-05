package com.p05tourmgmt.tourservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="category")
public class Category {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "cat_id")
	private int catId;
	 
	 @Column(name="cname",nullable=false)
	private String cname;

	public Category() {
		super();
	}

	public Category(int catId, String cname) {
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
