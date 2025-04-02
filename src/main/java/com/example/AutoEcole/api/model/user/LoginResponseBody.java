package com.example.AutoEcole.api.model.user;
import com.example.AutoEcole.dal.domain.entity.Role;
import com.example.AutoEcole.dal.domain.entity.User;
import lombok.Data;

@Data
public class LoginResponseBody {
    private Long id;
    private String email;
    private String token;
    private Role role;

    public LoginResponseBody(Long id,
                             String email, Role role){
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public static LoginResponseBody fromEntity(User user){
        return new LoginResponseBody(user.getId(), user.getEmail(), user.getRole());
    }

}
