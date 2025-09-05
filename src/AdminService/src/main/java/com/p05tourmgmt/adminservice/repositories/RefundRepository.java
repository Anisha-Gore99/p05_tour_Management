package com.p05tourmgmt.adminservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p05tourmgmt.adminservice.entities.Refund;
@Repository
public interface RefundRepository extends JpaRepository<Refund, Integer> {

}
