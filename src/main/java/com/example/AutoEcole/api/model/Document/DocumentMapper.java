package com.example.AutoEcole.api.model.Document;

import com.example.AutoEcole.dal.domain.entity.Document;
import com.example.AutoEcole.dal.domain.enum_.DocumentType;
import org.springframework.stereotype.Component;

@Component // ou @Service si tu veux l'injecter ailleurs
public class DocumentMapper {

    public DocumentDTO toDto(Document doc) {
        if (doc == null) return null;

        return new DocumentDTO(
                doc.getId(),
                doc.getFileName(),
                doc.getType().name(),
                "/api/files/" + doc.getFilePath(),
                doc.getUser() != null ? doc.getUser().getId() : null,
                doc.getInscription() != null ? doc.getInscription().getId() : null,
                doc.getUploadedAt()
        );
    }

    // Optionnel : de DTO vers Entity
    public Document toEntity(DocumentDTO dto) {
        Document document = new Document();
        document.setId(dto.id());
        document.setFileName(dto.fileName());
        document.setType(DocumentType.valueOf(dto.type()));
        document.setFilePath(dto.url().replace("/api/files/", ""));
        // Pas de user ni d’inscription ici directement, à gérer ailleurs
        document.setUploadedAt(dto.uploadedAt());
        return document;
    }
}

