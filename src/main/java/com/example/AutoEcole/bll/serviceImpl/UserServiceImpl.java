package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.Exception.ressourceNotFound.RessourceNotFoundException;
import com.example.AutoEcole.api.model.Entreprise.EmployeeInscriptionForm;
import com.example.AutoEcole.bll.service.UserService;
import com.example.AutoEcole.dal.domain.entity.Particulier;
import com.example.AutoEcole.dal.domain.entity.PrivateLink;
import com.example.AutoEcole.dal.domain.entity.Role;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.dal.repository.PrivateLinkRepository;
import com.example.AutoEcole.dal.repository.RoleRepository;
import com.example.AutoEcole.dal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PrivateLinkRepository privateLinkRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }


    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur avec cette adresse email: " + email));

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("Mot de passe incorrect");
        }
        return user;
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
/*
    @Override
    public boolean update(Long id, User user) {
        User userToUpdate = findById(id);
        try{
            userToUpdate.setLastname(user.getLastname());
            userToUpdate.setFirstname(user.getFirstname());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setRole(user.getRole());
            userRepository.save(userToUpdate);
            return true;
        }catch(Exception e){
            return false;
        }
    }

 */
    @Override
    public Long getUserIdByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(User::getId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé : " + username));
    }

    @Override
    public boolean delete(Long id) {
        //NOT IMPLEMENTED YET
        return false;
    }

    @Override
    public void registerEmployeeViaPrivateLink(EmployeeInscriptionForm form, PrivateLink link) {
        // Vérifier l'unicité de l'e-mail
        if (userRepository.findByEmail(form.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email déjà utilisé");
        }
// Vérification si le lien a déjà été utilisé ou expiré
        if (!link.isActive()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lien expiré ou déjà utilisé");
        }

        if (link.getExpirationDate() != null && link.getExpirationDate().isBefore(LocalDateTime.now())) {
            link.setActive(false);  // Le lien est expiré, on le marque comme inactif
            privateLinkRepository.save(link);
            throw new ResponseStatusException(HttpStatus.GONE, "Lien expiré");
        }
        // Créer un nouvel utilisateur (Particulier)
        Particulier employee = new Particulier();
        employee.setEmail(form.email());
        employee.setFirstname(form.firstname());
        employee.setLastname(form.lastName());
        employee.setPassword(passwordEncoder.encode(form.password()));

        // Associer l'entreprise
        employee.setEntreprise(link.getEntreprise());

        // Associer le stage concerné par le lien privé
        employee.setStage(link.getStage());

        // Affecter le rôle "particulier" ou "employe" selon ta stratégie
        Role role = roleRepository.findRoleByName("PARTICULIER") // ou "employe"
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Rôle introuvable"));
        employee.setRole(role);

        userRepository.save(employee);
    }


}

