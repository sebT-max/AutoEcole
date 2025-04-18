package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.api.model.PrivateLink.PrivateLinkResponse;
import com.example.AutoEcole.api.model.PrivateLink.PrivateLinkValidationResponse;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.PrivateLink;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PrivateLinkService {
    public PrivateLinkResponse createPrivateLink(Long stageId, Long entrepriseId);

    PrivateLink getValidLink(String token);

    PrivateLinkValidationResponse validateAndGetInfo(String token);

    // Autres méthodes...
    List<PrivateLinkResponse> getAllLinks();

    List<PrivateLinkResponse> getPrivateLinksForEntreprise(Entreprise entreprise);

    void incrementUsage(PrivateLink privateLink);
}
