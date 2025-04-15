package com.example.AutoEcole.dal.domain.entity;
import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;
import com.example.AutoEcole.dal.domain.enum_.InscriptionStatut;
import com.example.AutoEcole.dal.domain.enum_.StageType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Inscription extends BaseEntity<Long> {

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne()
    @JoinColumn(name="stage_id",nullable = false)
    private Stage stage;

    @Column(name = "statut_inscription", nullable = false)
    @Enumerated(EnumType.STRING)
    private InscriptionStatut inscriptionStatut;

    @Column
    private Integer nbrPerson;

    @Column(name = "Type_de_stage", nullable = false)
    @Enumerated(EnumType.STRING)
    private StageType stageType;

    @OneToOne
    @JoinColumn(name = "code_promo_id") // Correction du mapping
    private CodePromo codePromo;

    @JsonBackReference
    @OneToMany(mappedBy = "inscription", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Document> documents;

}



