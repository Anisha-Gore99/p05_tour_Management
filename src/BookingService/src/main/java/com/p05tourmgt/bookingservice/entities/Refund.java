package com.p05tourmgt.bookingservice.entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="refund")
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refund_id")
    private int refundId;

    @Column(name = "refund_amount")
    private double refundAmount;

    @Column(name = "refund_date")
    private Date refundDate;

    @Column(name = "refund_status", length = 255)
    private String refundStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancellation_id", unique = true)
    private Cancellation cancellation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", unique = true)
    private Payment payment;
    
	public Refund() {
		super();
	}

	public Refund(int refundId, double refundAmount, Date refundDate, String refundStatus, Cancellation cancellation,
			Payment payment) {
		super();
		this.refundId = refundId;
		this.refundAmount = refundAmount;
		this.refundDate = refundDate;
		this.refundStatus = refundStatus;
		this.cancellation = cancellation;
		this.payment = payment;
	}

	public int getRefundId() {
		return refundId;
	}

	public void setRefundId(int refundId) {
		this.refundId = refundId;
	}

	public double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public Cancellation getCancellation() {
		return cancellation;
	}

	public void setCancellation(Cancellation cancellation) {
		this.cancellation = cancellation;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
    
    
    
    
}