package com.example.AutoEcole.dal.domain.entity;

import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;
import com.example.AutoEcole.dal.domain.enum_.CodePromoStatut;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CodePromo  extends BaseEntity<Long> {

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private Integer reduction;

    @Column(nullable = false)
    private LocalDate expiry_date;

    @Column(name = "codePromo_statut")
    @Enumerated(EnumType.STRING)
    private CodePromoStatut codePromoStatut;
}
