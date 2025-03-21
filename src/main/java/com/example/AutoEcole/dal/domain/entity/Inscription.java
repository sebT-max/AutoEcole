package com.example.AutoEcole.dal.domain.entity;

import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;
import com.example.AutoEcole.dal.domain.enum_.InscriptionStatut;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;


import java.time.LocalDate;
import java.util.Date;

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
    Integer nbrPerson;

    @OneToOne(mappedBy = "code_promo")
    private CodePromo codePromo;

    @NotNull
    private Boolean acceptTerms;

}
