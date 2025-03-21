package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.api.model.Devis.CreateDevisRequestBody;
import com.example.AutoEcole.api.model.Devis.CreateDevisResponseBody;
import com.example.AutoEcole.bll.service.DevisService;
import com.example.AutoEcole.dal.domain.entity.Devis;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.dal.repository.DevisRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DevisServiceImpl implements DevisService {
    private final DevisRepository devisRepo;

    @Override
    public CreateDevisResponseBody createDevis(CreateDevisRequestBody request) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Devis devis = new Devis();
        devisRepo.save(devis);
        // Retour
        return new CreateDevisResponseBody("La planète a bien été créé", request.name(), request.description());
    }

    @Override
    public List<Devis> getAllDevis() {
        return devisRepo.findAll();
    }

    @Override
    public Devis getDevisById(Long id) {
        Devis planetById = devisRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Planète non trouvé"));
        return planetById;
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


