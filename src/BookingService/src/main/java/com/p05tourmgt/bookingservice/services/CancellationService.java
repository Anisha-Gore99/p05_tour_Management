package com.p05tourmgt.bookingservice.services;

import com.p05tourmgt.bookingservice.entities.*;
import com.p05tourmgt.bookingservice.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CancellationService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CancellationRepository cancellationRepository;
    @Autowired
    private TourScheduleRepository tourScheduleRepository;
    @Autowired
    private RefundRepository refundRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public Cancellation cancelBooking(int bookingId, String cancelReason) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isEmpty()) {
            throw new EntityNotFoundException("Booking with ID " + bookingId + " not found.");
        }

        Booking booking = optionalBooking.get();
        if ("cancelled".equalsIgnoreCase(booking.getStatus())) {
            throw new IllegalStateException("Booking is already cancelled.");
        }

        // Increment available seats
        TourSchedule tourSchedule = booking.getTourSchedule();
        tourSchedule.setAvailableBookings(tourSchedule.getAvailableBookings() + booking.getNoOfTourist());
        tourScheduleRepository.save(tourSchedule);

        // Update booking status
        booking.setStatus("cancelled");
        bookingRepository.save(booking);

        // Create and save cancellation record
        Cancellation cancellation = new Cancellation();
        cancellation.setBooking(booking);
        cancellation.setCancelDate(new Date());
        cancellation.setCancelReason(cancelReason);
        cancellation.setCancellationStatus("requested");
        return cancellationRepository.save(cancellation);
    }

    @Transactional
    public Refund requestRefund(int cancellationId) {
        Optional<Cancellation> optionalCancellation = cancellationRepository.findById(cancellationId);
        if (optionalCancellation.isEmpty()) {
            throw new EntityNotFoundException("Cancellation with ID " + cancellationId + " not found.");
        }

        Cancellation cancellation = optionalCancellation.get();
        if (!"requested".equalsIgnoreCase(cancellation.getCancellationStatus())) {
            throw new IllegalStateException("Refund request is not possible for this cancellation status.");
        }
        
        // Find the payment associated with the booking of the cancellation
        Payment payment = paymentRepository.findByBooking(cancellation.getBooking())
                .orElseThrow(() -> new EntityNotFoundException("Payment not found for this booking."));

        // Dummy refund amount calculation using double
        double refundAmount = payment.getAmount() * 0.90;

        Refund refund = new Refund();
        refund.setCancellation(cancellation);
        refund.setPayment(payment);
        refund.setRefundAmount(refundAmount);
        refund.setRefundDate(new Date());
        refund.setRefundStatus("initiated");
        
        cancellation.setCancellationStatus("refund initiated");
        cancellationRepository.save(cancellation);

        return refundRepository.save(refund);
    }
    
//    public List<Cancellation> getAllCancellations() {
//        return cancellationRepository.findAll();
//    }

//    public Optional<Cancellation> getCancellationDetails(int cancellationId) {
//        return cancellationRepository.findById(cancellationId);
//    }
    
//    @Transactional
//    public void deleteCancellation(int cancellationId) {
//        if (!cancellationRepository.existsById(cancellationId)) {
//            throw new EntityNotFoundException("Cancellation with ID " + cancellationId + " not found.");
//        }
//        cancellationRepository.deleteById(cancellationId);
//    }
    
//    @Transactional
//    public void deleteCancellation(int cancellationId) {
//        if (!cancellationRepository.existsById(cancellationId)) {
//            throw new EntityNotFoundException("Cancellation with ID " + cancellationId + " not found.");
//        }
//
//        // First, delete any associated refund records
//        refundRepository.deleteByCancellationId(cancellationId);
//
//        // Then, delete the cancellation record itself
//        cancellationRepository.deleteById(cancellationId);
//    }
}