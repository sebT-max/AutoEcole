package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.Stage.CreateStageRequestBody;
import com.example.AutoEcole.api.model.Stage.CreateStageResponseBody;
import com.example.AutoEcole.bll.service.StageService;
import com.example.AutoEcole.dal.domain.entity.Stage;
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
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateStageResponseBody> createBooking(@RequestBody CreateStageRequestBody request){
        return ResponseEntity.ok(stageService.createStage(request));
    }

    @GetMapping("/all")
    public List<Stage> getAllJourneys(){
        return stageService.getAllStages();
    }

    @GetMapping("/{id}")
    public Stage getStageById(@PathVariable Long id){
        return stageService.getStageById(id);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteStage(@PathVariable Long id){
        return stageService.delete(id);
    }
}
