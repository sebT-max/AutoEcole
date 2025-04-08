package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.api.model.DemandeDevis.CreateDemandeDevisRequestBody;
import com.example.AutoEcole.api.model.DemandeDevis.CreateDemandeDevisResponseBody;
import com.example.AutoEcole.dal.domain.entity.DemandeDevis;

import java.util.List;

public interface DemandeDevisService {
    CreateDemandeDevisResponseBody createDemandeDevis(CreateDemandeDevisRequestBody request);
    List<CreateDemandeDevisResponseBody> getAllDemandeDevis();
}
