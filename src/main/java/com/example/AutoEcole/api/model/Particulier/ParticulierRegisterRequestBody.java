package com.example.AutoEcole.api.model.Particulier;

import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.Particulier;
import com.example.AutoEcole.dal.domain.entity.Role;
import com.example.AutoEcole.dal.domain.entity.User;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Contract;

import java.time.LocalDate;

public record ParticulierRegisterRequestBody(
        @NotBlank(message = "lastname can not be empty")
        String lastname,

        @NotBlank(message = "firstname can not be empty")
        String firstname,

        @NotBlank(message = "email can not be empty")
        @Email(message = "please type a correct email")
        String email,

        @NotBlank(message = "The password can not be empty")
        String password,

        @NotBlank(message = "The telephone can not be empty")
        String telephone,

        @NotNull(message = "The birthdate can not be empty")
        LocalDate birthdate,

        @AssertTrue(message = "Vous devez accepter les conditions")
        boolean acceptTerms,

        @NotNull(message = "Role must not be null")  // Vérifie que le rôle est présent
        Long roleId

) {
    public Particulier toEntity() {
        Particulier particulier = new Particulier();
        particulier.setLastname(lastname);
        particulier.setFirstname(firstname);
        particulier.setEmail(email);
        particulier.setPassword(password);
        particulier.setTelephone(telephone);
        particulier.setBirthdate(birthdate);
        particulier.setAcceptTerms(acceptTerms);
        return particulier;
    }
}
