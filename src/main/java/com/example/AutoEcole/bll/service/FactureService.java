package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.dal.domain.entity.Facture;

import java.util.List;

public interface FactureService {
    CreateFactureResponseBody createPlanet(CreateFactureRequestBody request);
    public List<Facture> getAllPlanets();
    public Facture getPlanetById(Long id);

    boolean update(Long id, Facture facture);

    boolean delete(Long id);
}
