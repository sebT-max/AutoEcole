package com.example.AutoEcole.api.model.Document;

import java.time.LocalDateTime;

public record DocumentDTO(
        Long id,
        String fileName,
        String type, // ou DocumentType si tu préfères exposer l'enum tel quel
        String url,
        Long userId,
        Long inscriptionId,
        LocalDateTime uploadedAt
) {}
