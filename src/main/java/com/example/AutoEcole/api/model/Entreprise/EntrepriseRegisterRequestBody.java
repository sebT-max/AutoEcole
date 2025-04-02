package com.example.AutoEcole.api.model.Entreprise;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.Role;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Contract;

public record EntrepriseRegisterRequestBody(
        @NotBlank(message = "name can not be empty")
        String name,
        @NotBlank(message = "email can not be empty")
        @Email(message = "please type a correct email")
        String email,
        @NotBlank(message = "The password can not be empty")
        String password,
        @NotBlank(message = "The telephone can not be empty")
        String telephone,
        @AssertTrue(message = "Vous devez accepter les conditions")
        boolean acceptTerms,
        @NotNull(message = "Role Id must not be null")
        Long roleId
) {
    @Contract
    @NotNull
    public Entreprise toEntity() {
        Entreprise entreprise = new Entreprise();
        entreprise.setName(name);
        entreprise.setEmail(email);
        entreprise.setPassword(password);
        entreprise.setTelephone(telephone);
        entreprise.setAcceptTerms(acceptTerms);
        return entreprise;
    }
}

