package com.example.AutoEcole.api.model.Stage;

import java.time.LocalDate;

public record CreateStageResponseBody(
        String message,
        LocalDate getDateDeStage,
        Double Price,
        String city,
        String street,
        String arrondissement,
        int capacity,
        String organisation
) {

}
