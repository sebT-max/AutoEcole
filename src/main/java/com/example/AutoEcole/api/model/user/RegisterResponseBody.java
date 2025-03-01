package com.example.AutoEcole.api.model.user;

import com.example.AutoEcole.dal.domain.entity.Role;
import com.example.AutoEcole.dal.domain.entity.User;
import lombok.Data;

@Data
public class RegisterResponseBody {
    Long id;
    String lastname;
    String firstname;
    String email;
    Role role;

    public RegisterResponseBody(Long id, String lastname, String firstname, String email, Role role){
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.role = role;
    }

    public static RegisterResponseBody fromEntity(User user){
        return new RegisterResponseBody(
                user.getId(),
                user.getLastname(),
                user.getFirstname(),
                user.getEmail(),
                user.getRole()
        );
    }

}


