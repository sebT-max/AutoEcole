package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.dal.domain.entity.Paiement;

import java.util.List;

public interface PaiementService {
    CreatePaiementResponseBody createPlanet(CreatePaiementRequestBody request);
    public List<Paiement> getAllPlanets();
    public Paiement getPlanetById(Long id);

    boolean update(Long id, Paiement paiement);

    boolean delete(Long id);}
