package com.p05tourmgt.userservice.entities;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "tourist")
public class Tourist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tourist_id")
    private Integer tourist_id;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @NotBlank(message = "Address is required")
    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @NotBlank(message = "First name is required")
    @Column(name = "fname", nullable = false, length = 255)
    private String fname;

    @NotBlank(message = "Last name is required")
    @Column(name = "lname", nullable = false, length = 255)
    private String lname;

    @Valid
    @NotNull(message = "Account details are required")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uid", nullable = false)
    private User uid;

    public Tourist() {}

    public Tourist(Integer tourist_id, LocalDate dob, String address, String fname, String lname, User uid) {
        this.tourist_id = tourist_id;
        this.dob = dob;
        this.address = address;
        this.fname = fname;
        this.lname = lname;
        this.uid = uid;
    }

    public Integer getTourist_id() { return tourist_id; }
    public void setTourist_id(Integer tourist_id) { this.tourist_id = tourist_id; }
    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getFname() { return fname; }
    public void setFname(String fname) { this.fname = fname; }
    public String getLname() { return lname; }
    public void setLname(String lname) { this.lname = lname; }
    public User getUid() { return uid; }
    public void setUid(User uid) { this.uid = uid; }
}
