package com.example.AutoEcole.api.model.PrivateLink;

import java.time.LocalDateTime;

public record PrivateLinkResponse(
        String token,
        LocalDateTime expirationDate,
        Long entrepriseId,
        String entrepriseNom,
        Long stageId
) {}

