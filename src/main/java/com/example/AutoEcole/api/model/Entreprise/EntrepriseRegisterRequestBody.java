package com.example.AutoEcole.api.model.Entreprise;

import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.Role;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.List;

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

    @NotNull(message = "Role must not be null")  // Vérifie que le rôle est présent
    Role role,

    @AssertTrue(message = "Vous devez accepter les conditions")
    boolean acceptTerms

) {

        public Entreprise toEntity() {
            return new Entreprise(name, email, password,telephone,role,acceptTerms) {
                /**
                 * Returns the authorities granted to the user. Cannot return <code>null</code>.
                 *
                 * @return the authorities, sorted by natural key (never <code>null</code>)

                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return List.of();
                }
                */
            };
        }
    }
