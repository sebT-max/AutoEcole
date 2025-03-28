package com.example.AutoEcole.api.model.Inscription;

import com.example.AutoEcole.dal.domain.entity.Inscription;
import com.example.AutoEcole.dal.domain.entity.Stage;
import com.example.AutoEcole.dal.domain.enum_.InscriptionStatut;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;

public record CreateInscriptionResponseBody(
        String message,
        Long stageId,
        LocalDate dateOfInscription,
        Long codePromoId

) {}
