package com.p05tourmgmt.adminservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.p05tourmgmt.adminservice.entities.PaymentMode;

public interface PaymentModeRepository extends JpaRepository<PaymentMode, Integer> {

}
