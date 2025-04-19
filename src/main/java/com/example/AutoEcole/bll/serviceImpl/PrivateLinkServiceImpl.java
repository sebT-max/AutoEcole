package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.api.model.PrivateLink.PrivateLinkResponse;
import com.example.AutoEcole.api.model.PrivateLink.PrivateLinkValidationResponse;
import com.example.AutoEcole.api.model.Stage.StageInfoResponse;
import com.example.AutoEcole.bll.service.PrivateLinkService;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.PrivateLink;
import com.example.AutoEcole.dal.domain.entity.PrivateLinkUsageLog;
import com.example.AutoEcole.dal.domain.entity.Stage;
import com.example.AutoEcole.dal.repository.EntrepriseRepository;
import com.example.AutoEcole.dal.repository.PrivateLinkRepository;
import com.example.AutoEcole.dal.repository.PrivateLinkUsageLogRepository;
import com.example.AutoEcole.dal.repository.StageRepository;
import com.example.AutoEcole.il.config.LinkExpirationConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PrivateLinkServiceImpl implements PrivateLinkService {
    private final PrivateLinkRepository privateLinkRepository ;
    private final StageRepository stageRepository ;
    private final EntrepriseRepository entrepriseRepository ;
    private final PrivateLinkUsageLogRepository privateLinkUsageLogRepository;
    private final LinkExpirationConfig linkExpirationConfig;

    public PrivateLinkServiceImpl(PrivateLinkRepository privateLinkRepository, StageRepository stageRepository, EntrepriseRepository entrepriseRepository, PrivateLinkUsageLogRepository privateLinkUsageLogRepository, LinkExpirationConfig linkExpirationConfig) {
        this.privateLinkRepository = privateLinkRepository;
        this.stageRepository = stageRepository;
        this.entrepriseRepository = entrepriseRepository;
        this.privateLinkUsageLogRepository = privateLinkUsageLogRepository;
        this.linkExpirationConfig = linkExpirationConfig;
    }


    @Override
    public PrivateLinkResponse createPrivateLink(Long stageId, Long entrepriseId) {
        // Recherche des entités Stage et Entreprise
        Stage stage = stageRepository.findById(stageId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stage non trouvé"));
        Entreprise entreprise = entrepriseRepository.findById(entrepriseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entreprise non trouvée"));

//        int nombreStagiaires = facture.getNumberOfInterns(); // Ce champ est dans la facture

        String token = UUID.randomUUID().toString();

        PrivateLink link = new PrivateLink();
        link.setStage(stage);
        link.setEntreprise(entreprise);
        link.setToken(token);
        link.setActive(true);
        link.setUsageCount(0);
        link.setMaxUsages(10);
        link.setExpirationDate(LocalDateTime.now().plusDays(linkExpirationConfig.getDays())); // Expiration à partir de la config
        privateLinkRepository.save(link);

        StageInfoResponse stageInfo = StageInfoResponse.fromEntity(stage); // Création du DTO StageInfoResponse

        return new PrivateLinkResponse(
                token,
                link.getExpirationDate(),
                entreprise.getId(),
                entreprise.getName(),
                stageInfo

        );
    }
    @Override
    public PrivateLink getValidLink(String token) {
        PrivateLink link = privateLinkRepository.findByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lien invalide"));

        if (link.getExpirationDate() != null && link.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.GONE, "Lien expiré");
        }
//        if (!link.isActive()) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Lien désactivé");
//        }
//        if (link.getUsageCount() >= link.getMaxUsages()) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Lien déjà utilisé au maximum");
//        }

        return link;
    }

    @Override
    public PrivateLinkValidationResponse validateAndGetInfo(String token) {
        PrivateLink link = privateLinkRepository.findByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lien invalide"));

        // Logique de validation du lien
        if (link.getExpirationDate() != null && link.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.GONE, "Lien expiré");
        }
        if (!link.isActive()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Lien désactivé");
        }
        if (link.getUsageCount() >= link.getMaxUsages()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Lien déjà utilisé au maximum");
        }

        // Vérification de la présence de l'entreprise et du stage
        if (link.getEntreprise() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entreprise associée au lien introuvable");
        }
        if (link.getStage() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stage associé au lien introuvable");
        }

        // Journalisation sans utilisateur authentifié
        PrivateLinkUsageLog usageLog = new PrivateLinkUsageLog();
        usageLog.setEmail("non-authentifié"); // Utilisation sans authentification
        usageLog.setUsedAt(LocalDateTime.now());
        usageLog.setLink(link);
        privateLinkUsageLogRepository.save(usageLog);

        // Incrémentation du nombre d'utilisations
        link.setUsageCount(link.getUsageCount() + 1);
        privateLinkRepository.save(link);

        // Retour de la réponse avec toutes les informations
        return PrivateLinkValidationResponse.fromEntities(
                link.getEntreprise().getName(),
                link.getStage().getStreet(),
                link.getStage(),
                link.getExpirationDate(),
                link.isActive(),
                link.getMaxUsages() - link.getUsageCount()
        );
    }



    // Autres méthodes...
    @Override
    public List<PrivateLinkResponse> getAllLinks() {
        return privateLinkRepository.findAll().stream()
                .map(link -> new PrivateLinkResponse(
                        link.getToken(),
                        link.getExpirationDate(),
                        link.getEntreprise().getId(),
                        link.getEntreprise().getName(),
                        StageInfoResponse.fromEntity(link.getStage()) // Utilisation de StageInfoResponse
                ))
                .collect(Collectors.toList());
    }
    @Override
    public List<PrivateLinkResponse> getPrivateLinksForEntreprise(Entreprise entreprise) {
        List<PrivateLink> links = privateLinkRepository.findByCompanyId(entreprise.getId());

        return links.stream()
                .map(link -> new PrivateLinkResponse(
                        link.getToken(),
                        link.getExpirationDate(),
                        entreprise.getId(),
                        entreprise.getName(),
                        StageInfoResponse.fromEntity(link.getStage()) // Utilisation de StageInfoResponse
                ))
                .collect(Collectors.toList());
    }
    @Override
    public void incrementUsage(PrivateLink privateLink) {
        int currentUsage = privateLink.getUsageCount();
        privateLink.setUsageCount(currentUsage + 1);
        privateLinkRepository.save(privateLink);
    }


}
