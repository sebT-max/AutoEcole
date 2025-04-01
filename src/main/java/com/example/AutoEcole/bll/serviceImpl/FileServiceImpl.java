package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.bll.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private static final String UPLOAD_DIR = "uploads/";

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null; // Aucun fichier fourni
        }

        // Vérifier l'extension du fichier
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && !originalFilename.toLowerCase().endsWith(".pdf")) {
            throw new IOException("Seuls les fichiers PDF sont acceptés");
        }

        // Créer le répertoire si nécessaire
        Files.createDirectories(Paths.get(UPLOAD_DIR));

        // Générer un nom unique pour le fichier
        String fileName = UUID.randomUUID() + "_" + originalFilename;
        Path filePath = Paths.get(UPLOAD_DIR, fileName);

        // Sauvegarder le fichier
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return fileName; // Retourner le nom du fichier
    }


    @Override
    public Resource getFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();
            if (!Files.exists(filePath)) {
                throw new FileNotFoundException("Fichier non trouvé : " + fileName);
            }
            return new UrlResource(filePath.toUri());
        } catch (IOException e) {
            // Gérer l'exception ou la transformer en runtime exception
            throw new RuntimeException("Erreur lors de l'accès au fichier : " + fileName, e);
        }
    }
}
