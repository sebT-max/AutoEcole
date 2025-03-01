package com.example.AutoEcole.bll.service;

import com.example.AutoEcole.dal.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User login(String email, String password);
    Long register(User user);
    User findById(Long id);
    List<User> findAll();

}
