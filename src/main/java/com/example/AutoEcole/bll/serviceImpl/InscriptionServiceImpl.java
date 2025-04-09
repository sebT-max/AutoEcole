package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.Exception.AccessDeniedException.AccessDeniedException;
import com.example.AutoEcole.Exception.StageNotFoundException.StageNotFoundException;
import com.example.AutoEcole.Exception.UserNotFound.UserNotFoundException;
import com.example.AutoEcole.api.model.Inscription.CreateInscriptionRequestBody;
import com.example.AutoEcole.api.model.Inscription.CreateInscriptionResponseBody;
import com.example.AutoEcole.bll.service.FileService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InscriptionServiceImpl implements InscriptionService {

    private final UserRepository userRepository;
    private final InscriptionRepository inscriptionRepository;
    private final StageRepository stageRepository;
    private final CodePromoRepository codePromoRepository;
    private final FileService fileService;

    @Override
    public CreateInscriptionResponseBody createInscription(CreateInscriptionRequestBody request, MultipartFile fileName) {
        // Récupérer le principal de Spring Security (utilisateur authentifié)
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Vérifier si le principal est une instance de User ou de ses sous-classes concrètes
        User user;
        if (principal instanceof User) {
            user = (User) principal;  // Cast en toute sécurité si c'est un User
        } else {
            throw new UserNotFoundException("User not authenticated");
        }

        // Vérifier que le stage existe
        assert request.stageId() != null;
        Stage stage = stageRepository.findById(request.stageId())
                .orElseThrow(() -> new StageNotFoundException("Stage non trouvé"));

        // Créer l'inscription
        Inscription inscription = new Inscription();
        inscription.setUser(user);  // Assigner l'utilisateur récupéré
        inscription.setStage(stage);  // Assigner le stage
        inscription.setStageType(request.stageType());
        inscription.setInscriptionStatut(InscriptionStatut.EN_ATTENTE);

        // Sauvegarder le fichier et son nom
        if (fileName != null && !fileName.isEmpty()) {
            try {
                if (!Objects.equals(fileName.getContentType(), "application/pdf")) {
                    throw new IOException("Le fichier doit être un PDF");
                }
                String fileStoredName = fileService.saveFile(fileName);  // Sauvegarde du fichier et récupération du nom
                inscription.setLettrePdf(fileStoredName);  // Sauvegarder le nom du fichier
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors du téléchargement du fichier PDF", e);
            }
        }

        // Sauvegarder l'inscription dans la base de données
        inscriptionRepository.save(inscription);

        // Retourner la réponse avec les informations de l'inscription
        return new CreateInscriptionResponseBody(
                "Réservation effectuée avec succès !",
                inscription.getId(),
                inscription.getUser().getId(),
                inscription.getStage().getId(),
                inscription.getStageType(),
                inscription.getInscriptionStatut()
        );
    }


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
    @Override
    public List<Inscription> getInscriptionsByUserId(Long userId){
        List<Inscription> inscriptions = inscriptionRepository.findByUserIdWithDetails(userId);
        if (inscriptions.isEmpty()) {
            throw new RuntimeException("Aucune réservation trouvée pour l'utilisateur avec l'ID : " + userId);
        }
        return inscriptions;
    }

    private byte[] getPictureBytes(MultipartFile pictureFile) {
        try {
            return pictureFile != null ? pictureFile.getBytes() : null;
        } catch (IOException e) {
            throw new RuntimeException("Error while processing picture file", e);
        }
    }

    @Override
    public List<Inscription> getAllInscriptions() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Récupération de l'utilisateur authentifié
        if (user == null) {
            throw new UserNotFoundException("User not authenticated");
        }

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

    @Override
    public boolean delete(Long id) {
        Optional<Inscription> booking = inscriptionRepository.findById(id);
        if (booking.isPresent()) {
            inscriptionRepository.deleteById(id);
            return true;
        }
        return false;  // Booking not found
    }
    @Override
    public Inscription validateInscriptionById(Long id) {
        Inscription inscription = inscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));

        // Met à jour le statut
        inscription.setInscriptionStatut(InscriptionStatut.CONFIRME); // ou autre enum selon ton projet

        // Sauvegarde en BDD
        return inscriptionRepository.save(inscription);
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

//    public Optional<Booking> getBookingWithDetails(Long id) {
//        return bookingRepository.findByIdWithDetails(id);
//    }

}

 */
