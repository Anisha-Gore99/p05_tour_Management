package com.p05tourmgt.bookingservice.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="payment_mode")
public class PaymentMode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mode_id")
    private int modeId;

    @Column(name = "mode_name", unique = true, length = 255)
    private String modeName;

    @OneToMany(mappedBy = "paymentMode")
    private List<Payment> payments;

	public PaymentMode() {
		super();
	}

	public PaymentMode(int modeId, String modeName, List<Payment> payments) {
		super();
		this.modeId = modeId;
		this.modeName = modeName;
		this.payments = payments;
	}

	public int getModeId() {
		return modeId;
	}

	public void setModeId(int modeId) {
		this.modeId = modeId;
	}

	public String getModeName() {
		return modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
    
    
}