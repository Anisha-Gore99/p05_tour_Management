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
@Table(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rid")
    private int rid;

    @Column(name = "rname", nullable = false, unique = true, length = 45)
    private String rname;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<User> users;

	public Role() {
		super();
	}

	public Role(int rid, String rname, List<User> users) {
		super();
		this.rid = rid;
		this.rname = rname;
		this.users = users;
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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
    
    
}
