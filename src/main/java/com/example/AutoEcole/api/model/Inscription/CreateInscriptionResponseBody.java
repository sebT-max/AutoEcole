package com.example.AutoEcole.api.model.Inscription;

import com.example.AutoEcole.api.model.Document.DocumentDTO;
import com.example.AutoEcole.dal.domain.enum_.InscriptionStatut;
import com.example.AutoEcole.dal.domain.enum_.StageType;

import java.util.List;

public record CreateInscriptionResponseBody(
        Long id,
        Long userId,
        Long stageId,
        StageType stageType,
        InscriptionStatut inscriptionStatut,
        List<DocumentDTO>documents
        //CodePromo codePromo

) {}