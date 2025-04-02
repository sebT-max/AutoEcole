package com.example.AutoEcole.dal.domain.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Particulier extends User {

    @Column(name = "LASTNAME", nullable = false, length = 50)
    private String lastname;

    @Column(name = "FIRSTNAME", nullable = false, length = 50)
    private String firstname;

    @Column(name ="birthdate",nullable = false)
    private LocalDate birthdate;


    //@Column(nullable = false)
    //private String email;

    //@Column(nullable = false)
    //private String password;

//    @Column(nullable = false)
//    private String telephone;



/*
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

 */
}

