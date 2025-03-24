package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.api.model.Devis.CreateDevisRequestBody;
import com.example.AutoEcole.api.model.Devis.CreateDevisResponseBody;
import com.example.AutoEcole.bll.service.DevisService;
import com.example.AutoEcole.dal.domain.entity.Devis;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.Facture;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.dal.repository.DevisRepository;
import com.example.AutoEcole.dal.repository.EntrepriseRepository;
import com.example.AutoEcole.dal.repository.FactureRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DevisServiceImpl implements DevisService {
    private final DevisRepository devisRepo;
    private final EntrepriseRepository entrepriseRepo;
    private final FactureRepository factureRepo;

    @Override
    public CreateDevisResponseBody createDevis(CreateDevisRequestBody request) {

        // Récupérer l'utilisateur connecté
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

         // Vérifier si l'entreprise existe
        Entreprise entreprise = entrepriseRepo.findById(request.entrepriseId())
                .orElseThrow(() -> new RuntimeException("Entreprise introuvable"));

        // Récupérer les factures si des IDs sont fournis
        List<Facture> factures = (request.factureIds() != null && !request.factureIds().isEmpty()) ?
                factureRepo.findAllById(request.factureIds()) : new ArrayList<>();
        // Créer un devis à partir des informations de la requête

        // Créer et sauvegarder le devis
        Devis devis = new Devis(entreprise, request.numeroDevis(), request.estimatedAmount(), request.dateOfDemand(), factures);
        devisRepo.save(devis);

        // Retourner la réponse
        return new CreateDevisResponseBody(
                "Le devis a bien été créé",
                entreprise.getId(),
                request.numeroDevis(),
                request.dateOfDemand(),
                request.estimatedAmount(),
                factures.stream().map(Facture::getId).toList()
        );
    }

    @Override
    public List<Devis> getAllDevis() {
        return devisRepo.findAll();
    }

    @Override
    public Devis getDevisById(Long id) {
        Devis devisById = devisRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Planète non trouvé"));
        return devisById;
    }

    @Override
    @Transactional
    public boolean update(Long id, Devis devis) {
        Devis devisToUpdate = getDevisById(id);
        try{
            devisRepo.save(devisToUpdate);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        Optional<Devis> devis = devisRepo.findById(id);
        if (devis.isPresent()) {
            devisRepo.deleteById(id);
            return true;
        }
        return false;  // Booking not found
    }

}


