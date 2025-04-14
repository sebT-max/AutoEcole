package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.api.model.Entreprise.EntrepriseRegisterRequestBody;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.repository.EntrepriseRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface EntrepriseService {

    Entreprise register(@Valid EntrepriseRegisterRequestBody requestBody);

    Entreprise getEntrepriseByEmail(String email);

    //Entreprise login(String email, String password);

}
