package com.example.AutoEcole.api.model.DemandeDevis;

import com.example.AutoEcole.dal.domain.entity.DemandeDevis;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.User;

public record CreateDemandeDevisResponseBody(
        Long id,
        String entrepriseName,
        String contactFirstName,
        String contactLastName,
        int numberOfInterns,
        String message
) {
    public static CreateDemandeDevisResponseBody from(DemandeDevis demandeDevis) {
        String entrepriseName = (demandeDevis.getUser() instanceof Entreprise e) ? e.getName() : "Inconnu";
        return new CreateDemandeDevisResponseBody(
                demandeDevis.getId(),
                entrepriseName,
                demandeDevis.getContactFirstName(),
                demandeDevis.getContactLastName(),
                demandeDevis.getNumberOfInterns(),
                demandeDevis.getMessage()
        );
    }
}
