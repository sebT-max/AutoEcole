package com.example.AutoEcole.dal.domain.entity;

import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

public class Convocation extends BaseEntity<Long> {
    @Entity
    public class Convocation {

        @ManyToOne
        private Inscription inscription;

        private String pdfPath; // Lien vers le fichier généré

        private boolean sent;

        @Temporal(TemporalType.TIMESTAMP)
        private Date sentAt;
    }
}
