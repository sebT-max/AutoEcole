package com.example.AutoEcole.il.util;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.Role;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.dal.domain.enum_.Gender;
import com.example.AutoEcole.dal.repository.EntrepriseRepository;
import com.example.AutoEcole.dal.repository.RoleRepository;
import com.example.AutoEcole.dal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EntrepriseRepository entrepriseRepository;


    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count() == 0){
            Role roleClient = new Role();
            roleClient.setName("CLIENT");
            roleClient.setDescription("Il s'agit du particulier qui doit suivre un stage");
            roleRepository.save(roleClient);

            Role roleAdmin = new Role();
            roleAdmin.setName("ADMIN");
            roleAdmin.setDescription("Il s'agit d'un membre admin d'ACF");
            roleRepository.save(roleAdmin);

            Role roleCompany = new Role();
            roleCompany.setName("roleCompany");
            roleCompany.setDescription("Il s'agit d'un employé d'une entreprise qui doit récupérer des points de permis");
            roleRepository.save(roleCompany);
            //test

            User client01 = new User();
            client01.setLastname("De Brive Gaillarde");
            client01.setFirstname("Jean-Charles Edouard");
            client01.setEmail("jce.dbg@aristo.org");
            client01.setPassword(passwordEncoder.encode("Chichis1234="));
            client01.setTelephone("0455/22.22.22");
            client01.setGender(Gender.MALE);
            client01.setBirthdate(LocalDateTime.of(1985, 5, 20, 0, 0)); // Ex: 20 mai 1985
            client01.setRole(roleClient);
            client01.setAcceptTerms(true);
            userRepository.save(client01);

            User admin01 = new User();
            admin01.setLastname("Vassiliniei");
            admin01.setFirstname("Petrov");
            admin01.setEmail("vass.pet@operator.org");
            admin01.setPassword(passwordEncoder.encode("Cccp1917"));
            admin01.setTelephone("0455/22.22.22");
            admin01.setGender(Gender.MALE);
            admin01.setBirthdate(LocalDateTime.of(1986, 5, 2, 0, 0)); // Ex: 20 mai 1985
            admin01.setRole(roleAdmin);
            admin01.setAcceptTerms(true);
            userRepository.save(admin01);

            Entreprise companyClient1 = new Entreprise();
            companyClient1.setName("MacDonalds");
            companyClient1.setEmail("hilde.dupont@snob.org");
            companyClient1.setPassword(passwordEncoder.encode("Test1234="));
            companyClient1.setTelephone("0455/22.22.22");
            companyClient1.setRole(roleCompany);
            companyClient1.setAcceptTerms(true);
            entrepriseRepository.save(companyClient1);

        }
    }
}
