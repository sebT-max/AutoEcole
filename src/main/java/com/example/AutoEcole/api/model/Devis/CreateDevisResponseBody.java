package com.example.AutoEcole.api.model.Devis;

import java.time.LocalDate;
import java.util.List;

public record CreateDevisResponseBody(
        String message,
        Long entrepriseId,
        String numeroDevis,
        LocalDate dateOfDemand,
        Double estimatedAmount,
        List<Long> factureIds
) {}
