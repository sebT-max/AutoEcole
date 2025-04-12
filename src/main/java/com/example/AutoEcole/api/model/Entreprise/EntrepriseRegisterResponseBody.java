package com.example.AutoEcole.api.model.Entreprise;

import com.example.AutoEcole.dal.domain.entity.Entreprise;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Contract;

public record EntrepriseRegisterResponseBody(
        Long id,
        String name,
        String email,
        String telephone,
        String token // facultatif, seulement si tu veux auto-connecter
) {
    public static EntrepriseRegisterResponseBody fromEntity(Entreprise entreprise, String token) {
        return new EntrepriseRegisterResponseBody(
                entreprise.getId(),
                entreprise.getName(),
                entreprise.getEmail(),
                entreprise.getTelephone(),
                token
        );
    }
}

