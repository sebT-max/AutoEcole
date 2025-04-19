package com.example.AutoEcole.api.model.Stage;

import com.example.AutoEcole.dal.domain.entity.Stage;

public record StageInfoResponse(
        Long id,
        String street,
        String city,
        String startDate,
        String endDate// ou un autre format, selon ce que tu souhaites
) {
    public static StageInfoResponse fromEntity(Stage stage) {
        return new StageInfoResponse(
                stage.getId(),
                stage.getStreet(),
                stage.getCity(),
                stage.getDateDebut().toString(),
                stage.getDateFin().toString()// Ou un formatage plus spécifique
        );
    }
}
