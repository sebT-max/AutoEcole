package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.Exception.UserNotFound.UserNotFoundException;
import com.example.AutoEcole.api.model.DemandeDevis.CreateDemandeDevisRequestBody;
import com.example.AutoEcole.api.model.DemandeDevis.CreateDemandeDevisResponseBody;
import com.example.AutoEcole.bll.service.DemandeDevisService;
import com.example.AutoEcole.dal.domain.entity.DemandeDevis;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.repository.DemandeDevisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemandeDevisServiceImpl implements DemandeDevisService {

    private final DemandeDevisRepository demandeDevisRepository;

    @Override
    public CreateDemandeDevisResponseBody createDemandeDevis(CreateDemandeDevisRequestBody request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Entreprise entreprise;
        if (principal instanceof Entreprise) {
            entreprise = (Entreprise) principal;
        } else {
            throw new UserNotFoundException("User is not an entreprise");
        }

// Créer la demande
        DemandeDevis demandeDevis = new DemandeDevis();
        demandeDevis.setUser(entreprise); // ✔️ entreprise est une instance de User
        demandeDevis.setContactFirstName(request.contactFirstName());
        demandeDevis.setContactLastName(request.contactLastName());
        demandeDevis.setNumberOfInterns(request.numberOfInterns());
        demandeDevis.setAcceptDevis(request.acceptDevis());

        demandeDevisRepository.save(demandeDevis);

// Tu peux accéder au nom de l'entreprise ici :
        String nomEntreprise = entreprise.getName(); // par exemple

        return new CreateDemandeDevisResponseBody(
                "Réservation effectuée avec succès !",
                entreprise,
                demandeDevis.getContactFirstName(),
                demandeDevis.getContactLastName(),
                demandeDevis.getNumberOfInterns(),
                demandeDevis.isAcceptDevis()
        );
    }
}
