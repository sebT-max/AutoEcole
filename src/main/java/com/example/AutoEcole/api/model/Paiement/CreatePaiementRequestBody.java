package com.example.AutoEcole.api.model.Paiement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreatePaiementRequestBody(
        @NotBlank(message = "The universalNumber do not be blank")
        String montant,
        @NotBlank(message = "The place of issue do not be blank")
        String statut,
        @NotNull(message = "The birth Date do not be null")
        LocalDate mode,
        @NotNull(message = "The expire Date do not be null")
        LocalDate Date
) {

}
