package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.bll.exception.alreadyExist.AlreadyExistException;
import com.example.AutoEcole.bll.exception.ressourceNotFound.RessourceNotFoundException;
import com.example.AutoEcole.bll.service.UniversalIdService;
import com.example.AutoEcole.dal.domain.entity.UniversalId;
import com.example.AutoEcole.dal.repository.UniversalIDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements UniversalIdService {
    private final UniversalIDRepository universalIDRepository;


    @Override
    public Long create(UniversalId universalId) {
        if(universalIDRepository.existUniversalIdByUniversalNumber(universalId.getUniversalNumber())){
            throw new AlreadyExistException("This UniversalId " + universalId.getUniversalNumber() +
                    " Already Exits");
        }else{
            return universalIDRepository.save(universalId).getId();
        }
    }

    @Override
    public UniversalId findUniversalIdByUniversalNumber(String universalNumber) {
       if(!universalIDRepository.existUniversalIdByUniversalNumber(universalNumber)){
           throw new RessourceNotFoundException("This number + " + universalNumber + " does not exist");
       }else{
           return universalIDRepository.findUniversalIdByUniversalNumber(universalNumber);
       }
    }

    @Override
    public UniversalId findById(Long id) {
        return universalIDRepository.findById(id).orElseThrow(
                () -> new RessourceNotFoundException("The id : " + id + " does not esxist " +
                        "for UniversalId")
        );
    }

    @Override
    public List<UniversalId> findAll() {
        return universalIDRepository.findAll();
    }

    @Override
    public boolean update(Long id, UniversalId universalId) {
        /*
        si universalId.getUniversalId
         */
        boolean doublon = false;
        String temp = universalId.getUniversalNumber();
        List<UniversalId> listUIDs = findAll();
        for(UniversalId el : listUIDs){
            if(el.getUniversalNumber().equalsIgnoreCase(temp) && el.getId() != id){
                doublon = true;
            }
        }
        if(!doublon){
            UniversalId universalIdToUpdate = findById(id);
            try{
                universalIdToUpdate.setUniversalNumber(universalId.getUniversalNumber());
                universalIdToUpdate.setExpiryDate(universalId.getExpiryDate());
                universalIdToUpdate.setBirthDate(universalId.getBirthDate());
                universalIdToUpdate.setPlaceOfIssue(universalId.getPlaceOfIssue());
                universalIDRepository.save(universalIdToUpdate);
                return true;
            }catch (Exception e){
                return false;
            }
        }else{
            return false;
        }

    }

    @Override
    public boolean delete(Long id) {
        return false;
    }


}
