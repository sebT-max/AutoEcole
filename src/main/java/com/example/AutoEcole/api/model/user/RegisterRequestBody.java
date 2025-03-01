package com.example.AutoEcole.api.model.user;

import com.example.AutoEcole.dal.domain.entity.Role;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.dal.domain.enum_.BloodType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

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
        // @ ANNOTATION TO INSERT HERE!!!!! LIKE NOT BLANK OR EMPTY????
        Role role,
        // @ ANNOTATION TO INSERT HERE!!!! LIKE NOT BLANK OR E_MPTY????
        BloodType bloodType
) {
        public User toEntity(Role role, BloodType bloodType){
                //
                return new User(lastname, firstname, email, password, role, bloodType);
        }
}

