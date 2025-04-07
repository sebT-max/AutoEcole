package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.api.model.Inscription.CreateInscriptionRequestBody;
import com.example.AutoEcole.api.model.Inscription.CreateInscriptionResponseBody;
import com.example.AutoEcole.dal.domain.entity.Inscription;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface InscriptionService {

    CreateInscriptionResponseBody createInscription(CreateInscriptionRequestBody request, MultipartFile fileName);

    List<Inscription> getInscriptionsByUserId(Long userId);

    List<Inscription> getAllInscriptions();


    Inscription getInscriptionById(Long id);
    boolean delete(Long id);

    Inscription validateInscriptionById(Long id);
    /*
    @Transactional
    boolean update(Long id, Inscription inscription);

 */
}
