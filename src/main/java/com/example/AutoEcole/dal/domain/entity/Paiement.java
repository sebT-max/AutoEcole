package com.example.AutoEcole.dal.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Paiement {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

}
