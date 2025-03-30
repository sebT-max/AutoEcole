package com.example.AutoEcole.api.model.Inscription;

import com.example.AutoEcole.dal.domain.entity.*;
import com.example.AutoEcole.dal.domain.enum_.InscriptionStatut;
import com.example.AutoEcole.dal.domain.enum_.StageType;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;

public record CreateInscriptionRequestBody(
        Long id,
        Long userId,
        Long stageId,
        StageType stageType,
        LocalDate dateOfInscription,
        InscriptionStatut inscriptionStatut
        //CodePromo codePromo
) {}


