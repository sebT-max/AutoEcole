package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.bll.exception.alreadyExist.AlreadyExistException;
import com.example.AutoEcole.bll.exception.ressourceNotFound.RessourceNotFoundException;
import com.example.AutoEcole.bll.service.UserService;
import com.example.AutoEcole.dal.domain.entity.User;
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

