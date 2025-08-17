package com.p05tourmgmt.adminservice.entities;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="refund")
public class Refund {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="refund_id")
	int refund_id;
	@Column(name="refund_amount")
	double refund_amount;
	@Column(name="refund_date")
	Date refund_date;
	@Column(name="refund_status")
	String refund_status;
	@Column(name="cancellation_id")
	Integer cancellation_id;
	@Column(name="payment_id")
	Integer payment_id;
	public Refund() {
		super();
	}
	public Refund(int refund_id, double refund_amount, Date refund_date, String refund_status, Integer cancellation_id,
			Integer payment_id) {
		super();
		this.refund_id = refund_id;
		this.refund_amount = refund_amount;
		this.refund_date = refund_date;
		this.refund_status = refund_status;
		this.cancellation_id = cancellation_id;
		this.payment_id = payment_id;
	}
	public int getRefund_id() {
		return refund_id;
	}
	public void setRefund_id(int refund_id) {
		this.refund_id = refund_id;
	}
	public double getRefund_amount() {
		return refund_amount;
	}
	public void setRefund_amount(double refund_amount) {
		this.refund_amount = refund_amount;
	}
	public Date getRefund_date() {
		return refund_date;
	}
	public void setRefund_date(Date refund_date) {
		this.refund_date = refund_date;
	}
	public String getRefund_status() {
		return refund_status;
	}
	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
	}
	public Integer getCancellation_id() {
		return cancellation_id;
	}
	public void setCancellation_id(Integer cancellation_id) {
		this.cancellation_id = cancellation_id;
	}
	public Integer getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(Integer payment_id) {
		this.payment_id = payment_id;
	}
	@Override
	public String toString() {
		return "Refund [refund_id=" + refund_id + ", refund_amount=" + refund_amount + ", refund_date=" + refund_date
				+ ", refund_status=" + refund_status + ", cancellation_id=" + cancellation_id + ", payment_id="
				+ payment_id + "]";
	}
	
}
