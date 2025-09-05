package com.p05tourmgmt.adminservice.entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="uid")
int uid;

@Column(name="uname")	
String uname;

@Column(name="password")
String password;

@Column(name="phone_no")
String phone_no;

@Column(name="email")
String email;

@Column(name="rid",nullable = true)
Integer rid;

public User() {
	super();
}

public User(int uid, String uname, String password, String phone_no, String email, Integer rid) {
	super();
	this.uid = uid;
	this.uname = uname;
	this.password = password;
	this.phone_no = phone_no;
	this.email = email;
	this.rid = rid;
}

public int getUid() {
	return uid;
}

public void setUid(int uid) {
	this.uid = uid;
}

public String getUname() {
	return uname;
}

public void setUname(String uname) {
	this.uname = uname;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getPhone_no() {
	return phone_no;
}

public void setPhone_no(String phone_no) {
	this.phone_no = phone_no;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public Integer getRid() {
	return rid;
}

public void setRid(Integer rid) {
	this.rid = rid;
}

@Override
public String toString() {
	return "User [uid=" + uid + ", uname=" + uname + ", password=" + password + ", phone_no=" + phone_no + ", email="
			+ email + ", rid=" + rid + "]";
}




      
}
