package com.example.AutoEcole.api.model.Booking;

import com.example.AutoEcole.dal.domain.entity.*;

import java.time.LocalDate;

public record CreateInscriptionRequestBody(
        Long userId,
        Long journeyId,
        LocalDate date,
        Integer nbrPerson
) {
    public Booking toEntity(User user, Journey journey) {
        return new Booking(user, journey, date, nbrPerson);
    }
}


