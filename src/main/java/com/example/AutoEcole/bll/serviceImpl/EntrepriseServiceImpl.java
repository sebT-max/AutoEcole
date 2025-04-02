package com.example.AutoEcole.bll.serviceImpl;
import com.example.AutoEcole.Exception.EntrepriseNotFound.EntrepriseNotFoundException;
import com.example.AutoEcole.api.model.Entreprise.EntrepriseRegisterRequestBody;
import com.example.AutoEcole.bll.service.EntrepriseService;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.Role;
import com.example.AutoEcole.dal.repository.EntrepriseRepository;
import com.example.AutoEcole.dal.repository.RoleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntrepriseServiceImpl implements EntrepriseService {
    private final EntrepriseRepository entrepriseRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Entreprise login(String email, String password) {
        Entreprise entreprise = entrepriseRepository.findByIdWithRole(2L)
                .orElseThrow(() -> new EntrepriseNotFoundException("Entreprise non trouvée"));
        System.out.println("Role name: " + entreprise.getRole().getName());
        if(!passwordEncoder.matches(password, entreprise.getPassword())){
            throw new BadCredentialsException("Mot de passe incorrect");
        }
        return entreprise;
    }

    @Override
    public Long register(@Valid EntrepriseRegisterRequestBody requestBody) {
        // Vérifier que l'utilisateur accepte les conditions d'utilisation


        if (!requestBody.acceptTerms()) {
            throw new IllegalArgumentException("You must accept the terms and conditions to register.");
        }
        // Vérifier si l'email est déjà utilisé
        if (entrepriseRepository.findByEmail(requestBody.email()).isPresent()) {
            throw new IllegalArgumentException("Email already in use.");
        }

        // Récupérer le rôle_id depuis la base

        Role role = roleRepository.findById(requestBody.roleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Créer l’entité utilisateur
        Entreprise newEntreprise = requestBody.toEntity();

        // Hasher le mot de passe (si tu utilises Spring Security)
        newEntreprise.setPassword(passwordEncoder.encode(requestBody.password()));

        // Sauvegarder l'utilisateur
        Entreprise savedEntreprise = entrepriseRepository.save(newEntreprise);

        // Retourner l'ID de l'utilisateur créé
        return savedEntreprise.getId();
    }
}
