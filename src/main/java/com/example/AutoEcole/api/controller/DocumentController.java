package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.bll.service.DocumentService;
import com.example.AutoEcole.bll.service.InscriptionService;
import com.example.AutoEcole.bll.service.UserService;
import com.example.AutoEcole.dal.domain.entity.Document;
import com.example.AutoEcole.dal.domain.entity.Inscription;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.dal.domain.enum_.DocumentType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/V1/documents")
@CrossOrigin(origins = "*")
public class DocumentController {

    private final DocumentService documentService;
    private final UserService userService;
    private final InscriptionService inscriptionService;

    public DocumentController(DocumentService documentService, UserService userService, InscriptionService inscriptionService) {
        this.documentService = documentService;
        this.userService = userService;
        this.inscriptionService = inscriptionService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Document> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") DocumentType type,
            @RequestParam("userId") Long userId,
            @RequestParam("inscriptionId") Long inscriptionId
    ) throws IOException {
        User user = userService.findById(userId);
        Inscription inscription = inscriptionService.getInscriptionById(inscriptionId);

        Document document = documentService.uploadDocument(file, type, user, inscription);
        return ResponseEntity.ok(document);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Document> replaceDocument(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        Document updated = documentService.replaceDocument(id, file);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/me")
    public ResponseEntity<List<Document>> getByUser(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.getUserIdByUsername(userDetails.getUsername());
        List<Document> documents = documentService.getByUserId(userId);

        return ResponseEntity.ok(documents);
    }

    @GetMapping("/by-inscription/{inscriptionId}")
    public ResponseEntity<List<Document>> getByInscription(@PathVariable Long inscriptionId) {
        return ResponseEntity.ok(documentService.getByInscriptionId(inscriptionId));
    }
}

