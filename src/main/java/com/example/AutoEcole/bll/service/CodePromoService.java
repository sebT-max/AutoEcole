package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.api.model.CodePromo.CreateCodePromoRequestBody;
import com.example.AutoEcole.api.model.CodePromo.CreateCodePromoResponseBody;
import com.example.AutoEcole.dal.domain.entity.CodePromo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CodePromoService {
    CreateCodePromoResponseBody createCodePromo(CreateCodePromoRequestBody request);
    /*
    public List<CodePromo> getAllCodePromos();
    public CodePromo getCodePromoById(Long id);

    boolean update(Long id, CodePromo planet);

    boolean delete(Long id);

     */
}
