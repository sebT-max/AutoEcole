package com.example.AutoEcole.api.model.Inscription;

import com.example.AutoEcole.api.model.Document.DocumentMapper;
import com.example.AutoEcole.api.model.Document.DocumentDTO;
import com.example.AutoEcole.dal.domain.entity.Inscription;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InscriptionMapper {

    private final DocumentMapper documentMapper;

    public InscriptionMapper(DocumentMapper documentMapper) {
        this.documentMapper = documentMapper;
    }

    public CreateInscriptionRequestBody toDto(Inscription i) {
        List<DocumentDTO> documentDTOs = i.getDocuments() != null
                ? i.getDocuments().stream().map(documentMapper::toDto).toList()
                : List.of();

        return new CreateInscriptionRequestBody(
                i.getUser().getId(),
                i.getStage().getId(),
                i.getStageType(),
                i.getInscriptionStatut(),
                documentDTOs
        );
    }

    // Tu peux aussi ajouter toEntity si besoin
}
