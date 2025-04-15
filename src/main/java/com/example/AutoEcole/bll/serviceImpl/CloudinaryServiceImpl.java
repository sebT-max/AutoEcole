package com.example.AutoEcole.bll.serviceImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.AutoEcole.bll.service.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    // Constructeur pour injecter Cloudinary
    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public Map<String, String> upload(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Aucun fichier fourni");
        }

        // Upload du fichier vers Cloudinary
        Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        // Extraction de l'URL sécurisée et du public_id
        return Map.of(
                "url", result.get("secure_url").toString(),
                "public_id", result.get("public_id").toString()
        );
    }

    @Override
    public void delete(String publicId) throws IOException {
        if (publicId == null || publicId.isEmpty()) {
            throw new IllegalArgumentException("Le public_id est requis pour la suppression");
        }

        // Suppression de l'image dans Cloudinary
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}


//@Service
//public class CloudinaryServiceImpl implements CloudinaryService {
//    private final Cloudinary cloudinary;
//
//    public CloudinaryServiceImpl(Cloudinary cloudinary) {
//        this.cloudinary = cloudinary;
//    }
//
//    @Override
//    public String uploadFile(MultipartFile file) throws IOException {
//        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
//        return uploadResult.get("secure_url").toString();
//    }
//}
//    private static final String UPLOAD_DIR = "uploads/";
//    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 Mo
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
//        // Vérification de la taille du fichier
//        if (file.getSize() > MAX_FILE_SIZE) {
//            throw new IOException("Le fichier est trop volumineux. Taille maximale : 10 Mo");
//        }
//
//        // Créer le répertoire si nécessaire
//        Path uploadPath = Paths.get(UPLOAD_DIR);
//        Files.createDirectories(uploadPath);
//
//        // Normalisation du nom de fichier pour éviter des caractères spéciaux
//        String normalizedFileName = originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_");
//        String fileName = UUID.randomUUID() + "_" + normalizedFileName;
//        Path filePath = uploadPath.resolve(fileName);
//
//        // Sauvegarder le fichier
//        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//        return fileName;
//    }
//
//    @Override
//    public Resource getFileAsResource(String fileName) throws FileNotFoundException {
//        try {
//            Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();
//            if (!Files.exists(filePath)) {
//                throw new FileNotFoundException("Fichier non trouvé : " + fileName);
//            }
//            return new UrlResource(filePath.toUri());
//        } catch (IOException e) {
//            // Gérer l'exception de manière plus spécifique
//            throw new FileNotFoundException("Erreur lors de l'accès au fichier : " + fileName);
//        }
//    }


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
