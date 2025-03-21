package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.api.model.Inscription.CreateInscriptionRequestBody;
import com.example.AutoEcole.api.model.Inscription.CreateInscriptionResponseBody;
import com.example.AutoEcole.dal.domain.entity.Inscription;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InscriptionService {

    CreateInscriptionResponseBody createBooking(CreateInscriptionRequestBody request);

    List<Inscription> getAllBooking();

    List<Inscription> getBookingByUserId(Long userId);

    List<Inscription> getInscriptionsByUserId(Long userId);

    Inscription getBookingById(Long id);

    @Transactional
    boolean update(Long id, Inscription inscription);

    boolean delete(Long id);
}
