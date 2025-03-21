package com.example.AutoEcole.dal.domain.entity;

import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "users")
public class User extends BaseEntity<Long> implements UserDetails {

    @Getter
    @Setter
    @Column(name = "LASTNAME", nullable = false, length = 50)
    private String lastname;

    @Getter
    @Setter
    @Column(name = "FIRSTNAME", nullable = false, length = 50)
    private String firstname;

    @Getter @Setter
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Setter
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Setter
    @Column(name = "TELEPHONE", nullable = false)
    private String telephone;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private Role role;

    private boolean acceptTerms;

    public User(String lastname, String firstname, String email, String password,Role role){
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String lastname, String firstname, String email, com.example.AutoEcole.dal.domain.entity.Role role){
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.getName()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }

//    public Role getRole(){
//        return role;
//    }

    @Override
    public String getUsername() {
        return email;
    }

}
