package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.api.model.DemandeDevis.CreateDemandeDevisRequestBody;
import com.example.AutoEcole.api.model.DemandeDevis.CreateDemandeDevisResponseBody;

public interface DemandeDevisService {
    CreateDemandeDevisResponseBody createDemandeDevis(CreateDemandeDevisRequestBody request);
}
