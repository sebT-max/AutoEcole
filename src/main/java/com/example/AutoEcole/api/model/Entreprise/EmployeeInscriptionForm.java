package com.example.AutoEcole.api.model.Entreprise;

import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.Particulier;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record EmployeeInscriptionForm(

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
Long roleId,

@NotNull(message = "Entreprise must not be null")  // Vérifie que le rôle est présent
Entreprise entreprise

) {
    public void setPassword(String hashedPassword) {
    }

    public CharSequence getPassword() {
        return null;
    }
}