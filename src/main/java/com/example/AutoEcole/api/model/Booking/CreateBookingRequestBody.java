package com.example.AutoEcole.api.model.Booking;

import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.List;

public record CreateBookingRequestBody(
        User user,
        com.example.AutoEcole.dal.domain.entity.Paiement planet,
        Date date,
        Integer nombreParticipants,
        List<com.example.AutoEcole.dal.domain.entity.Document> bookingActivities
) {

}
