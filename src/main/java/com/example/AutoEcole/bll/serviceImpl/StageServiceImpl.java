package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.api.model.Stage.CreateStageRequestBody;
import com.example.AutoEcole.api.model.Stage.CreateStageResponseBody;
import com.example.AutoEcole.bll.service.StageService;
import com.example.AutoEcole.dal.domain.entity.*;
import com.example.AutoEcole.dal.repository.StageRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StageServiceImpl implements StageService {
    private final StageRepository stageRepository;

    @Override
    public ResponseEntity<Stage> decrementStageCapacity(Long id){
        Stage stage = stageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stage avec l'ID " + id + " non trouvé"));

        if (stage.getCapacity() > 0) {
            stage.setCapacity(stage.getCapacity() - 1);
            stageRepository.save(stage);
            return ResponseEntity.ok(stage);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Override
    public CreateStageResponseBody createStage(CreateStageRequestBody request) throws Exception {
        Stage stage = request.toEntity();
        stageRepository.save(stage);
        return CreateStageResponseBody.fromEntity(stage);
    }

    @Override
    public List<Stage> getAllStages() {
        return stageRepository.findAll();
    }

    @Override
    public CreateStageResponseBody getStageById(Long id) throws Exception {
        Stage stageById = stageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stage avec l'ID " + id + " non trouvé"));

        return CreateStageResponseBody.fromEntity(stageById);
    }

    @Override
    @Transactional
    public boolean update(Long id, Stage updatedStage) throws Exception {
        Stage existingStage = stageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stage avec l'ID " + id + " non trouvé"));

        // Met à jour les champs que tu veux (exemple ci-dessous, à adapter)
        existingStage.setDateDebut(updatedStage.getDateDebut());
        existingStage.setDateFin(updatedStage.getDateFin());
        existingStage.setCity(updatedStage.getCity());
        existingStage.setStreet(updatedStage.getStreet());
        existingStage.setArrondissement(updatedStage.getArrondissement());
        existingStage.setCapacity(updatedStage.getCapacity());
        existingStage.setArrondissement(updatedStage.getArrondissement());
        existingStage.setPrice(updatedStage.getPrice());
        existingStage.setOrganisation(updatedStage.getOrganisation());
        // ... autres champs à mettre à jour

        stageRepository.save(existingStage); // facultatif avec @Transactional si l'entité est modifiée

        return true;
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
    private double extractLatitude(String json) throws JSONException {
        // Utilise un parseur JSON ici : Jackson, org.json, etc.
        JSONObject obj = new JSONObject(json);
        return obj.getJSONArray("features").getJSONObject(0)
                .getJSONObject("geometry")
                .getJSONArray("coordinates")
                .getDouble(1); // Latitude = index 1
    }

    private double extractLongitude(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        return obj.getJSONArray("features").getJSONObject(0)
                .getJSONObject("geometry")
                .getJSONArray("coordinates")
                .getDouble(0); // Longitude = index 0
    }

}

