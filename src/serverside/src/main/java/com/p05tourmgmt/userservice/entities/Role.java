package com.p05tourmgmt.userservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Role")
public class Role {
	@Id
	@Column(name="rid")
	int rid;

	@Column(name="rname")
	String rname;

	public Role() {
		super();
	}

	public Role(int rid, String rname) {
		super();
		this.rid = rid;
		this.rname = rname;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}
}
