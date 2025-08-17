// src/main/java/com/p05tourmgt/bookingservice/services/BookingServices.java
package com.p05tourmgt.bookingservice.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.p05tourmgt.bookingservice.entities.Booking;
import com.p05tourmgt.bookingservice.entities.TouristDetails;
import com.p05tourmgt.bookingservice.entities.TourSchedule;
import com.p05tourmgt.bookingservice.repositories.BookingRepository;
import com.p05tourmgt.bookingservice.repositories.TourScheduleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BookingServices {

    private final BookingRepository bookingRepository;
    private final TourScheduleRepository tourScheduleRepository;

    public BookingServices(BookingRepository bookingRepository,
                           TourScheduleRepository tourScheduleRepository) {
        this.bookingRepository = bookingRepository;
        this.tourScheduleRepository = tourScheduleRepository;
    }

    @Transactional
    public Booking createBooking(int touristId, int scheduleId, List<TouristDetails> details, int modeId) {
        int count = details.size();

        // 1) Try to reserve seats atomically
        int updated = tourScheduleRepository.reserveSeats(scheduleId, count);
        if (updated == 0) {
            throw new IllegalStateException("Not enough seats available");
        }

        // 2) Load the schedule (now with reduced availability)
        TourSchedule schedule = tourScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

        // 3) Build the booking using ONLY fields you actually have
        Booking b = new Booking();
        b.setBookingDate(new Date());
        b.setNoOfTourist(count);
        b.setStatus("PENDING"); // or whatever default you use

        // If your Booking has a relation to TourSchedule, set it here:
        // b.setTourSchedule(schedule);   // only if this method exists in your Booking

        // Attach tourist details back to booking (if mapped)
        for (TouristDetails d : details) {
            d.setBooking(b); // requires @ManyToOne Booking in TouristDetails
        }
        b.setTouristDetails(details);   // requires @OneToMany(mappedBy="booking") in Booking

        // If you need to link a Tourist/User, do that here according to your model:
        // Tourist tourist = touristRepository.findById(touristId).orElseThrow(...);
        // b.setTourist(tourist);

        // 4) Save
        return bookingRepository.save(b);
    }

    // If you had this in other parts of your app:
    public List<Booking> getTouristBookings(int touristId) {
        return bookingRepository.findByTouristTouristId(touristId); // ensure this method exists in BookingRepository
    }

    public Booking updateBooking(int bookingId, Booking updated) {
        Booking existing = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        // copy allowed fields here...
        return bookingRepository.save(existing);
    }

    public void deleteBooking(int bookingId) {
        Booking existing = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        bookingRepository.delete(existing);
    }

    // Placeholder: you can wire online-vs-offline logic later if needed.
    public boolean requiresOnlinePayment(int modeId) {
        return false;
    }
    @Transactional
    public void setStatus(int bookingId, String status) {
        Booking b = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        b.setStatus(status);  // ensure your Booking has setStatus
        bookingRepository.save(b);
    }
   

}
