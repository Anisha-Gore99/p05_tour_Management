package com.p05tourmgt.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.p05tourmgt.bookingservice.entities.Refund;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Integer> {
	@Query("DELETE FROM Refund r WHERE r.cancellation.cancellationId = :cancellationId")
    @Modifying
    void deleteByCancellationId(@Param("cancellationId") int cancellationId);
}
