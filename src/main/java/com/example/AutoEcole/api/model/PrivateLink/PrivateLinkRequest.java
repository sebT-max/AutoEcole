package com.example.AutoEcole.api.model.PrivateLink;

import java.time.LocalDateTime;

public record PrivateLinkRequest(
        Long entrepriseId,
        Long stageId
) {}

