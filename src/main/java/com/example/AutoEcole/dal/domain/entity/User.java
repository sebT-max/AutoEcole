package com.example.AutoEcole.dal.domain.entity;
import com.example.AutoEcole.dal.domain.entity.base.BaseEntity;
import com.example.AutoEcole.dal.domain.enum_.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDateTime;
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

    @Column(name = "gender",nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * The birthdate of the {@code user}.
     */
    @Column(name ="birthdate",nullable = false)
    private LocalDateTime birthdate;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", nullable = false)
    private Role role;

    private boolean acceptTerms;

    public User() {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.gender = gender;
        this.birthdate = birthdate;
        this.role = role;
        this.acceptTerms = acceptTerms;
    }

    @Override

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.getName()));
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