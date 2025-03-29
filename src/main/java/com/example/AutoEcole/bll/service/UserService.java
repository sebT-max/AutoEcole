package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.api.model.user.RegisterRequestBody;
import com.example.AutoEcole.dal.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User login(String email, String password);

    Long register(RegisterRequestBody requestBody);

    User findById(Long id);
    List<User> findAll();
    /*
    boolean update(Long id, User user);

     */
    public Long getUserIdByUsername(String username);
    boolean delete(Long id);

}
