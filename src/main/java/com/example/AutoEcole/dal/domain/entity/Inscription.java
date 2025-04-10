package com.example.AutoEcole.dal.domain.entity;
import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;
import com.example.AutoEcole.dal.domain.enum_.InscriptionStatut;
import com.example.AutoEcole.dal.domain.enum_.StageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Inscription extends BaseEntity<Long> {

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

    @OneToMany(mappedBy = "inscription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents;
}



