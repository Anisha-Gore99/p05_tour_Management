package com.p05tourmgmt.adminservice.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "payment_mode")
public class PaymentMode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mode_id")
    private Integer modeId;

    @Column(name = "mode_name")
    private String modeName;

    // Getters and setters
    public Integer getModeId() {
        return modeId;
    }

    public void setModeId(Integer modeId) {
        this.modeId = modeId;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }
}

