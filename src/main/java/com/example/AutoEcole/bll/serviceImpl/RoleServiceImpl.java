package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.Exception.alreadyExist.AlreadyExistException;
import com.example.AutoEcole.Exception.ressourceNotFound.RessourceNotFoundException;
import com.example.AutoEcole.bll.service.RoleService;
import com.example.AutoEcole.dal.domain.entity.Role;
import com.example.AutoEcole.dal.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;


    @Override
    public Long create(Role role) {
        if(roleRepository.existRoleByName(role.getName())){
            throw new AlreadyExistException("This Role already exist");
        }else{
            return roleRepository.save(role).getId();
        }
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name)
                .orElseThrow(() -> new RessourceNotFoundException("Ce role n'existe pas: " + name));
    }


    @Override
    public Role findById(Long id) {
        if(id == null){
            return null;
        }
        return roleRepository.findById(id).orElseThrow(
                () -> new RessourceNotFoundException("This id " + id + " do not exist")
        );
    }

}
