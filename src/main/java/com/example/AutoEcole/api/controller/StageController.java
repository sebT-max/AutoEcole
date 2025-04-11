package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.Stage.CreateStageRequestBody;
import com.example.AutoEcole.api.model.Stage.CreateStageResponseBody;
import com.example.AutoEcole.bll.service.StageService;
import com.example.AutoEcole.dal.domain.entity.Stage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/V1/stages")
public class StageController {
    private final StageService stageService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateStageResponseBody> createStage(@RequestBody @Valid CreateStageRequestBody request){
        return ResponseEntity.ok(stageService.createStage(request));
    }

    @GetMapping("/all")
    public List<Stage> getAllStages(){
        return stageService.getAllStages();
    }

    @GetMapping("/{id}")
    public Stage getStageById(@PathVariable Long id){
        return stageService.getStageById(id);
    }


    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<Boolean> updateBooking(
            @PathVariable Long id,
            @RequestBody @Valid CreateStageRequestBody request) {

        Stage stage = stageService.getStageById(id);
        if (stage == null) {
            return ResponseEntity.notFound().build();
        }
        // Crée la nouvelle entité Booking avec les nouvelles valeurs, en conservant l'utilisateur existant
        Boolean updatedPlanet = stageService.update(id,request.toEntity());

        return ResponseEntity.ok(updatedPlanet);
    }


    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteStage(@PathVariable Long id){
        return stageService.delete(id);
    }

    // ✅ Ajout du filtre de recherche
    @GetMapping("/search")
    public List<Stage> searchStages(@RequestParam String searchTerm) {
        return stageService.searchStages(searchTerm);
    }
}
