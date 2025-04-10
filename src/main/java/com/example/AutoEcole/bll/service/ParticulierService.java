package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.api.model.Particulier.ParticulierRegisterRequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ParticulierService {

    Long register(ParticulierRegisterRequestBody requestBody, List<MultipartFile> files);
}
