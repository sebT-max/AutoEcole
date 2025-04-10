package com.example.AutoEcole.api.model.Inscription;

import com.example.AutoEcole.api.model.Document.DocumentDTO;
import com.example.AutoEcole.dal.domain.entity.*;
import com.example.AutoEcole.dal.domain.enum_.InscriptionStatut;
import com.example.AutoEcole.dal.domain.enum_.StageType;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public record CreateInscriptionRequestBody(
        @Nullable
        Long userId,
        @Nullable
        Long stageId,
        @Nullable
        StageType stageType,
        InscriptionStatut inscriptionStatut,
        List<DocumentDTO> documents
) {}


