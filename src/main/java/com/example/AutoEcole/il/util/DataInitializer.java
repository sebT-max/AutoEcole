package com.example.AutoEcole.il.util;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.Particulier;
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
import java.time.LocalDate;


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
            Role roleParticulier = new Role();
            roleParticulier.setName("PARTICULIER");
            roleParticulier.setDescription("Il s'agit du particulier qui doit suivre un stage");
            roleRepository.save(roleParticulier);

            Role roleAdmin = new Role();
            roleAdmin.setName("ADMIN");
            roleAdmin.setDescription("Il s'agit d'un membre admin d'ACF");
            roleRepository.save(roleAdmin);

            Role roleCompany = new Role();
            roleCompany.setName("ENTREPRISE");
            roleCompany.setDescription("Il s'agit d'une entreprise dont un employé doit/ou des employés doivent récupérer des points de permis");
            roleRepository.save(roleCompany);
            //test

            Particulier client01 = new Particulier();
            client01.setLastname("De Brive Gaillarde");
            client01.setFirstname("Jean-Charles Edouard");
            client01.setEmail("jce.dbg@aristo.org");
            client01.setPassword(passwordEncoder.encode("Chichis1234="));
            client01.setTelephone("0455/22.22.22");
            client01.setBirthdate(LocalDate.of(1985, 5, 20)); // Ex: 20 mai 1985
            client01.setAcceptTerms(true);
            client01.setRole(roleParticulier);
            userRepository.save(client01);

            Particulier admin01 = new Particulier();
            admin01.setLastname("Vassiliniei");
            admin01.setFirstname("Petrov");
            admin01.setEmail("vass.pet@operator.org");
            admin01.setPassword(passwordEncoder.encode("Cccp1917"));
            admin01.setTelephone("0455/22.22.22");
            admin01.setBirthdate(LocalDate.of(1986, 5, 2)); // Ex: 20 mai 1985
            admin01.setAcceptTerms(true);
            admin01.setRole(roleAdmin);
            userRepository.save(admin01);

            Entreprise companyClient1 = new Entreprise();
            companyClient1.setName("Nasa");
            companyClient1.setEmail("flavian.ovyn@nasa.org");
            companyClient1.setPassword(passwordEncoder.encode("Test1234="));
            companyClient1.setTelephone("0455/22.22.22");
            companyClient1.setRole(roleCompany);
            companyClient1.setAcceptTerms(true);
            entrepriseRepository.save(companyClient1);

        }
    }
}
