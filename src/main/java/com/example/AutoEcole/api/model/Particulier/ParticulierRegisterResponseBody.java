package com.example.AutoEcole.api.model.Particulier;

import com.example.AutoEcole.dal.domain.entity.Particulier;
import com.example.AutoEcole.dal.domain.entity.Role;
import lombok.Data;

@Data
public class ParticulierRegisterResponseBody {
    Long id;
    String lastname;
    String firstname;
    String email;
    Role role;

    public ParticulierRegisterResponseBody(Long id, String lastname, String firstname, String email, Role role){
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.role = role;
    }

    public static ParticulierRegisterResponseBody fromEntity(Particulier particulier, String token){
        return new ParticulierRegisterResponseBody(
                particulier.getId(),
                particulier.getLastname(),
                particulier.getFirstname(),
                particulier.getEmail(),
                particulier.getRole()
        );
    }

}


