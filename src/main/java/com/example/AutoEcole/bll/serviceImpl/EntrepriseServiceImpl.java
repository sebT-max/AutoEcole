package com.example.AutoEcole.bll.serviceImpl;
import com.example.AutoEcole.Exception.EmailAlreadyInUseException.EmailAlreadyInUseException;
import com.example.AutoEcole.Exception.RoleNotFoundException.RoleNotFoundException;
import com.example.AutoEcole.api.model.Entreprise.EntrepriseRegisterRequestBody;
import com.example.AutoEcole.bll.service.EntrepriseService;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.Role;
import com.example.AutoEcole.dal.repository.EntrepriseRepository;
import com.example.AutoEcole.dal.repository.RoleRepository;
import com.example.AutoEcole.il.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntrepriseServiceImpl implements EntrepriseService {
    private final EntrepriseRepository entrepriseRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public Entreprise register(@Valid EntrepriseRegisterRequestBody requestBody) {
        if (!requestBody.acceptTerms()) {
            throw new IllegalArgumentException("Vous devez acceptez les termes et conditions");
        }

        if (entrepriseRepository.findByEmail(requestBody.email()).isPresent()) {
            throw new EmailAlreadyInUseException("Email déjà utilisé");
        }

        Role role = roleRepository.findById(requestBody.roleId())
                .orElseThrow(() -> new RoleNotFoundException("Rôle non trouvé"));

        Entreprise newEntreprise = requestBody.toEntity(role);
        newEntreprise.setPassword(passwordEncoder.encode(requestBody.password()));

        Entreprise savedEntreprise = entrepriseRepository.save(newEntreprise);
        String token = jwtUtil.generateToken(savedEntreprise);

        // 🔁 On retourne l'objet entier, pas juste l'ID
        return savedEntreprise;
    }
}
