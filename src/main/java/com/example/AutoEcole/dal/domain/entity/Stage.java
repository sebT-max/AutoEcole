package com.example.AutoEcole.dal.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "journey")

public class Stage {
    @Column
    private Long capacity;

    @Column
    private Long FlightDurationInHours;

    @Column
    private Long Price;

    @Column
    private String destination;

    @OneToMany(mappedBy = "journey")
    private set<Passenger> passengers;

    @JoinColumn(name="planet_id",nullable = false)
    private String planet;
}
