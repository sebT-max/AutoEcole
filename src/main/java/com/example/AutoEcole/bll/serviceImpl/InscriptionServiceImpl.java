package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.Exception.AccessDeniedException.AccessDeniedException;
import com.example.AutoEcole.Exception.StageNotFoundException.StageNotFoundException;
import com.example.AutoEcole.Exception.UserNotFound.UserNotFoundException;
import com.example.AutoEcole.api.model.Inscription.CreateInscriptionRequestBody;
import com.example.AutoEcole.api.model.Inscription.CreateInscriptionResponseBody;
import com.example.AutoEcole.bll.service.InscriptionService;
import com.example.AutoEcole.dal.domain.entity.Inscription;
import com.example.AutoEcole.dal.domain.entity.Stage;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.dal.domain.enum_.InscriptionStatut;
import com.example.AutoEcole.dal.repository.CodePromoRepository;
import com.example.AutoEcole.dal.repository.InscriptionRepository;
import com.example.AutoEcole.dal.repository.StageRepository;
import com.example.AutoEcole.dal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InscriptionServiceImpl implements InscriptionService {

    private final UserRepository userRepository;
    private final InscriptionRepository inscriptionRepository;
    private final StageRepository stageRepository;
    private final CodePromoRepository codePromoRepository;

//    @Override
//    public CreateInscriptionResponseBody createInscription(CreateInscriptionRequestBody request, String fileName) {
//
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (user == null) {
//            throw new UserNotFoundException("User not authenticated");
//        }
//
//        // Vérifier que le stage existe
//        assert request.stageId() != null;
//        Stage stage = stageRepository.findById(request.stageId())
//                .orElseThrow(() -> new StageNotFoundException("Stage non trouvé"));
//
//        /*
//        Vérifier si un code promo est appliqué
//        CodePromo codePromo = null;
//        if (request.codePromo() != null) {
//            codePromo = codePromoRepository.findByCode(request.codePromo().getCode()); // Recherche par code promo
//        }
//
//
//
//        // Si un code promo est trouvé, vérifier sa validité (expiration)
//
//        if (codePromo != null && codePromo.getExpiry_date().isAfter(LocalDate.now())) {
//            // Appliquer la réduction (si nécessaire)
//        } else if (codePromo != null) {
//            throw new RuntimeException("Le code promo est expiré.");
//        }
//
//         */
//
//        // Créer l'inscription
//        Inscription inscription = new Inscription();
//        inscription.setUser(user);
//        inscription.setStage(stage);
//        inscription.setStageType(request.stageType());
//        inscription.setInscriptionStatut(InscriptionStatut.EN_ATTENTE);
//        inscription.setLettrePdf(fileName); // 🔥 Correction ici : utiliser le bon fichier
//
//        // Sauvegarde en base
//        inscriptionRepository.save(inscription);
//
//        return new CreateInscriptionResponseBody(
//                "Réservation effectuée avec succès !",
//                inscription.getId(),
//                inscription.getUser().getId(),
//                inscription.getStage().getId(),
//                inscription.getStageType(),
//                inscription.getInscriptionStatut(),
//                inscription.getLettrePdf()
//        );
//    }

    @Override
    public CreateInscriptionResponseBody createInscription(CreateInscriptionRequestBody request, String fileName) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user == null) {
            throw new UserNotFoundException("User not authenticated");
        }

        // Vérifier que le stage existe
        Stage stage = stageRepository.findById(request.stageId())
                .orElseThrow(() -> new StageNotFoundException("Stage non trouvé"));

        /*
        Vérifier si un code promo est appliqué
        CodePromo codePromo = null;
        if (request.codePromo() != null) {
            codePromo = codePromoRepository.findByCode(request.codePromo().getCode()); // Recherche par code promo
        }



        // Si un code promo est trouvé, vérifier sa validité (expiration)

        if (codePromo != null && codePromo.getExpiry_date().isAfter(LocalDate.now())) {
            // Appliquer la réduction (si nécessaire)
        } else if (codePromo != null) {
            throw new RuntimeException("Le code promo est expiré.");
        }

         */

        // Créer l'inscription
        Inscription inscription = new Inscription();
        inscription.setUser(user);
        inscription.setStage(stage);
        inscription.setStageType(request.stageType());
        inscription.setInscriptionStatut(InscriptionStatut.EN_ATTENTE);
//        inscription.setLettrePdf(request.lettrePdf());

        //inscription.setCodePromo(codePromo);

        // Sauvegarder l'inscription
        inscriptionRepository.save(inscription);

        // Retourner la réponse
        return new CreateInscriptionResponseBody(
                "Réservation effectuée avec succès !",
                inscription.getId(),
                inscription.getUser().getId(),
                inscription.getStage().getId(),
                inscription.getStageType(),
                inscription.getInscriptionStatut(),
                inscription.getLettrePdf()

                // inscription.getCodePromo()
        );
    }
    @Override
    public List<Inscription> getInscriptionsByUserId(Long userId){
        List<Inscription> inscriptions = inscriptionRepository.findByUserIdWithDetails(userId);
        if (inscriptions.isEmpty()) {
            throw new RuntimeException("Aucune réservation trouvée pour l'utilisateur avec l'ID : " + userId);
        }
        return inscriptions;
    }


    @Override
    public List<Inscription> getAllInscriptions() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Récupération de l'utilisateur authentifié
        if (user == null) {
            throw new UserNotFoundException("User not authenticated");
        }


        // Recherche de l'utilisateur en base
        //User user = userRepository.findByEmail(email)
          //     .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérification si il s'agit d'un opérateur
        boolean isAdmin = user.getRole().getName().equals("ADMIN");

        if (!isAdmin) {
            throw new AccessDeniedException("Accès refusé : vous n'avez pas les permissions nécessaires.");
        }

        return inscriptionRepository.findAll();
    }





    @Override
    public Inscription getInscriptionById(Long id){
        Inscription inscriptionById = inscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvé"));

        return inscriptionById;
    }
}
    /*

    @Transactional
    @Override
    public boolean update(Long id, Inscription inscription) {
        Inscription inscriptionToUpdate = getBookingById(id);
        if (inscriptionToUpdate == null) {
            throw new EntityNotFoundException("Réservation avec l'ID " + id + " non trouvée");
        }
        try {
            inscriptionToUpdate.setUser(inscriptionToUpdate.getUser());
            // Mettre à jour les champs nécessaires
            inscriptionToUpdate.setStage(inscription.getStage());
            inscriptionToUpdate.setDateOfInscription(inscription.getDateOfInscription());
            inscriptionToUpdate.setNbrPerson(inscription.getNbrPerson());

            // Sauvegarder la réservation mise à jour
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la réservation", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        Optional<Inscription> booking = inscriptionRepository.findById(id);
        if (booking.isPresent()) {
            inscriptionRepository.deleteById(id);
            return true;
        }
        return false;  // Booking not found
    }

//    public Optional<Booking> getBookingWithDetails(Long id) {
//        return bookingRepository.findByIdWithDetails(id);
//    }

}

 */
