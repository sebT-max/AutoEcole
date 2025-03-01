package com.example.AutoEcole.dal.domain.entity;

import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;
import com.example.AutoEcole.dal.domain.enum_.BloodType;
import com.example.AutoEcole.dal.domain.enum_.DevisStatut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Devis extends BaseEntity<Long> {

    @OneToMany(mappedBy = "devis")
    Entreprise entreprise_id;

    @Column(name="numero_devis")
    private String numeroDevis;

    @Column(name="montant_estimé")
    Double estimated_amount;
-
    @Column(name="date_demande")
    LocalDate dateOfDemand;

    @OneToMany(mappedBy = "devis", cascade = CascadeType.ALL)
    private List<Facture> factures;

    @Column(name = "statut")
    @Enumerated(EnumType.STRING)
    private DevisStatut devisStatut;
}
