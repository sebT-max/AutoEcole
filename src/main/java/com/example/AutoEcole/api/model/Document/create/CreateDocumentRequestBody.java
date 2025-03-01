package com.example.AutoEcole.api.model.Document.create;

public record CreateDocumentRequestBody(
        String name,
        String description,
        Double price
) {
}
