package com.example.AutoEcole.api.model.user;

import com.example.AutoEcole.dal.domain.entity.Role;
import com.example.AutoEcole.dal.domain.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.AssertTrue;

public record RegisterRequestBody(

        @NotBlank(message = "lastname can not be empty")
        String lastname,

        @NotBlank(message = "firstname can not be empty")
        String firstname,

        @NotBlank(message = "email can not be empty")
        @Email(message = "please type a correct email")
        String email,

        @NotBlank(message = "The password can not be empty")
        String password,

        @NotNull(message = "Role must not be null")  // Vérifie que le rôle est présent
        Role role,

        @AssertTrue(message = "You must accept the terms of service") // Vérifie que c’est vrai
        boolean acceptTerms

) {

        public User toEntity(Role role, BloodType bloodType) {
                return new User(lastname, firstname, email, password, role);
        }
}
