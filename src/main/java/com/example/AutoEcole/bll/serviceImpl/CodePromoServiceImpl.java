package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.api.model.CodePromo.CreateCodePromoRequestBody;
import com.example.AutoEcole.api.model.CodePromo.CreateCodePromoResponseBody;
import com.example.AutoEcole.bll.service.CodePromoService;
import com.example.AutoEcole.dal.domain.entity.CodePromo;
import com.example.AutoEcole.dal.repository.CodePromoRepository;
import org.springframework.stereotype.Service;

@Service
public class CodePromoServiceImpl implements CodePromoService {

    private final CodePromoRepository codePromoRepository;

    public CodePromoServiceImpl(CodePromoRepository codePromoRepository) {
        this.codePromoRepository = codePromoRepository;
    }

    @Override
    public CreateCodePromoResponseBody createCodePromo(CreateCodePromoRequestBody request) {
        //User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CodePromo codePromo = new CodePromo();
        codePromo.setCode(request.code());
        codePromo.setReduction(request.reduction());
        codePromo.setExpiry_date(request.expiry_date());
        codePromoRepository.save(codePromo);

        return new CreateCodePromoResponseBody(
                codePromo.getCode(),
                codePromo.getReduction(),
                codePromo.getExpiry_date()
        );
    }
}
/*
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

 */

