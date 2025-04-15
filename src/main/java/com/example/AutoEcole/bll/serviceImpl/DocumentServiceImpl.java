package com.example.AutoEcole.bll.serviceImpl;


import com.example.AutoEcole.bll.service.CloudinaryService;
import com.example.AutoEcole.bll.service.DocumentService;
import com.example.AutoEcole.dal.domain.entity.Document;
import com.example.AutoEcole.dal.domain.entity.Inscription;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.dal.domain.enum_.DocumentType;
import com.example.AutoEcole.dal.repository.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepo;
    private final CloudinaryService cloudinaryService;
    private static final Logger log = LoggerFactory.getLogger(DocumentServiceImpl.class);


    public DocumentServiceImpl(DocumentRepository documentRepo, CloudinaryService cloudinaryService) {
        this.documentRepo = documentRepo;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public Document uploadDocument(MultipartFile file, DocumentType type, User user, Inscription inscription) throws IOException {
        Map<String, String> cloudinaryResult = cloudinaryService.upload(file);
        String url = cloudinaryResult.get("url");
        System.out.println("Uploaded file URL: " + url);

        Document document = Document.builder()
                .fileName(file.getOriginalFilename())
                .type(type)
                .fileUrl(url)  // update ici
                .user(user)
                .inscription(inscription)
                .uploadedAt(LocalDateTime.now())
                .build();

        return documentRepo.save(document);
    }

    @Override
    public Document replaceDocument(Long documentId, MultipartFile file) throws IOException {
        Document existing = documentRepo.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document non trouvé"));

        // Supprimer l'ancien sur Cloudinary si nécessaire (si tu as le publicId)
        // cloudinaryService.delete(existing.getPublicId());

        Map<String, String> newUpload = cloudinaryService.upload(file);
        existing.setFileUrl(newUpload.get("url"));
        existing.setFileName(file.getOriginalFilename());
        existing.setUploadedAt(LocalDateTime.now());

        return documentRepo.save(existing);
    }

    @Override
    public List<Document> getByUserId(Long userId) {
        List<Document> documents = documentRepo.findAllByUserId(userId);
        if (documents.isEmpty()) {
            throw new RuntimeException("Aucune réservation trouvée pour l'utilisateur avec l'ID : " + userId);
        }
        return documents;
    }

    @Override
    public List<Document> getByInscriptionId(Long id) {
        return documentRepo.findAllByInscriptionId(id);
    }
}

