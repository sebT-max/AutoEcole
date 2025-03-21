package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.api.model.Stage.CreateStageRequestBody;
import com.example.AutoEcole.api.model.Stage.CreateStageResponseBody;
import com.example.AutoEcole.bll.service.JourneyService;
import com.example.AutoEcole.bll.service.StageService;
import com.example.AutoEcole.dal.domain.entity.*;
import com.example.AutoEcole.dal.repository.JourneyRepository;
import com.example.AutoEcole.dal.repository.PlanetRepository;
import com.example.AutoEcole.dal.repository.StageRepository;
import com.example.AutoEcole.dal.specifications.StageSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StageServiceImpl implements StageService {
    private final StageRepository stageRepository;
    private final PlanetRepository planetRepository;

    @Override
    public CreateStageResponseBody createStage(CreateStageRequestBody request) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Planet planet = planetRepository.findPlanetByName(request.planet()); // Recherche dans la base de données

        if (planet == null) {
            // Si la planète n'existe pas dans la base, lancer une exception
            throw new RuntimeException("Planète non trouvée avec le nom: " + request.planet());
        }
        Stage stage = new Stage();
        stage.setFlightDurationInHours(request.flightDurationInHours());
        stage.setPrice(request.price());
        stage.setDestination(request.destination());
        stage.setPlanet(planet);
       stageRepository.save(stage);
       //retour

        return new CreateStageResponseBody(
                "Le voyage a bien été créé",
                (long) stage.getCapacity(), // Convert capacity to Long for response
                stage.getFlightDurationInHours(),
                stage.getPrice(),
                stage.getDestination(),
                stage.getPlanet().getName());
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
        try{
            stageToUpdate.setFlightDurationInHours(stage.getFlightDurationInHours());
            stageToUpdate.setPrice(stage.getPrice());
            stageToUpdate.setDestination(stage.getDestination());
            stageToUpdate.setPlanet(stage.getPlanet());
            stageRepository.save(stageToUpdate);
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
    @Override
    public List<Stage> searchStages(String entreprise, String localisation, Integer duree, LocalDate dateDebut) {
        Specification<Stage> spec = StageSpecification.filterBy(entreprise, localisation, duree, dateDebut);
        return stageRepository.findAll(spec);
    }

}

