package com.example.AutoEcole.api.model.user;

import com.example.AutoEcole.dal.domain.entity.Role;
import com.example.AutoEcole.dal.domain.entity.User;

import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserResponseBody {
    private Long id;
    private String lastname;
    private String firstname;
    private String email;
    private Role role;

    public UserResponseBody(Long id, String lastname, String firstname,
                            String email, Role role){
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.role = role;
    }

    public static UserResponseBody fromEntity(User user){
        return new UserResponseBody(
                user.getId(),
                user.getLastname(),
                user.getFirstname(),
                user.getEmail(),
                user.getRole()
                );
    }
}

