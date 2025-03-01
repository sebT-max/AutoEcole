package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.bll.service.BookingService;
import com.example.AutoEcole.dal.repository.InscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final InscriptionRepository bookingRepository;

    public com.example.AutoEcole.dal.domain.entity.Inscription createBooking(com.example.AutoEcole.dal.domain.entity.Inscription booking) {
        return bookingRepository.save(booking);
    }

    public Optional<com.example.AutoEcole.dal.domain.entity.Inscription> getBookingWithDetails(Long id) {
        return bookingRepository.findByIdWithDetails(id);
    }
}