package com.p05tourmgmt.adminservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.p05tourmgmt.adminservice.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
