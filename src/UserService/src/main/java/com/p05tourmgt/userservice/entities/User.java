package com.p05tourmgt.userservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(
    name = "user",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phone_no")
    }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private Integer uid;

    @NotBlank(message = "Username is required")
    @Column(name = "uname", nullable = false, length = 255)
    private String uname;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 60, message = "Password must be 6–60 chars")
    @Column(name = "password", nullable = false, length = 60) // bcrypt fits 60
    private String password;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10,}", message = "Phone must have at least 10 digits")
    @Column(name = "phone_no", nullable = false, length = 20)
    private String phoneNo;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @NotNull(message = "Role ID is required")
    @ManyToOne
    @JoinColumn(name = "rid", nullable = false)
    private Role role;

	public User() {
		super();
	}

	public User(Integer uid, @NotBlank(message = "Username is required") String uname,
			@NotBlank(message = "Password is required") @Size(min = 6, max = 60, message = "Password must be 6–60 chars") String password,
			@NotBlank(message = "Phone number is required") @Pattern(regexp = "\\d{10,}", message = "Phone must have at least 10 digits") String phoneNo,
			@NotBlank(message = "Email is required") @Email(message = "Invalid email") String email,
			@NotNull(message = "Role ID is required") Role role) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.password = password;
		this.phoneNo = phoneNo;
		this.email = email;
		this.role = role;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	
	
    
}
