package com.p05tourmgt.bookingservice.services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.p05tourmgmt.bookingservice.dto.BookingDTO;
import com.p05tourmgmt.bookingservice.dto.CategoryDTO;
import com.p05tourmgmt.bookingservice.dto.TourAgencyDTO;
import com.p05tourmgmt.bookingservice.dto.TourPackageDTO;
import com.p05tourmgmt.bookingservice.dto.TourScheduleDTO;
import com.p05tourmgmt.bookingservice.dto.TouristDetailDTO;
import com.p05tourmgt.bookingservice.entities.Booking;
import com.p05tourmgt.bookingservice.entities.Category;
import com.p05tourmgt.bookingservice.entities.TourAgency;
import com.p05tourmgt.bookingservice.entities.TourPackage;
import com.p05tourmgt.bookingservice.entities.TouristDetails;
import com.p05tourmgt.bookingservice.entities.TourSchedule;
import com.p05tourmgt.bookingservice.entities.Tourist;
import com.p05tourmgt.bookingservice.repositories.BookingRepository;
import com.p05tourmgt.bookingservice.repositories.TourScheduleRepository;
import com.p05tourmgt.bookingservice.repositories.TouristRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BookingServices {

    private final BookingRepository bookingRepository;
    private final TouristRepository touristRepository;
    private final TourScheduleRepository tourScheduleRepository;

    public static final String STATUS_CANCELLED = "CANCELLED";

    public BookingServices(BookingRepository bookingRepository,
                           TouristRepository touristRepository,
                           TourScheduleRepository tourScheduleRepository) {
        this.bookingRepository = bookingRepository;
        this.touristRepository = touristRepository;
        this.tourScheduleRepository = tourScheduleRepository;
    }

    public List<BookingDTO> getCancelledBookingsDTOByTourist(int touristId) {
        return bookingRepository.findByTouristTouristIdAndStatus(touristId, STATUS_CANCELLED)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<BookingDTO> getAgencyBookingsDTO(int agencyId) {
        return bookingRepository.findByTourScheduleTourPackageTourAgencyTourAgencyId(agencyId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<BookingDTO> getTouristBookings(int touristId) {
        return bookingRepository.findByTouristTouristId(touristId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public BookingDTO createBookingDTO(int touristId, int scheduleId, List<TouristDetails> details) {
        try {
            Booking booking = createBooking(touristId, scheduleId, details);
            return mapToDTO(booking);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Booking creation failed: " + e.getMessage());
        }
    }

    @Transactional
    public Booking createBooking(int touristId, int scheduleId, List<TouristDetails> details) {
        int count = details.size();

        int updated = tourScheduleRepository.reserveSeats(scheduleId, count);
        if (updated == 0) throw new IllegalStateException("Not enough seats available");

        TourSchedule schedule = tourScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));

        Tourist tourist = touristRepository.findById(touristId)
                .orElseThrow(() -> new EntityNotFoundException("Tourist not found"));

        Booking booking = new Booking();
        booking.setBookingDate(new Date());
        booking.setNoOfTourist(count);
        booking.setStatus("PENDING");
        booking.setTourSchedule(schedule);
        booking.setTourist(tourist);

        for (TouristDetails detail : details) {
            detail.setBooking(booking);
        }
        booking.setTouristDetails(details);

        Booking savedBooking = bookingRepository.save(booking);

        System.out.println("✅ Booking created: Tourist ID " + touristId + ", Booking ID " + savedBooking.getBookingId());
        return savedBooking;
    }

    @Transactional
    public void confirmBooking(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        booking.setStatus("CONFIRMED");
        bookingRepository.save(booking);
    }

    @Transactional
    public void cancelBooking(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);

        TourSchedule schedule = booking.getTourSchedule();
        schedule.setAvailableBookings(schedule.getAvailableBookings() + booking.getNoOfTourist());
        tourScheduleRepository.save(schedule);
    }

    @Transactional
    public void restoreBooking(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        booking.setStatus("CONFIRMED");
        bookingRepository.save(booking);

        TourSchedule schedule = booking.getTourSchedule();
        schedule.setAvailableBookings(schedule.getAvailableBookings() - booking.getNoOfTourist());
        tourScheduleRepository.save(schedule);
    }

    private BookingDTO mapToDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setStatus(booking.getStatus());
//        dto.setBookingDate(booking.getBookingDate().toString());
        dto.setBookingDate(new SimpleDateFormat("yyyy-MM-dd").format(booking.getBookingDate()));


        TourSchedule schedule = booking.getTourSchedule();
        if (schedule != null) {
            TourScheduleDTO scheduleDTO = new TourScheduleDTO();
            scheduleDTO.setScheduleId(schedule.getScheduleId());
            scheduleDTO.setStartDate(schedule.getStartDate());
            scheduleDTO.setEndDate(schedule.getEndDate());
            scheduleDTO.setDuration(schedule.getDuration());
            scheduleDTO.setCost(schedule.getCost());
            scheduleDTO.setAvailableBookings(schedule.getAvailableBookings());

            TourPackage pkg = schedule.getTourPackage();
            if (pkg != null) {
                TourPackageDTO packageDTO = new TourPackageDTO();
                packageDTO.setPackageId(pkg.getPackageId());
                packageDTO.setPname(pkg.getPname());
                packageDTO.setDestination(pkg.getDestination());
                packageDTO.setDescription(pkg.getDescription());

                Category category = pkg.getCategory();
                if (category != null) {
                    CategoryDTO categoryDTO = new CategoryDTO();
                    categoryDTO.setCatId(category.getCatId());
                    categoryDTO.setCname(category.getCname());
                    packageDTO.setCategory(categoryDTO);
                }

                TourAgency agency = pkg.getTourAgency();
                if (agency != null) {
                    TourAgencyDTO agencyDTO = new TourAgencyDTO();
                    agencyDTO.setAgencyId(agency.getTourAgencyId());
                    agencyDTO.setTourAgencyName(agency.getTourAgencyName());
                    agencyDTO.setAgencyEmail(agency.getAgencyEmail());
                    agencyDTO.setPhoneNo(agency.getPhoneNo());
                    agencyDTO.setAddress(agency.getAddress());
                    packageDTO.setTourAgency(agencyDTO);
                }

                scheduleDTO.setTourPackage(packageDTO);
            }

            dto.setTourSchedule(scheduleDTO);
        }

        dto.setCompanions(
            booking.getTouristDetails().stream()
                .map(td -> new TouristDetailDTO(td.getFname(), td.getLname(), td.getAge(), td.getGender()))
                .collect(Collectors.toList())
        );

        return dto;
    }

    private Booking mapToEntity(BookingDTO dto) {
        Booking booking = new Booking();
        booking.setBookingId(dto.getBookingId());
        booking.setStatus(dto.getStatus());
        booking.setBookingDate(java.sql.Date.valueOf(dto.getBookingDate()));

        TourScheduleDTO scheduleDTO = dto.getTourSchedule();
        if (scheduleDTO != null) {
            TourSchedule schedule = new TourSchedule();
            schedule.setScheduleId(scheduleDTO.getScheduleId());
            schedule.setStartDate(scheduleDTO.getStartDate());
            schedule.setEndDate(scheduleDTO.getEndDate());
            schedule.setDuration(scheduleDTO.getDuration());
            schedule.setCost(scheduleDTO.getCost());
            schedule.setAvailableBookings(scheduleDTO.getAvailableBookings());

            TourPackageDTO packageDTO = scheduleDTO.getTourPackage();
            if (packageDTO != null) {
                TourPackage pkg = new TourPackage();
                pkg.setPackageId(packageDTO.getPackageId());
                pkg.setPname(packageDTO.getPname());
                pkg.setDestination(packageDTO.getDestination());
                pkg.setDescription(packageDTO.getDescription());

                CategoryDTO categoryDTO = packageDTO.getCategory();
                if (categoryDTO != null) {
                    Category category = new Category();
                    category.setCatId(categoryDTO.getCatId());
                    category.setCname(categoryDTO.getCname());
                    pkg.setCategory(category);
                }

                TourAgencyDTO agencyDTO = packageDTO.getTourAgency();
                if (agencyDTO != null) {
                    TourAgency agency = new TourAgency();
                    agency.setTourAgencyId(agencyDTO.getAgencyId());
                    agency.setTourAgencyName(agencyDTO.getTourAgencyName());
                    agency.setAgencyEmail(agencyDTO.getAgencyEmail());
                    agency.setPhoneNo(agencyDTO.getPhoneNo());
                    agency.setAddress(agencyDTO.getAddress());
                    pkg.setTourAgency(agency);
                }

                schedule.setTourPackage(pkg);
            }

            booking.setTourSchedule(schedule);
        }

        List<TouristDetails> details = dto.getCompanions().stream().map(tdDTO -> {
            TouristDetails td = new TouristDetails();
            td.setFname(tdDTO.getFname());
            td.setLname(tdDTO.getLname());
            td.setAge(tdDTO.getAge());
            td.setGender(tdDTO.getGender());
            td.setBooking(booking);
            return td;
        }).collect(Collectors.toList());

        booking.setTouristDetails(details);
        booking.setNoOfTourist(details.size());

        return booking;
    }
}
