package com.example.AutoEcole.il.util;
import com.example.AutoEcole.dal.domain.entity.*;
import com.example.AutoEcole.dal.domain.enum_.Gender;
import com.example.AutoEcole.dal.domain.enum_.Month;
import com.example.AutoEcole.dal.domain.enum_.TwoDaysOfTheWeek;
import com.example.AutoEcole.dal.repository.*;
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
    private final StageRepository stageRepository;
    private final DemandeDevisRepository demandeDevisRepository;


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
            client01.setLastname("Depardieu");
            client01.setFirstname("Gérard");
            client01.setEmail("gegedepardieu@zozo.com");
            client01.setPassword(passwordEncoder.encode("123pignon"));
            client01.setTelephone("0455/22.22.22");
            client01.setBirthdate(LocalDate.of(1985, 5, 20)); // Ex: 20 mai 1985
            client01.setAcceptTerms(true);
            client01.setRole(roleParticulier);
            userRepository.save(client01);

            Particulier admin01 = new Particulier();
            admin01.setLastname("Palmade");
            admin01.setFirstname("Pierre");
            admin01.setEmail("pierrotlapalme@sevrage.org");
            admin01.setPassword(passwordEncoder.encode("ensevrage"));
            admin01.setTelephone("0455/22.22.22");
            admin01.setBirthdate(LocalDate.of(1986, 5, 2)); // Ex: 20 mai 1985
            admin01.setAcceptTerms(true);
            admin01.setRole(roleAdmin);
            userRepository.save(admin01);

            Entreprise companyClient1 = new Entreprise();
            companyClient1.setName("Jupiler");
            companyClient1.setEmail("contact@jupiler.be");
            companyClient1.setPassword(passwordEncoder.encode("123123123"));
            companyClient1.setTelephone("0455/22.22.22");
            companyClient1.setRole(roleCompany);
            companyClient1.setAcceptTerms(true);
            entrepriseRepository.save(companyClient1);


            Stage stage1 = new Stage();
            stage1.setDateDebut(LocalDate.of(2025, 5, 2));
            stage1.setDateFin(LocalDate.of(2025, 5, 3));
            stage1.setCity("Paris");
            stage1.setStreet("rue Doudeauville 14");
            stage1.setArrondissement("16ième");
            stage1.setCapacity(20);
            stage1.setPrice(260.00);
            stage1.setOrganisation("9h-12h || 13h-17h");
//            stage1.setTwoDaysOfTheWeek(TwoDaysOfTheWeek.LUNDI_MARDI);
//            stage1.setMonth(Month.JUNE);
            stageRepository.save(stage1);


            Stage stage2 = new Stage();
            stage2.setDateDebut(LocalDate.of(2025, 5, 5));
            stage2.setDateFin(LocalDate.of(2025, 5, 6));
            stage2.setCity("Bordeaux");
            stage2.setStreet("rue Beaudelaire 12");
            stage2.setArrondissement("16ième");
            stage2.setCapacity(20);
            stage2.setPrice(260.00);
            stage2.setOrganisation("9h-12h || 13h-17h");
//            stage2.setTwoDaysOfTheWeek(TwoDaysOfTheWeek.LUNDI_MARDI);
//            stage2.setMonth(Month.JUNE);
            stageRepository.save(stage2);

            DemandeDevis demandeDevis1= new DemandeDevis();
            demandeDevis1.setUser(companyClient1);
            demandeDevis1.setContactFirstName("Jean Pascal");
            demandeDevis1.setContactLastName("Vrebos");
            demandeDevis1.setNumberOfInterns(4);
            demandeDevis1.setMessage("Bonjour, je souhaite envoyer 4 de mes amployés chez vous à Quimper");
            demandeDevisRepository.save(demandeDevis1);
        }
    }
}
