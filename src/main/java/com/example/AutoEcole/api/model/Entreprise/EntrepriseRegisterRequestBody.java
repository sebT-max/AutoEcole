package com.example.AutoEcole.api.model.Entreprise;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.Role;
import jakarta.validation.constraints.*;
import org.springframework.lang.Contract;

public record EntrepriseRegisterRequestBody(
        @NotBlank(message = "name can not be empty")
        String name,

        @NotBlank(message = "email can not be empty")
        @Email(message = "please type a correct email")
        String email,

        @NotBlank(message = "The password can not be empty")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password,

        @NotBlank(message = "Numéro de téléphone requis")
        String telephone,

        @AssertTrue(message = "Vous devez accepter les conditions")
        boolean acceptTerms,

        @NotNull(message = "Role Id must not be null")
        Long roleId
) {
    @Contract
    @NotNull
    public Entreprise toEntity(Role role) {
        Entreprise entreprise = new Entreprise();
        entreprise.setName(name);
        entreprise.setEmail(email);
        entreprise.setPassword(password);
        entreprise.setTelephone(telephone);
        entreprise.setAcceptTerms(acceptTerms);
        entreprise.setRole(role);
        return entreprise;
    }
}

