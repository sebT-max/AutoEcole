package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.api.model.PrivateLink.PrivateLinkResponse;
import com.example.AutoEcole.bll.service.PrivateLinkService;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.PrivateLink;
import com.example.AutoEcole.dal.domain.entity.Stage;
import com.example.AutoEcole.dal.repository.EntrepriseRepository;
import com.example.AutoEcole.dal.repository.PrivateLinkRepository;
import com.example.AutoEcole.dal.repository.StageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivateLinkServiceImpl implements PrivateLinkService {
    private final PrivateLinkRepository privateLinkRepository ;
    private final StageRepository stageRepository ;
    private final EntrepriseRepository entrepriseRepository ;

    @Override
    public PrivateLinkResponse createPrivateLink(Long stageId, Long entrepriseId) {
        Stage stage = stageRepository.findById(stageId).orElseThrow();
        Entreprise entreprise = entrepriseRepository.findById(entrepriseId).orElseThrow();

        String token = UUID.randomUUID().toString();

        PrivateLink link = new PrivateLink();
        link.setStage(stage);
        link.setEntreprise(entreprise);
        link.setToken(token);
        link.setActive(false);
        link.setUsageCount(link.getUsageCount() + 1);
        link.setExpirationDate(LocalDateTime.now().plusDays(7)); // exemple : expire dans 7 jours
        privateLinkRepository.save(link);

        return new PrivateLinkResponse(
                token,
                link.getExpirationDate(),
                entreprise.getId(),
                entreprise.getName(),
                stage.getId()
        );
    }
    @Override
    public PrivateLink getValidLink(String token) {
        PrivateLink link = privateLinkRepository.findByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lien invalide"));

        if (link.getExpirationDate() != null && link.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.GONE, "Lien expiré");
        }
        if (!link.isActive()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Lien désactivé");
        }
        if (link.getUsageCount() >= link.getMaxUsages()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Lien déjà utilisé au maximum");
        }

        return link;
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
                        link.getStage().getId()
                ))
                .toList();
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
                        link.getStage().getId() // Assure-toi que getStage() n'est pas null
                ))
                .collect(Collectors.toList());
    }


}
