package com.example.AutoEcole.api.model.Stage;

import com.example.AutoEcole.dal.domain.entity.Planet;

public record CreateStageRequestBody(
        //Long capacity,
        Long flightDurationInHours,
        Long price,
        String destination,
        //@OneToMany(mappedBy = "journey")//private set<Passenger> passengers;
        String planet
) {

}
