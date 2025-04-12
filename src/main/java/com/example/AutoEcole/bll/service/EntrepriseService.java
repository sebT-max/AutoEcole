package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.api.model.Entreprise.EntrepriseRegisterRequestBody;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface EntrepriseService {
    Entreprise register(@Valid EntrepriseRegisterRequestBody requestBody);
    //Entreprise login(String email, String password);

}
