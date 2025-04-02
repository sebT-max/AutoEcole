package com.example.AutoEcole.api.model.Particulier;

import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.Particulier;
import com.example.AutoEcole.dal.domain.entity.Role;
import lombok.Data;

@Data
public class ParticulierLoginResponseBody {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String token;
    private Role role;

    public ParticulierLoginResponseBody(Long id, String firstname,String lastname, String email, Role role) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
    }

    public static ParticulierLoginResponseBody fromEntity(Particulier particulier) {
        return new ParticulierLoginResponseBody(
                particulier.getId(),
                particulier.getFirstname(),
                particulier.getLastname(),
                particulier.getEmail(),
                particulier.getRole()
        );
    }
}
