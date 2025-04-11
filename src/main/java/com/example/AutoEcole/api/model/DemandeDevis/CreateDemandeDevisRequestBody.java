package com.example.AutoEcole.api.model.DemandeDevis;

import com.example.AutoEcole.dal.domain.entity.Entreprise;
import jakarta.persistence.Column;

public record CreateDemandeDevisRequestBody(
        Entreprise entreprise,
        String contactFirstName,
        String contactLastName,
        int numberOfInterns,
        String message
) {
}
