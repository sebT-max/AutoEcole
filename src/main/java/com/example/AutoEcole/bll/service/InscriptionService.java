package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.api.model.Inscription.CreateInscriptionRequestBody;
import com.example.AutoEcole.api.model.Inscription.CreateInscriptionResponseBody;
import com.example.AutoEcole.api.model.Inscription.InscriptionListResponse;
import com.example.AutoEcole.dal.domain.entity.Inscription;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface InscriptionService {


    CreateInscriptionResponseBody createInscription(CreateInscriptionRequestBody request, List<MultipartFile> files) throws IOException;

    /*
           Vérifier si un code promo est appliqué
           CodePromo codePromo = null;
           if (request.codePromo() != null) {
               codePromo = codePromoRepository.findByCode(request.codePromo().getCode()); // Recherche par code promo
           }

           // Si un code promo est trouvé, vérifier sa validité (expiration)

           if (codePromo != null && codePromo.getExpiry_date().isAfter(LocalDate.now())) {
               // Appliquer la réduction (si nécessaire)
           } else if (codePromo != null) {
               throw new RuntimeException("Le code promo est expiré.");
           }

            */
    List<Inscription> getInscriptionsByUserId(Long userId);

    List<InscriptionListResponse> getAllInscriptions();

    Inscription getInscriptionById(Long id);

    boolean delete(Long id);

    Inscription validateInscriptionById(Long id);
}
