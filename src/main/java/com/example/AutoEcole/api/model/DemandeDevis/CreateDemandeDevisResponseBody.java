package com.example.AutoEcole.api.model.DemandeDevis;

import com.example.AutoEcole.dal.domain.entity.Entreprise;

public record CreateDemandeDevisResponseBody(
        String message,
        Entreprise entreprise,
        String contactFirstName,
        String contactLastName,
        int numberOfInterns,
        boolean acceptDevis
) {
}
