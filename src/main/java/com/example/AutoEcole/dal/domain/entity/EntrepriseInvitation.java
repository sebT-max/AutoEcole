package com.example.AutoEcole.dal.domain.entity;

import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class EntrepriseInvitation extends BaseEntity<Long> {

    private String token;

    @ManyToOne
    private Entreprise entreprise;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    // Optionnel : email invité, utilisé, rôle, etc.

    // Getters / setters
}
