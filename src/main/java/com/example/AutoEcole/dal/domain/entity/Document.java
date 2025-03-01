package com.example.AutoEcole.dal.domain.entity;

import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



public class Document extends BaseEntity<Long> {

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
