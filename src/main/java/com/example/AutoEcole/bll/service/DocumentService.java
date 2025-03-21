package com.example.AutoEcole.bll.service;

import java.util.List;

public interface DocumentService {
    Long createDocument(Document document);
    public List<Document> getAllDocuments();
    public Document getDocumentById(Long id);

    boolean update(Long id, Document planet);

    boolean delete(Long id);

}
