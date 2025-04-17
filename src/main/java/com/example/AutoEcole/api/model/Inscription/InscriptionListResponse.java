package com.example.AutoEcole.api.model.Inscription;

import com.example.AutoEcole.api.model.Document.DocumentDTO;
import com.example.AutoEcole.dal.domain.enum_.InscriptionStatut;
import com.example.AutoEcole.dal.domain.enum_.StageType;

import java.util.List;

public record InscriptionListResponse(
        Long id,
        Long userId,
        String userLastName,
        String userFirstName,
        Long stageId,
        String stageCity,
        String stageStreet,
        String userEmail,
        StageType stageType,
        InscriptionStatut inscriptionStatut,
        List<DocumentDTO>documents
){

}