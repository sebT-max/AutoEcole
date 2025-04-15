package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.dal.domain.entity.Document;
import com.example.AutoEcole.dal.domain.entity.Inscription;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.dal.domain.enum_.DocumentType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface DocumentService {
    Document uploadDocument(MultipartFile file, DocumentType type, User user, Inscription inscription) throws IOException;

    Document replaceDocument(Long documentId, MultipartFile file) throws IOException;

    List<Document> getByUserId(Long userId);

    List<Document> getByInscriptionId(Long id);
}
