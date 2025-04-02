package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.api.model.Particulier.ParticulierRegisterRequestBody;

public interface ParticulierService {

    Long register(ParticulierRegisterRequestBody requestBody);
}
