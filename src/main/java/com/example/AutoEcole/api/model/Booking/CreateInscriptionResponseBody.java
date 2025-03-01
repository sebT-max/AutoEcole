package com.example.AutoEcole.api.model.Booking;

import java.time.LocalDate;

public record CreateInscriptionResponseBody(
        String message,
        Long bookingId,
        String destination,
        LocalDate date,
        int nbrPerson
) {
    public CreateInscriptionResponseBody(String message) {
        this(message, null, null, null, 0);
    }
}