package com.example.AutoEcole.dal.domain.entity;

import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;
import com.example.AutoEcole.dal.domain.enum_.DevisStatut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Devis extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name="entreprise_id")
    Entreprise entreprise;

    @Column(name="numero_devis")
    private String numeroDevis;

    @Column(name="montant_estimé")
    private Double estimatedAmount;

    @Column(name="date_demande")
    private LocalDate dateOfDemand;

    @OneToMany(mappedBy = "devis", cascade = CascadeType.ALL)
    private List<Facture> factures;

    @Column(name = "statut")
    @Enumerated(EnumType.STRING)
    private DevisStatut devisStatut;

    public Devis(Entreprise entreprise, String numeroDevis, Double estimatedAmount, LocalDate dateOfDemand, List<Facture> factures) {
        this.entreprise = entreprise;
        this.numeroDevis = numeroDevis;
        this.estimatedAmount = estimatedAmount;
        this.dateOfDemand = dateOfDemand;
        this.factures = factures != null ? factures : new ArrayList<>();
    }
}
