package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.api.model.Stage.CreateStageRequestBody;
import com.example.AutoEcole.api.model.Stage.CreateStageResponseBody;
import com.example.AutoEcole.dal.domain.entity.Stage;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StageService {

    CreateStageResponseBody createStage(CreateStageRequestBody request) throws Exception;
    public List<Stage> getAllStages();

    CreateStageResponseBody getStageById(Long id) throws Exception;

    boolean update(Long id, Stage stage)throws Exception;
    boolean delete(Long id);

    //List<Stage> searchStages(String entreprise, String localisation, Integer duree, String dateDebut);

    List<Stage> searchStages(String searchTerm);
    public ResponseEntity<Stage> decrementStageCapacity(Long id);
   /*
    List<Stage> searchStages(String entreprise, String localisation, Integer duree, LocalDate dateDebut);
*/
}

