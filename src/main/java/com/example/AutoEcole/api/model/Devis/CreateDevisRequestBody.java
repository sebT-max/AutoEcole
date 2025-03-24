package com.example.AutoEcole.api.model.Devis;

import com.example.AutoEcole.dal.domain.entity.Devis;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.Facture;
import com.example.AutoEcole.dal.domain.entity.User;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;
public record CreateDevisRequestBody(
        String message,
        Long entrepriseId,
        String numeroDevis,
        LocalDate dateOfDemand,
        Double estimatedAmount,
        List<Long> factureIds
) {}
