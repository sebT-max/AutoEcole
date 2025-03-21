package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.api.model.facture.CreateFactureRequestBody;
import com.example.AutoEcole.api.model.facture.CreateFactureResponseBody;
import com.example.AutoEcole.dal.domain.entity.Facture;

import java.util.List;

public interface FactureService {
    CreateFactureResponseBody createPlanet(CreateFactureRequestBody request);
    public List<Facture> getAllFactures();
    public Facture getFactureById(Long id);

    boolean update(Long id, Facture facture);

    boolean delete(Long id);
}
