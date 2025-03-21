package com.example.AutoEcole.api.model.Inscription;

import com.example.AutoEcole.dal.domain.entity.*;
import com.example.AutoEcole.dal.domain.enum_.InscriptionStatut;

import java.time.LocalDate;

public record CreateInscriptionRequestBody(
        Long userId,
        Long stageId,
        InscriptionStatut inscriptionStatut,
        LocalDate dateOfInscription,
        Integer nbrPerson,
        CodePromo codePromo,
        Boolean acceptTerms
) {
    public Inscription toEntity(User user, Stage stage) {
        return new Inscription(user, stage,inscriptionStatut, dateOfInscription, nbrPerson,codePromo,acceptTerms);
    }
}


