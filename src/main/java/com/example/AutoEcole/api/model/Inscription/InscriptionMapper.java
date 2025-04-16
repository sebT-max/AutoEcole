package com.example.AutoEcole.api.model.Inscription;

import com.example.AutoEcole.api.model.Document.DocumentMapper;
import com.example.AutoEcole.api.model.Document.DocumentDTO;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.Inscription;
import com.example.AutoEcole.dal.domain.entity.Particulier;
import com.example.AutoEcole.dal.domain.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InscriptionMapper {

    private final DocumentMapper documentMapper;

    public InscriptionMapper(DocumentMapper documentMapper) {
        this.documentMapper = documentMapper;
    }

    private String getNom(User user) {
        if (user instanceof Particulier particulier) {
            return particulier.getLastname();
        } else if (user instanceof Entreprise entreprise) {
            return entreprise.getName(); // ou "" si tu ne veux pas afficher de nom pour entreprise
        }
        return "";
    }

    private String getPrenom(User user) {
        if (user instanceof Particulier particulier) {
            return particulier.getFirstname();
        }
        return ""; // Entreprise n’a pas de prénom
    }

    public InscriptionListResponse toListResponse(Inscription i) {
        List<DocumentDTO> documentDTOs = i.getDocuments() != null
                ? i.getDocuments().stream().map(documentMapper::toDto).toList()
                : List.of();

        User user = i.getUser();  // Récupère l'utilisateur lié à l'inscription


        return new InscriptionListResponse(
                i.getId(),
                user.getId(),
                getNom(user),
                getPrenom(user),
                i.getStage().getId(),
                i.getStage().getCity(),
                i.getStage().getStreet(),
                i.getUser().getUsername(),
                i.getStageType(),
                i.getInscriptionStatut(),
                documentDTOs
        );
    }

    // Tu peux aussi ajouter toEntity si besoin
}
