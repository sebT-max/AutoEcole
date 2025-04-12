package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.api.model.Particulier.ParticulierRegisterRequestBody;
import com.example.AutoEcole.dal.domain.entity.Particulier;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ParticulierService {

    Particulier register(ParticulierRegisterRequestBody requestBody);
}
