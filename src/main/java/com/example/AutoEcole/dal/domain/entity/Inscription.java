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

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Inscription extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="stage_id")
    private Stage stage;

    @Column(name = "statut_inscription")
    @Enumerated(EnumType.STRING)
    private InscriptionStatut inscriptionStatut;

    @Column(nullable = false)
    private LocalDate dateOfInscription;

    @Column
    private Integer nbrPerson;

    @Column(name = "Type_de_stage")
    @Enumerated(EnumType.STRING)
    private StageType stageType;

    @OneToOne
    @JoinColumn(name = "code_promo_id") // Correction du mapping
    private CodePromo codePromo;

    @Column
    private String lettrePdf;

}
