package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.api.model.Particulier.ParticulierRegisterRequestBody;
import com.example.AutoEcole.bll.service.CloudinaryService;
import com.example.AutoEcole.bll.service.ParticulierService;
import com.example.AutoEcole.dal.domain.entity.Particulier;
import com.example.AutoEcole.dal.domain.entity.Role;
import com.example.AutoEcole.dal.domain.enum_.DocumentType;
import com.example.AutoEcole.dal.repository.DocumentRepository;
import com.example.AutoEcole.dal.repository.RoleRepository;
import com.example.AutoEcole.dal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ParticulierServiceImpl implements ParticulierService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService fileService;
    private final DocumentRepository documentRepository;


@Override
public Particulier register(ParticulierRegisterRequestBody requestBody) {
    if (!requestBody.acceptTerms()) {
        throw new IllegalArgumentException("You must accept the terms and conditions to register.");
    }

    if (userRepository.findByEmail(requestBody.email()).isPresent()) {
        throw new IllegalArgumentException("Email already in use.");
    }

    Role role = roleRepository.findById(requestBody.roleId())
            .orElseThrow(() -> new RuntimeException("Role not found"));

    Particulier newUser = requestBody.toEntity(); // ⚠️ ici toEntity doit retourner un Particulier
    newUser.setRole(role);
    newUser.setPassword(passwordEncoder.encode(requestBody.password()));

    Particulier savedUser = (Particulier) userRepository.save(newUser); // ou direct : `particulierRepository.save()`


    return savedUser;
}

    private static final Map<String, DocumentType> KEYWORD_TO_TYPE = Map.ofEntries(
            Map.entry("permis", DocumentType.PERMIS),
            Map.entry("identite", DocumentType.PIECE_IDENTITE),
            Map.entry("pdf", DocumentType.LETTRE_48N)
    );

    private DocumentType determineDocumentType(MultipartFile file) {
        String fileName = Objects.requireNonNull(file.getOriginalFilename()).toLowerCase();

        return KEYWORD_TO_TYPE.entrySet().stream()
                .filter(entry -> fileName.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(DocumentType.PIECE_IDENTITE); // Valeur par défaut
    }


    public boolean isAdmin(Particulier user) {
        return user.getRole().getName().equals("ADMIN");
    }

}

