package com.example.AutoEcole.dal.domain.entity;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class Review {
    @Column
    private int grade;

    @Column
    private String comment;

    @Column
    private LocalDate date;

}
