package com.example.AutoEcole.dal.domain.entity;
import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;



@Entity
@AllArgsConstructor
@Getter @Setter
@Table(name = "users")

public class User extends BaseEntity<Long> implements UserDetails {

    @Column(name = "LASTNAME", nullable = false, length = 50)
    private String lastname;


    @Column(name = "FIRSTNAME", nullable = false, length = 50)
    private String firstname;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;


    @Column(name = "PASSWORD", nullable = false)
    private String password;


    @Column(name = "TELEPHONE", nullable = false)
    private String telephone;

    /**
     * The birthdate of the {@code user}.
     */
    @Column(name ="birthdate",nullable = false)
    private LocalDate birthdate;

    private boolean acceptTerms;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", nullable = false)
    private Role role;

    public User() {

    }
/*
    @Override

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.getName()));
    }

 */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Ajouter le préfixe ROLE_ à votre rôle, car Spring Security s'attend à ce format
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.getName()));
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password
     */


    @Override    public boolean isAccountNonExpired() {
        return true;
    }

    @Override    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override    public boolean isEnabled() {
        return true;
    }

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */


    @Override
    public String getUsername() {
        return email;

    }
    @Override
    public String getPassword() {
        return password;
    }
}