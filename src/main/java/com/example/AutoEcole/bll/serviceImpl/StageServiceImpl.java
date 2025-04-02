package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.api.model.Stage.CreateStageRequestBody;
import com.example.AutoEcole.api.model.Stage.CreateStageResponseBody;
import com.example.AutoEcole.bll.service.StageService;
import com.example.AutoEcole.dal.domain.entity.*;
import com.example.AutoEcole.dal.repository.StageRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StageServiceImpl implements StageService {
    private final StageRepository stageRepository;

    @Override
    public CreateStageResponseBody createStage(CreateStageRequestBody request) {

        //User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Stage stage = new Stage();
        stage.setDateDeStage(request.dateDeStage());
        stage.setPrice(request.price());
        stage.setCity(request.city());
        stage.setStreet(request.street());
        stage.setArrondissement(request.arrondissement());
        stage.setCapacity(request.capacity());
        stage.setOrganisation(request.organisation());
       stageRepository.save(stage);
       //retour

        return new CreateStageResponseBody(
                stage.getDateDeStage(),
                stage.getPrice(),
                stage.getCity(),
                stage.getStreet(),
                stage.getArrondissement(),
                stage.getCapacity(),
                stage.getOrganisation()
        );
    }

    @Override
    public List<Stage> getAllStages() {
        return stageRepository.findAll();
    }

    @Override
    public Stage getStageById(Long id) {
        Stage stageById = stageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voyage non trouvé"));
        return stageById;
    }

    @Override
    @Transactional
    public boolean update(Long id, Stage stage) {
        Stage stageToUpdate = getStageById(id);
        if (stageToUpdate == null) {
            throw new EntityNotFoundException("Réservation avec l'ID " + id + " non trouvée");
        }
        try{
            stageToUpdate.setDateDeStage(stage.getDateDeStage());
            stageToUpdate.setPrice(stage.getPrice());
            stageToUpdate.setCity(String.valueOf(stage.getPrice()));
            stageToUpdate.setStreet(stage.getStreet());
            stageToUpdate.setArrondissement(stage.getArrondissement());
            stageToUpdate.setCapacity(stage.getCapacity());
            stageToUpdate.setOrganisation(stage.getOrganisation());
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        Optional<Stage> stage = stageRepository.findById(id);
        if (stage.isPresent()) {
            stageRepository.deleteById(id);
            return true;
        }
        return false;  // Booking not found
    }

//    @Override
//    public List<Stage> searchStages(String entreprise, String localisation, Integer duree, String dateDebut) {
//        return stageRepository.searchByFilters(entreprise, localisation, duree, dateDebut);
//    }

    @Override
    public List<Stage> searchStages(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return getAllStages(); // Si le terme de recherche est vide, retourner tous les stages
        }
        return stageRepository.searchByFilters(searchTerm);
    }
    /*
    @Override
    public List<Stage> searchStages(String entreprise, String localisation, Integer duree, LocalDate dateDebut) {
        Specification<Stage> spec = StageSpecification.filterBy();
        return stageRepository.findAll(spec);
    }

     */

}

