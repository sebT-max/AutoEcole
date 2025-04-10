package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.api.model.Particulier.ParticulierRegisterRequestBody;
import com.example.AutoEcole.bll.service.FileService;
import com.example.AutoEcole.bll.service.ParticulierService;
import com.example.AutoEcole.dal.domain.entity.Document;
import com.example.AutoEcole.dal.domain.entity.Role;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.dal.domain.enum_.DocumentType;
import com.example.AutoEcole.dal.repository.DocumentRepository;
import com.example.AutoEcole.dal.repository.RoleRepository;
import com.example.AutoEcole.dal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ParticulierServiceImpl implements ParticulierService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;
    private final DocumentRepository documentRepository;

    @Override
    public Long register(ParticulierRegisterRequestBody requestBody, List<MultipartFile> files) {
        // Vérifier que l'utilisateur accepte les conditions d'utilisation
        if (!requestBody.acceptTerms()) {
            throw new IllegalArgumentException("You must accept the terms and conditions to register.");
        }

        // Vérifier si l'email est déjà utilisé
        if (userRepository.findByEmail(requestBody.email()).isPresent()) {
            throw new IllegalArgumentException("Email already in use.");
        }

        // Récupérer le rôle depuis la base
        Role role = roleRepository.findById(requestBody.roleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Créer l’entité utilisateur
        User newUser = requestBody.toEntity();
        newUser.setRole(role);

        // Hasher le mot de passe (si tu utilises Spring Security)
        newUser.setPassword(passwordEncoder.encode(requestBody.password()));

        // Sauvegarder l'utilisateur
        User savedUser = userRepository.save(newUser);

        // ✅ Gérer les fichiers uploadés
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                try {
                    String fileName = fileService.saveFile(file);

                    // Déterminer le type de document (exemple simple avec nom de fichier)
                    DocumentType documentType = determineDocumentType(file);

                    Document doc = Document.builder()
                            .fileName(fileName)
                            .filePath("uploads/" + fileName)
                            .uploadedAt(LocalDateTime.now())
                            .user(savedUser)
                            .type(documentType)
                            .build();

                    documentRepository.save(doc);
                } catch (IOException e) {
                    throw new RuntimeException("Erreur lors de l'enregistrement du fichier : " + file.getOriginalFilename(), e);
                }
            }
        }

        // Retourner l'ID de l'utilisateur créé
        return savedUser.getId();
    }

    private DocumentType determineDocumentType(MultipartFile file) {
        // Exemple de logique pour déterminer le type de document
        String fileName = Objects.requireNonNull(file.getOriginalFilename()).toLowerCase();

        if (fileName.contains("recto")) {
            return DocumentType.PERMIS_RECTO;
        } else if (fileName.contains("verso")) {
            return DocumentType.PERMIS_VERSO;
        } else {
            return DocumentType.PIECE_IDENTITE;
        }
    }
}

