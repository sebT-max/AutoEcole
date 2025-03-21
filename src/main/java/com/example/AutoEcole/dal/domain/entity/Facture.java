package com.example.AutoEcole.dal.domain.entity;

import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;
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
public class Facture extends BaseEntity<Long> {

    private String numeroFacture;
    private LocalDate dateEmission;

    @ManyToOne
    @JoinColumn(name = "devis_id")
    private Devis devis;

    @Column(nullable = false)
    private Double price;

}
