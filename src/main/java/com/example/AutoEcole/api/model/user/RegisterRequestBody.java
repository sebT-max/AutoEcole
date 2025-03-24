package com.example.AutoEcole.api.model.user;

import com.example.AutoEcole.dal.domain.entity.Role;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.dal.domain.enum_.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.AssertTrue;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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

        @NotBlank(message = "The telephone can not be empty")
        String telephone,

        @NotBlank(message = "The gender can not be empty")
        Gender gender,

        @NotBlank(message = "The birthdate can not be empty")
        LocalDate birhtdate,

        @NotNull(message = "Role must not be null")  // Vérifie que le rôle est présent
        Role role,

        @AssertTrue(message = "Vous devez accepter les conditions")
        boolean acceptTerms

) {

        public User toEntity() {
                return new User(lastname, firstname, email, password,telephone,gender, LocalDateTime.from(birhtdate),role,acceptTerms) {
                        /**
                         * Returns the authorities granted to the user. Cannot return <code>null</code>.
                         *
                         * @return the authorities, sorted by natural key (never <code>null</code>)
                         */
                        @Override
                        public Collection<? extends GrantedAuthority> getAuthorities() {
                                return List.of();
                        }
                };
        }
}
