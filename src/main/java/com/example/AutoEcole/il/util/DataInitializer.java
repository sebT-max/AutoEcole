package com.example.AutoEcole.il.util;
import com.example.AutoEcole.dal.domain.entity.Role;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.dal.domain.enum_.BloodType;
import com.example.AutoEcole.dal.repository.RoleRepository;
import com.example.AutoEcole.dal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count() == 0){
            Role rolePassenger = new Role();
            rolePassenger.setName("PASSENGER");
            rolePassenger.setDescription("Role for client");
            roleRepository.save(rolePassenger);

            Role roleOperator = new Role();
            roleOperator.setName("OPERATOR");
            roleOperator.setDescription("This role can manage the application");
            roleRepository.save(roleOperator);
            //test

            User passenger01 = new User();
            passenger01.setLastname("De Brive Gaillarde");
            passenger01.setFirstname("Jean-Charles Edouard");
            passenger01.setEmail("jce.dbg@aristo.org");
            passenger01.setBloodType(BloodType.AB_POSITIF);
            passenger01.setPassword(passwordEncoder.encode("Chichis1234="));
            passenger01.setRole(rolePassenger);
            userRepository.save(passenger01);

            User operator01 = new User();
            operator01.setLastname("Vassiliniei");
            operator01.setFirstname("Petrov");
            operator01.setEmail("vass.pet@operator.org");
            operator01.setBloodType(BloodType.O_NEGATIF);
            operator01.setPassword(passwordEncoder.encode("Cccp1917"));
            operator01.setRole(roleOperator);
            userRepository.save(operator01);

            User passenger02 = new User();
            passenger02.setLastname("Dupont");
            passenger02.setFirstname("Hilde");
            passenger02.setEmail("hilde.dupont@snob.org");
            passenger02.setBloodType(BloodType.O_POSITIF);
            passenger02.setPassword(passwordEncoder.encode("Test1234="));
            passenger02.setRole(rolePassenger);
            userRepository.save(passenger02);

        }
    }
}
