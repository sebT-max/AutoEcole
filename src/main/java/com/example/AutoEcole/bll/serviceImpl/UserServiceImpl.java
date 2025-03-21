package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.api.model.user.RegisterRequestBody;
import com.example.AutoEcole.bll.exception.alreadyExist.AlreadyExistException;
import com.example.AutoEcole.bll.exception.ressourceNotFound.RessourceNotFoundException;
import com.example.AutoEcole.bll.service.UserService;
import com.example.AutoEcole.dal.domain.entity.Role;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.dal.repository.RoleRepository;
import com.example.AutoEcole.dal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException(username));
//    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

//    @Override
//    public User login(String email, String password) {
//        User user = userRepository.findByUsername(email)
//                .orElseThrow(() -> new UsernameNotFoundException(email));
//
//        if(!passwordEncoder.matches(password, user.getPassword())){
//            throw new BadCredentialsException("Wrong password");
//        }
//        return user;
//    }
    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("Wrong password");
        }
        return user;
    }

    @Override
    public Long register(User user) {
        if(userRepository.existUserByEmail(user.getEmail())){
            throw new AlreadyExistException("The email : " + user.getEmail() + " already exist!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user).getId();
    }
    public User register(RegisterRequestBody requestBody) {
        // Vérifier que l'utilisateur accepte les conditions d'utilisation
        if (!requestBody.acceptTerms()) {
            throw new IllegalArgumentException("You must accept the terms and conditions to register.");
        }

        // Vérifier si l'email est déjà utilisé
        if (userRepository.existUserByEmail(requestBody.email())) {
            throw new IllegalArgumentException("Email already in use.");
        }

        // Récupérer le rôle depuis la base
        Role role = roleRepository.findRoleByName(requestBody.role().getName())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Créer l’entité utilisateur
        User newUser = requestBody.toEntity(role, null);

        // Hasher le mot de passe (si tu utilises Spring Security)
        newUser.setPassword(passwordEncoder.encode(requestBody.password()));

        // Sauvegarder l'utilisateur
        return userRepository.save(newUser);
    }


    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RessourceNotFoundException("This " + id + " user not exist!")
        );
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean update(Long id, User user) {
        User userToUpdate = findById(id);
        try{
            userToUpdate.setUniversalId(user.getUniversalId());
            userToUpdate.setLastname(user.getLastname());
            userToUpdate.setFirstname(user.getFirstname());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setRole(user.getRole());
            userToUpdate.setBloodType(user.getBloodType());
            userRepository.save(userToUpdate);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        //NOT IMPLEMENTED YET
        return false;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
//    }
//
//    @Override
//    public String getPassword() {
//        return "";
//    }
//
//    @Override
//    public String getUsername() {
//        return "";
//    }

}

