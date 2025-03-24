package com.example.AutoEcole.api.model.Inscription;

import com.example.AutoEcole.dal.domain.entity.*;
import com.example.AutoEcole.dal.domain.enum_.InscriptionStatut;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;

public record CreateInscriptionRequestBody(
        Long userId,
        Long stageId,
        LocalDate dateOfInscription,
        Integer nbrPerson,
        Long codePromoId
) {}


