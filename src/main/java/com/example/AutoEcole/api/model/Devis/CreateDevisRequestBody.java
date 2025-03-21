package com.example.AutoEcole.api.model.Devis;

import com.example.AutoEcole.dal.domain.entity.Devis;

public record CreateDevisRequestBody(
        String name,
        String description
) {
    public Devis toEntity() {
    }
}
