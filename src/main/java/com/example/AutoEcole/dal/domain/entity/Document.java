package com.example.AutoEcole.dal.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Document {

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name="journey_id", nullable = false)
    private Journey journey;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

}
