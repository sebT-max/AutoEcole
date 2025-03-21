package com.example.AutoEcole.api.model.Devis;

import com.example.AutoEcole.dal.domain.entity.Devis;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.Facture;

import java.time.LocalDate;
import java.util.List;

public record CreateDevisRequestBody(
        Entreprise entreprise,
        String numeroDevis,
        Double estimated_amount,
        LocalDate dateOfDemand,
        List<Facture>factures
) {
    public Devis toEntity(Entreprise entreprise) {
    return  new Devis(entreprise,numeroDevis, estimated_amount,dateOfDemand,factures);
    }
}