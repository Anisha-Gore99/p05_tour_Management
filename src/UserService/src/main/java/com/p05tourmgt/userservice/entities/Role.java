package com.p05tourmgt.userservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(
    name = "role",
    uniqueConstraints = { @UniqueConstraint(columnNames = "rname") }
)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rid")
    private Integer rid;

    @NotBlank(message = "Role name is required")
    @Column(name = "rname", nullable = false, unique = true, length = 255)
    private String rname;

    public Role() {}
    public Role(Integer rid, String rname) { this.rid = rid; this.rname = rname; }

    public Integer getRid() { return rid; }
    public void setRid(Integer rid) { this.rid = rid; }
    public String getRname() { return rname; }
    public void setRname(String rname) { this.rname = rname; }
}

