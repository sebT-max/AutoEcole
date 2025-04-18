package com.example.AutoEcole.api.model.PrivateLink;

import com.example.AutoEcole.dal.domain.entity.PrivateLink;

import java.time.LocalDateTime;

public record PrivateLinkValidationResponse(
        String entrepriseNom,
        String nomStage,
        LocalDateTime dateExpiration,
        boolean lienActif,
        int usagesRestants
) {}
