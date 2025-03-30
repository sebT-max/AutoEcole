package com.example.AutoEcole.dal.domain.entity;

import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;
import com.example.AutoEcole.dal.domain.enum_.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "stage")

public class Stage extends BaseEntity<Long> {
    //@Column
    //@Enumerated(EnumType.STRING)
    //private StageType stageType;

    @Column
    private LocalDate dateDeStage;

    @Column
    private String city;

    @Column
    private String street;

    @Column
    private String arrondissement;

    @Column
    private Integer capacity;

    @Column
    private Double price;

    @Column
    private String organisation;

    @Column
    @Enumerated(EnumType.STRING)
    private TwoDaysOfTheWeek twoDaysOfTheWeek ;

    @Column
    @Enumerated(EnumType.STRING)
    private Month month ;

    @Column (name = "statut")
    @Enumerated(EnumType.STRING)
    private StatutStage statutStage;


}
