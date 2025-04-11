package com.example.AutoEcole.api.model.Stage;

import com.example.AutoEcole.dal.domain.entity.Stage;
import com.example.AutoEcole.dal.domain.enum_.StageType;

import java.time.LocalDate;
import java.util.Date;

public record CreateStageRequestBody(
        LocalDate dateDebut,
        LocalDate dateFin,
        String city,
        String street,
        String arrondissement,
        int capacity,
        Double price,
        String organisation
) {
    public Stage toEntity() {
        return new Stage(dateDebut, dateFin, city, street, arrondissement, capacity, price, organisation);
    }
}
