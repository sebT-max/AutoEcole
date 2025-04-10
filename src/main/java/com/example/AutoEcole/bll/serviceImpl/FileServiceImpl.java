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
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 Mo

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Aucun fichier fourni");
        }

        // Vérifier l'extension du fichier
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && !originalFilename.toLowerCase().endsWith(".pdf")) {
            throw new IOException("Seuls les fichiers PDF sont acceptés");
        }

        // Vérification de la taille du fichier
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IOException("Le fichier est trop volumineux. Taille maximale : 10 Mo");
        }

        // Créer le répertoire si nécessaire
        Path uploadPath = Paths.get(UPLOAD_DIR);
        Files.createDirectories(uploadPath);

        // Normalisation du nom de fichier pour éviter des caractères spéciaux
        String normalizedFileName = originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_");
        String fileName = UUID.randomUUID() + "_" + normalizedFileName;
        Path filePath = uploadPath.resolve(fileName);

        // Sauvegarder le fichier
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    @Override
    public Resource getFileAsResource(String fileName) throws FileNotFoundException {
        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();
            if (!Files.exists(filePath)) {
                throw new FileNotFoundException("Fichier non trouvé : " + fileName);
            }
            return new UrlResource(filePath.toUri());
        } catch (IOException e) {
            // Gérer l'exception de manière plus spécifique
            throw new FileNotFoundException("Erreur lors de l'accès au fichier : " + fileName);
        }
    }
}

//@Service
//public class FileServiceImpl implements FileService {
//    private static final String UPLOAD_DIR = "uploads/";
//
//    @Override
//    public String saveFile(MultipartFile file) throws IOException {
//        if (file == null || file.isEmpty()) {
//            throw new IllegalArgumentException("Aucun fichier fourni");
//        }
//
//        // Vérifier l'extension du fichier
//        String originalFilename = file.getOriginalFilename();
//        if (originalFilename != null && !originalFilename.toLowerCase().endsWith(".pdf")) {
//            throw new IOException("Seuls les fichiers PDF sont acceptés");
//        }
//
//        // Créer le répertoire si nécessaire
//        Path uploadPath = Paths.get(UPLOAD_DIR);
//        Files.createDirectories(uploadPath);
//
//        // Générer un nom unique pour le fichier
//        String fileName = UUID.randomUUID() + "_" + originalFilename;
//        Path filePath = uploadPath.resolve(fileName);
//
//        // Sauvegarder le fichier
//        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//        return fileName; // Retourner le nom du fichier
//    }
//
//    @Override
//    public Resource getFileAsResource(String fileName) {
//        try {
//            Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();
//            if (!Files.exists(filePath)) {
//                throw new FileNotFoundException("Fichier non trouvé : " + fileName);
//            }
//            return new UrlResource(filePath.toUri());
//        } catch (IOException e) {
//            // Gérer l'exception ou la transformer en runtime exception
//            throw new RuntimeException("Erreur lors de l'accès au fichier : " + fileName, e);
//        }
//    }
//    @Service
//    public class FileServiceImpl implements FileService {
//        private static final String UPLOAD_DIR = "uploads/";
//        private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 Mo
//        @Override
//        public String saveFile(MultipartFile file) throws IOException {
//            if (file == null || file.isEmpty()) {
//                throw new IllegalArgumentException("Aucun fichier fourni");
//            }
//
//            // Vérifier l'extension du fichier
//            String originalFilename = file.getOriginalFilename();
//            if (originalFilename != null && !originalFilename.toLowerCase().endsWith(".pdf")) {
//                throw new IOException("Seuls les fichiers PDF sont acceptés");
//            }
//
//            // Vérification de la taille du fichier
//            if (file.getSize() > MAX_FILE_SIZE) {
//                throw new IOException("Le fichier est trop volumineux. Taille maximale : 10 Mo");
//            }
//
//            // Créer le répertoire si nécessaire
//            Path uploadPath = Paths.get(UPLOAD_DIR);
//            Files.createDirectories(uploadPath);
//
//            // Normalisation du nom de fichier pour éviter des caractères spéciaux
//            String normalizedFileName = originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_");
//            String fileName = UUID.randomUUID() + "_" + normalizedFileName;
//            Path filePath = uploadPath.resolve(fileName);
//
//            // Sauvegarder le fichier
//            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//            return fileName;
//        }
//
//        @Override
//        public Resource getFileAsResource(String fileName) {
//            try {
//                Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();
//                if (!Files.exists(filePath)) {
//                    throw new FileNotFoundException("Fichier non trouvé : " + fileName);
//                }
//                return new UrlResource(filePath.toUri());
//            } catch (IOException e) {
//                // Gérer l'exception de manière plus spécifique
//                throw new FileNotFoundException("Erreur lors de l'accès au fichier : " + fileName);
//            }
//        }
//        @Override
//        public String saveFile(MultipartFile file) throws IOException {
//            if (file == null || file.isEmpty()) {
//                throw new IllegalArgumentException("Aucun fichier fourni");
//            }
//
//            // Vérifier l'extension du fichier
//            String originalFilename = file.getOriginalFilename();
//            if (originalFilename != null && !originalFilename.toLowerCase().endsWith(".pdf")) {
//                throw new IOException("Seuls les fichiers PDF sont acceptés");
//            }
//
//            // Vérification de la taille du fichier
//            if (file.getSize() > MAX_FILE_SIZE) {
//                throw new IOException("Le fichier est trop volumineux. Taille maximale : 10 Mo");
//            }
//
//            // Créer le répertoire si nécessaire
//            Path uploadPath = Paths.get(UPLOAD_DIR);
//            Files.createDirectories(uploadPath);
//
//            // Normalisation du nom de fichier pour éviter des caractères spéciaux
//            String normalizedFileName = originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_");
//            String fileName = UUID.randomUUID() + "_" + normalizedFileName;
//            Path filePath = uploadPath.resolve(fileName);
//
//            // Sauvegarder le fichier
//            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//            return fileName;
//        }
//
//        @Override
//        public Resource getFileAsResource(String fileName) {
//            try {
//                Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();
//                if (!Files.exists(filePath)) {
//                    throw new FileNotFoundException("Fichier non trouvé : " + fileName);
//                }
//                return new UrlResource(filePath.toUri());
//            } catch (IOException e) {
//                // Gérer l'exception de manière plus spécifique
//                throw new FileNotFoundException("Erreur lors de l'accès au fichier : " + fileName);
//            }
//        }
//    }
//
//}
