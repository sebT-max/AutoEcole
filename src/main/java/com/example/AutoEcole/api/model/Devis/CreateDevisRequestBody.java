package com.example.AutoEcole.api.model.Devis;

import com.example.AutoEcole.dal.domain.entity.Booking;
import com.example.AutoEcole.dal.domain.entity.Journey;
import com.example.AutoEcole.dal.domain.entity.Planet;

public record CreateDevisRequestBody(
        String name,
        String description
) {
}
