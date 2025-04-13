package com.example.AutoEcole.dal.domain.entity;

import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Entreprise extends User {

    @Column()
    private String name;

    @OneToMany(mappedBy = "entreprise")
    private List<Particulier> employees;


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
