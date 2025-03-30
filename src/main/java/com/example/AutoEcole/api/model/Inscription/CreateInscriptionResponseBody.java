package com.example.AutoEcole.api.model.Inscription;

import com.example.AutoEcole.dal.domain.entity.CodePromo;
import com.example.AutoEcole.dal.domain.enum_.InscriptionStatut;
import com.example.AutoEcole.dal.domain.enum_.StageType;

import java.time.LocalDate;

public record CreateInscriptionResponseBody(
        String message,
        Long id,
        Long userId,
        Long stageId,
        StageType stageType,
        LocalDate dateOfInscription,
        InscriptionStatut inscriptionStatut
        //CodePromo codePromo

) {}
