package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.api.model.Devis.CreateDevisRequestBody;
import com.example.AutoEcole.api.model.Devis.CreateDevisResponseBody;
import com.example.AutoEcole.dal.domain.entity.Devis;

import java.util.List;

public interface DevisService {
    CreateDevisResponseBody createDevis(CreateDevisRequestBody request);
    public List<Devis> getAllDevis();
    public Devis getDevisById(Long id);
    boolean update(Long id, Devis devis);
    boolean delete(Long id);
}
