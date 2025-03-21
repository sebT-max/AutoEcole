package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.api.model.Stage.CreateStageRequestBody;
import com.example.AutoEcole.api.model.Stage.CreateStageResponseBody;
import com.example.AutoEcole.dal.domain.entity.Stage;

import java.time.LocalDate;
import java.util.List;

public interface StageService {
    CreateStageResponseBody createStage(CreateStageRequestBody request);
    public List<Stage> getAllStages();
    public Stage getStageById(Long id);

    boolean update(Long id, Stage stage);

    boolean delete(Long id);

    List<Stage> searchStages(String entreprise, String localisation, Integer duree, LocalDate dateDebut);
}
