package com.cdac.projectp05tourmanagement.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user") 
public class AppUser {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
	 private int uid; 

	 @Column(nullable = false, unique = true)
	 private String username;

	 @Column(nullable = false)
	 private String password; // Remember to hash passwords in a real app!

	 @Column(name = "phone_no") // Maps to 'phone_no' column
	 private String phoneNo;

	 @Column(nullable = false, unique = true)
	 private String email;
	 
	 @Column(name="rid")
	 private int rid;

	public AppUser(int uid, String username, String password, String phoneNo, String email, int rid) {
		super();
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.phoneNo = phoneNo;
		this.email = email;
		this.rid = rid;
	}

	public AppUser() {
		super();
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	@Override
	public String toString() {
		return "AppUser [uid=" + uid + ", username=" + username + ", password=" + password + ", phoneNo=" + phoneNo
				+ ", email=" + email + ", rid=" + rid + "]";
	}
	 
	 

	
}
