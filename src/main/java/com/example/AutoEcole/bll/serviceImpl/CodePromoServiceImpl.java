package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.api.model.CodePromo.CreateCodePromoRequestBody;
import com.example.AutoEcole.api.model.CodePromo.CreateCodePromoResponseBody;
import com.example.AutoEcole.bll.service.CodePromoService;
import com.example.AutoEcole.bll.service.UserService;
import com.example.AutoEcole.dal.domain.entity.CodePromo;

import java.util.List;

public class CodePromoServiceImpl implements CodePromoService {
    @Override
    public CreateCodePromoResponseBody createCodePromo(CreateCodePromoRequestBody request) {
        return null;
    }

    @Override
    public List<CodePromo> getAllCodePromos() {
        return List.of();
    }

    @Override
    public CodePromo getCodePromoById(Long id) {
        return null;
    }

    @Override
    public boolean update(Long id, CodePromo planet) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
