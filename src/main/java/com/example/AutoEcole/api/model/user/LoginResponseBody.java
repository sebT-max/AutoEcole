package com.example.AutoEcole.api.model.user;

import com.example.AutoEcole.dal.domain.entity.Role;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.dal.domain.enum_.BloodType;
import lombok.Data;

@Data
public class LoginResponseBody {
    private Long id;
    private String lastname;
    private String firstname;
    private String email;
    private String token;
    private Role role;
    private BloodType bloodType;

    public LoginResponseBody(Long id, String lastname, String firstname, String email,
                            Role role, BloodType bloodType){
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.role = role;
        this.bloodType = bloodType;
    }

    public static LoginResponseBody fromEntity(User user){
        return new LoginResponseBody(user.getId(), user.getLastname(), user.getFirstname(),
                user.getEmail(), user.getRole(), user.getBloodType());
    }

}
