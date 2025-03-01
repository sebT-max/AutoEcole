package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.Stage.CreateStageRequestBody;
import com.example.AutoEcole.api.model.Stage.CreateStageResponseBody;
import com.example.AutoEcole.bll.service.JourneyService;
import com.example.AutoEcole.bll.service.PlanetService;
import com.example.AutoEcole.dal.domain.entity.Journey;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/V1/journey")
public class StageController {
    private final JourneyService journeyService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<CreateStageResponseBody> createBooking(@RequestBody CreateStageRequestBody request){
        return ResponseEntity.ok(journeyService.createJourney(request));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('OPERATOR')")
    public List<Journey> getAllJourneys(){
        return journeyService.getAllJourneys();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OPERATOR')")
    public Journey getJourneyById(@PathVariable Long id){
        return journeyService.getJourneyById(id);
    }

    @GetMapping("/planet/{planetName}")
    public ResponseEntity<List<Journey>> getJourneysByPlanet(@PathVariable String planetName) {
        List<Journey> journeys = journeyService.getJourneysByPlanetName(planetName);
        if (journeys.isEmpty()) {
            return ResponseEntity.notFound().build(); // Aucun voyage trouvé pour cette planète
        }
        return ResponseEntity.ok(journeys); // Retourne la liste des voyages
    }
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('OPERATOR')")
    public boolean deleteJourney(@PathVariable Long id){
        return journeyService.delete(id);
    }
}
