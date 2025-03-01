package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.Document.create.CreateDocumentResponseBody;
import com.example.AutoEcole.bll.service.DocumentService;
import com.example.AutoEcole.dal.domain.entity.Document;
import com.example.AutoEcole.api.model.Activity.create.CreateDocumentRequestBody;
import com.example.AutoEcole.bll.service.DocumentService;
import com.example.AutoEcole.dal.domain.entity.Document;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/V1/activity")
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<CreateDocumentResponseBody> createActivity(@RequestBody CreateDocumentRequestBody request){
        com.example.AutoEcole.api.model.Document.create.CreateDocumentResponseBody response = documentService.createDocument(request);
        return ResponseEntity.ok(response);    
    }
    @GetMapping("/all")
    public ResponseEntity<List<Document> getAllDocuments() {
        List<Document> activities = documentService.getAllDocuments();
        return ResponseEntity.ok(activities);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OPERATOR')")
    public Document getDpocumentById(@PathVariable Long id){
        return documentService.getDocumentById(id);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('OPERATOR')")
    public boolean deleteActivity(@PathVariable Long id){
        return documentService.delete(id);
    }
}
