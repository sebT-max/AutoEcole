package com.example.AutoEcole.api.model.Document;

import com.example.AutoEcole.dal.domain.enum_.DocumentType;

import java.time.LocalDateTime;

public record DocumentDTO(
        Long id,
        String fileName,
        DocumentType type, // ou DocumentType si tu préfères exposer l'enum tel quel
        String fileUrl,
        Long userId,
        Long inscriptionId,
        LocalDateTime uploadedAt
) {}
