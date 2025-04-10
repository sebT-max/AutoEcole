package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.bll.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class FileController {
    private final FileService fileService;
    private static final String UPLOAD_DIR = "uploads/";

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        files.forEach(file -> {
            try {
                // Traitez chaque fichier
                // Par exemple, enregistrez le fichier ou effectuez des validations
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return ResponseEntity.ok("Fichiers téléchargés avec succès");
    }
    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) throws IOException {
        Resource file = fileService.getFileAsResource(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}