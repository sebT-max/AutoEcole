package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.dal.domain.entity.Role;

public interface RoleService {
    Long create(Role role);
    Role findRoleByName(String name);
    Role findById(Long id);
}
