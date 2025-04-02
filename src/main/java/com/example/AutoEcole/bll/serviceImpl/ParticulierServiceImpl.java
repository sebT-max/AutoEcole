package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.api.model.Particulier.ParticulierRegisterRequestBody;
import com.example.AutoEcole.bll.service.ParticulierService;
import com.example.AutoEcole.dal.domain.entity.Role;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.dal.repository.RoleRepository;
import com.example.AutoEcole.dal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticulierServiceImpl implements ParticulierService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long register(ParticulierRegisterRequestBody requestBody) {
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
        User newUser = requestBody.toEntity(role);

        // Hasher le mot de passe (si tu utilises Spring Security)
        newUser.setPassword(passwordEncoder.encode(requestBody.password()));

        // Sauvegarder l'utilisateur
        User savedUser = userRepository.save(newUser);

        // Retourner l'ID de l'utilisateur créé
        return savedUser.getId();
    }
}
