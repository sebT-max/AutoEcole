package com.example.AutoEcole.dal.domain.entity;

import jakarta.persistence.Column;

public class DemandeDevis {
    @Column
    private String companyName;
    @Column
    private String contactFirstName;
    @Column
    private String contactFamilyName;
    @Column
    private int numberOfInterns;

    private boolean acceptDevis;
}
