package com.example.AutoEcole.api.model.Entreprise;

import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.Role;
import lombok.Data;

@Data
public class EntrepriseLoginResponseBody {
    private Long id;
    private String name;
    private String email;
    private String token;
    private Role role;

    public EntrepriseLoginResponseBody(Long id, String name,
                                       String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static EntrepriseLoginResponseBody fromEntity(Entreprise entreprise) {
        return new EntrepriseLoginResponseBody(entreprise.getId(), entreprise.getName(),
                entreprise.getEmail());
    }
}