package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.api.model.Stage.CreateStageRequestBody;
import com.example.AutoEcole.api.model.Stage.CreateStageResponseBody;
import com.example.AutoEcole.bll.service.MapboxService;
import com.example.AutoEcole.bll.service.StageService;
import com.example.AutoEcole.dal.domain.entity.*;
import com.example.AutoEcole.dal.repository.StageRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StageServiceImpl implements StageService {
    private final StageRepository stageRepository;
    private final MapboxService mapboxService;

    @Override
    public CreateStageResponseBody createStage(CreateStageRequestBody request) throws Exception {
        Stage stage = request.toEntity();
        stageRepository.save(stage);

        // Récupérer coordonnées Mapbox
        String fullAddress = stage.getFullAddress();
        String geocodeJson = mapboxService.getGeocodeData(fullAddress);
        double latitude = extractLatitude(geocodeJson);
        double longitude = extractLongitude(geocodeJson);

        return CreateStageResponseBody.fromEntity(stage, latitude, longitude);
    }

    @Override
    public List<Stage> getAllStages() {
        return stageRepository.findAll();
    }

    @Override
    public CreateStageResponseBody getStageById(Long id) throws Exception {
        Stage stageById = stageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stage non trouvé"));

        String fullAddress = stageById.getFullAddress();
        String geocodeJson = mapboxService.getGeocodeData(fullAddress);
        double latitude = extractLatitude(geocodeJson);
        double longitude = extractLongitude(geocodeJson);

        return CreateStageResponseBody.fromEntity(stageById, latitude, longitude);
    }

    @Override
    @Transactional
    public boolean update(Long id, Stage stage) throws Exception {
        CreateStageResponseBody stageToUpdate = getStageById(id);
        throw new EntityNotFoundException("Réservation avec l'ID " + id + " non trouvée");
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

