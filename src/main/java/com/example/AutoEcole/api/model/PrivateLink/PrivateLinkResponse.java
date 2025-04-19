package com.example.AutoEcole.api.model.PrivateLink;

import com.example.AutoEcole.api.model.Stage.StageInfoResponse;

import java.time.LocalDateTime;

public record PrivateLinkResponse(
        String token,
        LocalDateTime expirationDate,
        Long entrepriseId,
        String entrepriseNom,
        StageInfoResponse stageInfo
) {}


//Lien créé pour Jupiler.
//http://localhost:4200/inscription/68523bdb-5452-4a5e-967a-724b30d0b7ab (expire le 26/04/2025 15:41:57)
