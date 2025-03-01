package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.api.model.Document.create.CreateDocumentResponseBody;
import com.example.AutoEcole.dal.domain.entity.Document;

import java.util.List;

public interface DocumentService {
    CreateDocumentResponseBody createDocument(CreateDocumentRequestBody request);
    public List<Document> getAllDocuments();
    public Document getDocumentById(Long id);

    boolean update(Long id, Document planet);

    boolean delete(Long id);
}
