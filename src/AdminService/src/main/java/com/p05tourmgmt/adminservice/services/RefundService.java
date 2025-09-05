package com.p05tourmgmt.adminservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p05tourmgmt.adminservice.entities.Refund;
import com.p05tourmgmt.adminservice.repositories.RefundRepository;

@Service
public class RefundService {
   
	 @Autowired
	    private RefundRepository refundRepository;

	    public List<Refund> getAllRefunds() {
	        return refundRepository.findAll();
	    }

	    public Refund approveRefund(int refund_id) {
	        Refund refund = refundRepository.findById(refund_id).orElse(null);
	        if (refund != null) {
	            refund.setRefund_status("approved");
	            return refundRepository.save(refund);
	        }
	        return null;
	    }
}
