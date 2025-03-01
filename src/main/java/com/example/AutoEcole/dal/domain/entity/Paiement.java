package com.example.AutoEcole.dal.domain.entity;


import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Paiement extends BaseEntity<Long>  {

    @Column(nullable = false)
    private String montant;
    @Column(nullable = false)
    private String statut;
    @NotNull(message = "The birth Date do not be null")
    private LocalDate mode;
    @Column(nullable = false)
    private LocalDate Date;

}
