package com.p05tourmgt.bookingservice.services;

import com.p05tourmgt.bookingservice.entities.*;
import com.p05tourmgt.bookingservice.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServices {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private TourScheduleRepository tourScheduleRepository;
    @Autowired
    private TouristRepository touristRepository;
    @Autowired
    private TouristDetailsRepository touristDetailsRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentModeRepository paymentModeRepository;

//    @Transactional
//    public Booking createBooking(int touristId, int scheduleId, List<TouristDetails> touristDetailsList, int modeId) {
//        Optional<TourSchedule> optionalSchedule = tourScheduleRepository.findById(scheduleId);
//        Optional<Tourist> optionalTourist = touristRepository.findById(touristId);
//        Optional<PaymentMode> optionalPaymentMode = paymentModeRepository.findById(modeId);
//
//        if (optionalSchedule.isEmpty() || optionalTourist.isEmpty() || optionalPaymentMode.isEmpty()) {
//            throw new IllegalArgumentException("Invalid touristId, scheduleId, or modeId.");
//        }
//
//        TourSchedule tourSchedule = optionalSchedule.get();
//        Tourist tourist = optionalTourist.get();
//        PaymentMode paymentMode = optionalPaymentMode.get();
//        int noOfTourists = touristDetailsList.size();
//
//        if (tourSchedule.getAvailableBookings() < noOfTourists) {
//            throw new IllegalStateException("Not enough available seats for this tour.");
//        }
//
//        // Decrement available seats
//        tourSchedule.setAvailableBookings(tourSchedule.getAvailableBookings() - noOfTourists);
//        tourScheduleRepository.save(tourSchedule);
//
//        // Create the new booking
//        Booking newBooking = new Booking();
//        newBooking.setBookingDate(new Date());
//        newBooking.setNoOfTourist(noOfTourists);
//        newBooking.setStatus("confirmed");
//        newBooking.setTourSchedule(tourSchedule);
//        newBooking.setTourist(tourist);
//        
//        // Save the booking first to get its ID
//        Booking savedBooking = bookingRepository.save(newBooking);
//
//        // Set the booking for each tourist detail and save
//        touristDetailsList.forEach(detail -> detail.setBooking(savedBooking));
//        savedBooking.setTouristDetails(touristDetailsList);
//        
//        // Create and save the payment record using double
//        Payment payment = new Payment();
//        payment.setAmount(tourSchedule.getCost().doubleValue() * noOfTourists);
//        payment.setPaymentDate(new Date());
//        payment.setPaymentMode(paymentMode);
//        payment.setBooking(savedBooking);
//        paymentRepository.save(payment);
//        
//        // The Booking entity you provided doesn't have a `payment` field, so this line is commented out.
//        // If your Booking entity had a `payment` field, you would uncomment this line.
//        // savedBooking.setPayment(payment);
//        return bookingRepository.save(savedBooking);
//    }
//    
    
    @Transactional
    public Booking createBooking(int touristId, int scheduleId, List<TouristDetails> touristDetailsList, int modeId) {
        Optional<TourSchedule> optionalSchedule = tourScheduleRepository.findById(scheduleId);
        Optional<Tourist> optionalTourist = touristRepository.findById(touristId);
        Optional<PaymentMode> optionalPaymentMode = paymentModeRepository.findById(modeId);

        if (optionalSchedule.isEmpty() || optionalTourist.isEmpty() || optionalPaymentMode.isEmpty()) {
            throw new IllegalArgumentException("Invalid touristId, scheduleId, or modeId.");
        }

        TourSchedule tourSchedule = optionalSchedule.get();
        Tourist tourist = optionalTourist.get();
        PaymentMode paymentMode = optionalPaymentMode.get();
        int noOfTourists = touristDetailsList.size();

        if (tourSchedule.getAvailableBookings() < noOfTourists) {
            throw new IllegalStateException("Not enough available seats for this tour.");
        }
        
        // Create the new booking
        Booking newBooking = new Booking();
        newBooking.setBookingDate(new Date());
        newBooking.setNoOfTourist(noOfTourists);
        newBooking.setStatus("confirmed");
        newBooking.setTourSchedule(tourSchedule);
        newBooking.setTourist(tourist);

        // Save the booking first to get its ID
        Booking savedBooking = bookingRepository.save(newBooking);

        // Set the booking for each tourist detail and save
        touristDetailsList.forEach(detail -> detail.setBooking(savedBooking));
        savedBooking.setTouristDetails(touristDetailsList);
        touristDetailsRepository.saveAll(touristDetailsList); // Save all tourist details at once

        // Create and save the payment record
        Payment payment = new Payment();
        payment.setAmount(tourSchedule.getCost().doubleValue() * noOfTourists);
        payment.setPaymentDate(new Date());
        payment.setPaymentMode(paymentMode);
        payment.setBooking(savedBooking);
        paymentRepository.save(payment);
        
        // Now that all sub-operations are successful, decrement available seats
        tourSchedule.setAvailableBookings(tourSchedule.getAvailableBookings() - noOfTourists);
        tourScheduleRepository.save(tourSchedule);

        return bookingRepository.save(savedBooking);
    }
    
    public Optional<Booking> getBookingDetails(int bookingId) {
        return bookingRepository.findById(bookingId);
    }

    public List<Booking> getTouristBookings(int touristId) {
        return bookingRepository.findByTouristTouristId(touristId);
    }
    
    // This method is now commented out because the TourSchedule entity you provided
    // no longer has a direct relationship to TourPackage, but instead an int `packageId`.
    // public List<Booking> getAgencyBookings(int agencyId) {
    //     return bookingRepository.findByTourScheduleTourPackageTourAgencyId(agencyId);
    // }

    @Transactional
    public Booking updateBooking(int bookingId, Booking updatedBooking) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isEmpty()) {
            throw new EntityNotFoundException("Booking with ID " + bookingId + " not found.");
        }
        
        Booking existingBooking = optionalBooking.get();
        existingBooking.setBookingDate(updatedBooking.getBookingDate());
        existingBooking.setStatus(updatedBooking.getStatus());
        existingBooking.setNoOfTourist(updatedBooking.getNoOfTourist());
        return bookingRepository.save(existingBooking);
    }

    @Transactional
    public void deleteBooking(int bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new EntityNotFoundException("Booking with ID " + bookingId + " not found.");
        }
        bookingRepository.deleteById(bookingId);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}