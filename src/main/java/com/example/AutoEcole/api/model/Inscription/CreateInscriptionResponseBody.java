package com.example.AutoEcole.api.model.Inscription;

import com.example.AutoEcole.dal.domain.entity.Inscription;
import com.example.AutoEcole.dal.domain.entity.Stage;

import java.time.LocalDate;

public record CreateInscriptionResponseBody(
        String message,
        Long inscriptionId,
        Stage stageId,
        LocalDate dateOfInscription,
        int nbrPerson
) {
    public CreateInscriptionResponseBody(String message) {
        this(message, null, null, null, 0);
    }
}