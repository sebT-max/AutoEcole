package com.example.AutoEcole.api.model.Stage;

import com.example.AutoEcole.dal.domain.enum_.StageType;

import java.time.LocalDate;
import java.util.Date;

public record CreateStageRequestBody(

   LocalDate dateDeStage,

   String city,

   String street,

   String arrondissement,

   int capacity,

   Double price,

   String organisation

) {

}
