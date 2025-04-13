package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.api.model.PrivateLink.PrivateLinkResponse;
import com.example.AutoEcole.dal.domain.entity.PrivateLink;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PrivateLinkService {
    public String createPrivateLink(Long stageId, Long entrepriseId);

    PrivateLink getValidLink(String token);

    // Autres méthodes...
    List<PrivateLinkResponse> getAllLinks();
}
