package com.p05tourmgmt.adminservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p05tourmgmt.adminservice.entities.Refund;
import com.p05tourmgmt.adminservice.services.RefundService;

@RestController
@RequestMapping("/api/admin/refunds")
public class RefundController {
	@Autowired
    private RefundService refundService;

    @GetMapping
    public ResponseEntity<List<Refund>> getAllRefunds() {
        return ResponseEntity.ok(refundService.getAllRefunds());
    }

    @PutMapping("/{refundId}/approve")
    public ResponseEntity<Refund> approveRefund(@PathVariable int refund_id) {
        Refund updated = refundService.approveRefund(refund_id);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
}
