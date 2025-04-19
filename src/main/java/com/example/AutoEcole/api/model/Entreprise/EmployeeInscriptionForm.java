package com.example.AutoEcole.api.model.Entreprise;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmployeeInscriptionForm(
        @NotBlank(message = "Le prénom est obligatoire")
        String firstname,

        @NotBlank(message = "Le nom est obligatoire")
        String lastName,

        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "Email invalide")
        String email,

        @NotBlank(message = "Le mot de passe est obligatoire")
        String telephone

) {}

