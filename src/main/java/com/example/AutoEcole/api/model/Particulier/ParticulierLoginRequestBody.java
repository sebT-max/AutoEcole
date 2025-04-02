package com.example.AutoEcole.api.model.Particulier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ParticulierLoginRequestBody(
        @NotBlank(message = "email can not be empty") @Email(message = "please type a correct email") String email,
        @NotBlank(message = "The password can not be empty") String password
) {}
