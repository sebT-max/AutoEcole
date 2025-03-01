package com.example.AutoEcole.bll.serviceimpl;

import com.example.AutoEcole.bll.exception.alreadyExist.AlreadyExistException;
import com.example.AutoEcole.bll.service.RoleService;
import com.example.AutoEcole.dal.domain.entity.Role;
import com.example.AutoEcole.dal.repository.RoleRepository;
import lombok.NoArgsConstructor;
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
        if(!roleRepository.existRoleByName(name)){
            throw new RessourceNotFoundException("This role does not exist");
        }else{
            return roleRepository.findRoleByName(name);
        }
    }

}
