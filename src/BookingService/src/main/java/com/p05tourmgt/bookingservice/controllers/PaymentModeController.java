package com.p05tourmgt.bookingservice.controllers;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.p05tourmgt.bookingservice.entities.PaymentMode;
import com.p05tourmgt.bookingservice.repositories.PaymentModeRepository;

@RestController
@RequestMapping("/api/modes")

public class PaymentModeController {

 private final PaymentModeRepository repo;

 public PaymentModeController(PaymentModeRepository repo) {
     this.repo = repo;
 }

 @GetMapping
 public List<ModeDTO> getAll() {
     return repo.findAll().stream().map(ModeDTO::from).collect(Collectors.toList());
 }

 @GetMapping("/{id}")
 public ResponseEntity<ModeDTO> getOne(@PathVariable Integer id) {
     return repo.findById(id)
             .map(pm -> ResponseEntity.ok(ModeDTO.from(pm)))
             .orElse(ResponseEntity.notFound().build());
 }

 // Lightweight DTO to avoid serializing payments collection
 static class ModeDTO {
     public Integer modeId;
     public String modeName;

     static ModeDTO from(PaymentMode m) {
         ModeDTO dto = new ModeDTO();
         dto.modeId = m.getModeId();
         dto.modeName = m.getModeName();
         return dto;
     }
 }
}
