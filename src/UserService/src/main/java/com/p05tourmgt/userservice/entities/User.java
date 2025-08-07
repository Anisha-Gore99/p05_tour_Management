package com.p05tourmgt.userservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private Integer uid;

    @Column(name = "uname", length = 255)
    private String uname;

    @Column(name = "password", unique = true, nullable = false, length = 60)
    private String password;

    @Column(name = "phone_no", unique = true, length = 255)
    private String phone_no;

    @Column(name = "email", unique = true, nullable = false, length = 255)
    private String email;

    @ManyToOne
    @JoinColumn(name = "rid", nullable = false)
    private Role rid;

    
	public User() {
		super();
	}

	public User(Integer uid, String uname, String password, String phone_no, String email, Role rid) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.password = password;
		this.phone_no = phone_no;
		this.email = email;
		this.rid = rid;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
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

	public Role getRid() {
		return rid;
	}

	public void setRid(Role rid) {
		this.rid = rid;
	}

}
