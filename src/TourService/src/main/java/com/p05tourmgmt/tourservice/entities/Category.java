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
	private int cat_id;
	 
	 @Column(name="cname",nullable=false)
	private String cname;

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Category(int cat_id, String cname) {
		super();
		this.cat_id = cat_id;
		this.cname = cname;
	}

	public int getCat_id() {
		return cat_id;
	}

	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}
	 
	 
}
