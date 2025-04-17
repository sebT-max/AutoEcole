package com.example.AutoEcole.api.model.Document;

import com.example.AutoEcole.dal.domain.entity.Document;
import com.example.AutoEcole.dal.domain.enum_.DocumentType;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {

    // Conversion d'un Document vers un DocumentDTO
    public DocumentDTO toDto(Document doc) {
        if (doc == null) return null;

        return new DocumentDTO(
                doc.getId(),
                doc.getFileName(),
                doc.getType(),
                doc.getFileUrl(), // Utilisation de l'URL Cloudinary directement
                doc.getUser() != null ? doc.getUser().getId() : null,
                doc.getInscription() != null ? doc.getInscription().getId() : null,
                doc.getUploadedAt()
        );
    }

    // Conversion d'un DocumentDTO vers un Document (utilisation d'un constructeur plutôt que des setters)
    public Document toEntity(DocumentDTO dto) {
        Document document = new Document(
                dto.fileName(), // Directement depuis le DTO
                dto.type(),
                dto.fileUrl(), // Pas besoin de découper l’URL Cloudinary
                null, // User et Inscription peuvent être null à ce stade, selon tes besoins
                null,
                dto.uploadedAt()
        );
        document.setId(dto.id()); // Si tu veux conserver l'ID dans l'entité.
        return document;
    }
}