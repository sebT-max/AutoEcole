package com.example.AutoEcole.dal.domain.entity;

import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class PrivateLink extends BaseEntity<Long> {

    @ManyToOne
    private Entreprise entreprise;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id", nullable = false)
    private Stage stage;

    @Column(unique = true)
    private String token; // UUID ou chaîne aléatoire

    private LocalDateTime expirationDate;
//     facultatif
    private boolean active = true;

    @Column(nullable = false)
    private int maxUsages;

    @Column(nullable = false)
    private int usageCount = 0;


}

//Créer le lien
//Lien créé pour Jupiler.
//http://localhost:4200/inscription/d738d4d7-94aa-4575-a54f-9a50aef0faf0
