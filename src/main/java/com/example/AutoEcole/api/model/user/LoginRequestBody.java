package com.example.AutoEcole.api.model.user;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestBody(
        @NotBlank(message = "The email must not be blank")
        String email,
        @NotBlank(message = "The password must not be blank")
        String password
) {

}
