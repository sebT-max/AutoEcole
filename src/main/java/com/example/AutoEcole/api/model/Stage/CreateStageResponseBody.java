package com.example.AutoEcole.api.model.Stage;

import com.example.AutoEcole.dal.domain.entity.Stage;

import java.time.LocalDate;

public record CreateStageResponseBody(
        Long id,
        LocalDate dateDebut,
        LocalDate dateFin,
        String city,
        String street,
        String arrondissement,
        int capacity,
        Double price,
        String organisation
) {
    public static CreateStageResponseBody fromEntity(Stage stage) {
        return new CreateStageResponseBody(
                stage.getId(),
                stage.getDateDebut(),
                stage.getDateFin(),
                stage.getCity(),
                stage.getStreet(),
                stage.getArrondissement(),
                stage.getCapacity(),
                stage.getPrice(),
                stage.getOrganisation()
        );
    }
}
