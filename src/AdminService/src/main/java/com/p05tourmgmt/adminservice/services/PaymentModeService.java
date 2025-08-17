package com.p05tourmgmt.adminservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p05tourmgmt.adminservice.entities.PaymentMode;
import com.p05tourmgmt.adminservice.repositories.PaymentModeRepository;

@Service
public class PaymentModeService {

    @Autowired
    private PaymentModeRepository paymentModeRepository;

    public List<PaymentMode> getAllPaymentModes() {
        return paymentModeRepository.findAll();
    }
}
