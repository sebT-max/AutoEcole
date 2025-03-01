package com.example.AutoEcole.api.model.Stage;

public record CreateStageResponseBody(
        String message,
        Long capacity,
        Long flightDurationInHours,
        Long price,
        String destination,
        //@OneToMany(mappedBy = "journey")//private set<Passenger> passengers;
        String planet
) {

}
