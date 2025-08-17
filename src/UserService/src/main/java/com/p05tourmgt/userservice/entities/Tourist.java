package com.p05tourmgt.userservice.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tourist")
public class Tourist {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "tourist_id")   // <-- map to existing AI PK column
	    private Integer tid;
	 
    @NotBlank(message = "First name is required")
    private String fname;

    @NotBlank(message = "Last name is required")
    private String lname;

    @NotBlank(message = "Address is required")
    private String address;

    private String dob;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "uid", nullable = false)
    @Valid                       // ✅ validate nested user
    private User uid;            // ✅ field name must be 'uid' to match JSON

    public Tourist() {}

    // getters/setters
    public Integer getTid() { return tid; }
    public void setTid(Integer tid) { this.tid = tid; }

    public String getFname() { return fname; }
    public void setFname(String fname) { this.fname = fname; }

    public String getLname() { return lname; }
    public void setLname(String lname) { this.lname = lname; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public User getUid() { return uid; }
    public void setUid(User uid) { this.uid = uid; }
}

