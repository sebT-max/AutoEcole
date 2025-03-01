package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.api.model.Stage.CreateStageRequestBody;
import com.example.AutoEcole.api.model.Stage.CreateStageResponseBody;
import com.example.AutoEcole.bll.service.JourneyService;
import com.example.AutoEcole.dal.domain.entity.*;
import com.example.AutoEcole.dal.repository.JourneyRepository;
import com.example.AutoEcole.dal.repository.PlanetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StageServiceImpl implements JourneyService {
    private final JourneyRepository journeyRepository;
    private final PlanetRepository planetRepository;

    @Override
    public CreateStageResponseBody createJourney(CreateStageRequestBody request) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Planet planet = planetRepository.findPlanetByName(request.planet()); // Recherche dans la base de données

        if (planet == null) {
            // Si la planète n'existe pas dans la base, lancer une exception
            throw new RuntimeException("Planète non trouvée avec le nom: " + request.planet());
        }
        Journey journey = new Journey();
       journey.setFlightDurationInHours(request.flightDurationInHours());
       journey.setPrice(request.price());
       journey.setDestination(request.destination());
       journey.setPlanet(planet);
       journeyRepository.save(journey);
       //retour

        return new CreateStageResponseBody(
                "Le voyage a bien été créé",
                (long) journey.getCapacity(), // Convert capacity to Long for response
                journey.getFlightDurationInHours(),
                journey.getPrice(),
                journey.getDestination(),
                journey.getPlanet().getName());
    }

    @Override
    public List<Journey> getJourneysByPlanetName(String planetName) {
        return journeyRepository.findJourneysByPlanetName(planetName);
    }

    @Override
    public List<Journey> getAllJourneys() {
        return journeyRepository.findAll();
    }

    @Override
    public Journey getJourneyById(Long id) {
        Journey journeyById = journeyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voyage non trouvé"));
        return journeyById;
    }

    @Override
    @Transactional
    public boolean update(Long id, Journey journey) {
        Journey journeyToUpdate = getJourneyById(id);
        try{
            journeyToUpdate.setFlightDurationInHours(journey.getFlightDurationInHours());
            journeyToUpdate.setPrice(journey.getPrice());
            journeyToUpdate.setDestination(journey.getDestination());
            journeyToUpdate.setPlanet(journey.getPlanet());
            journeyRepository.save(journeyToUpdate);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        Optional<Journey> journey = journeyRepository.findById(id);
        if (journey.isPresent()) {
            journeyRepository.deleteById(id);
            return true;
        }
        return false;  // Booking not found
    }
}

