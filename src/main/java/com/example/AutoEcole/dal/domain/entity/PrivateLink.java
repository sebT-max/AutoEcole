package com.example.AutoEcole.dal.domain.entity;

import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PrivateLink extends BaseEntity<Long> {

    @ManyToOne
    private Entreprise entreprise;

    @ManyToOne
    private Stage stage;

    @Column(unique = true)
    private String token; // UUID ou chaîne aléatoire

    private LocalDateTime expirationDate;
    // facultatif
    private boolean active = true;

    @Column(nullable = false)
    private int maxUsages;

    @Column(nullable = false)
    private int usageCount = 0;

}
