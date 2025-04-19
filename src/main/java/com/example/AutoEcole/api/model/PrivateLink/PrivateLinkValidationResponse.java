package com.example.AutoEcole.api.model.PrivateLink;

import com.example.AutoEcole.api.model.Stage.CreateStageResponseBody;
import com.example.AutoEcole.api.model.Stage.StageInfoResponse;
import com.example.AutoEcole.dal.domain.entity.Stage;

import java.time.LocalDateTime;

public record PrivateLinkValidationResponse(
        String entrepriseNom,
        String nomStage,
        StageInfoResponse stage, // ⬅️ Utilisation du DTO ici
        LocalDateTime dateExpiration,
        boolean lienActif,
        int usagesRestants
) {
    public static PrivateLinkValidationResponse fromEntities(
            String entrepriseNom,
            String nomStage,
            Stage stage,
            LocalDateTime dateExpiration,
            boolean lienActif,
            int usagesRestants
    ) {
        return new PrivateLinkValidationResponse(
                entrepriseNom,
                nomStage,
                StageInfoResponse.fromEntity(stage), // ⬅️ Conversion ici
                dateExpiration,
                lienActif,
                usagesRestants
        );
    }
}

