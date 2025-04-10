package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.Exception.AccessDeniedException.AccessDeniedException;
import com.example.AutoEcole.Exception.StageNotFoundException.StageNotFoundException;
import com.example.AutoEcole.Exception.UserNotFound.UserNotFoundException;
import com.example.AutoEcole.api.model.Inscription.CreateInscriptionRequestBody;
import com.example.AutoEcole.api.model.Inscription.CreateInscriptionResponseBody;
import com.example.AutoEcole.bll.service.FileService;
import com.example.AutoEcole.bll.service.InscriptionService;
import com.example.AutoEcole.dal.domain.entity.Document;
import com.example.AutoEcole.dal.domain.entity.Inscription;
import com.example.AutoEcole.dal.domain.entity.Stage;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.dal.domain.enum_.DocumentType;
import com.example.AutoEcole.dal.domain.enum_.InscriptionStatut;
import com.example.AutoEcole.dal.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
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
    private final DocumentRepository documentRepository; // Ajouter le repository des documents

    @Override
    public CreateInscriptionResponseBody createInscription(CreateInscriptionRequestBody request, List<MultipartFile> files) throws IOException {

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
        inscription.setUser(user);
        inscription.setStage(stage);
        inscription.setStageType(request.stageType());
        inscription.setInscriptionStatut(InscriptionStatut.EN_ATTENTE);

        // Sauvegarder l'inscription avant de gérer les fichiers
        inscriptionRepository.save(inscription);

        // Traiter les fichiers uploadés
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    try {
                        // Enregistrer le fichier
                        String filename = fileService.saveFile(file);

                        // Déterminer le type du document basé sur le fichier
                        DocumentType documentType = determineDocumentType(file);

                        // Créer un document et l'associer à l'inscription
                        Document document = Document.builder()
                                .fileName(filename)
                                .filePath("uploads/" + filename) // Chemin où le fichier est stocké
                                .uploadedAt(LocalDateTime.now())
                                .user(user)
                                .inscription(inscription) // Lier le document à l'inscription
                                .type(documentType) // Déterminer le type (PERMIS_RECTO, PERMIS_VERSO, etc.)
                                .build();

                        // Sauvegarder le document
                        documentRepository.save(document);

                    } catch (IOException e) {
                        throw new RuntimeException("Erreur lors de l'enregistrement du fichier : " + file.getOriginalFilename(), e);
                    }
                }
            }
        }

        // Retourner la réponse avec les informations de l'inscription
        return new CreateInscriptionResponseBody(
                "Inscription enregistrée avec succès",
                inscription.getId(),
                user.getId(),
                stage.getId(),
                request.stageType(),
                inscription.getInscriptionStatut()
        );
    }

    private DocumentType determineDocumentType(MultipartFile file) {
        // Exemple de logique pour déterminer le type de document en fonction du nom du fichier
        String fileName = Objects.requireNonNull(file.getOriginalFilename()).toLowerCase();

        if (fileName.contains("recto")) {
            return DocumentType.PERMIS_RECTO;
        } else if (fileName.contains("verso")) {
            return DocumentType.PERMIS_VERSO;
        } else {
            return DocumentType.PIECE_IDENTITE; // Par défaut, c'est une pièce d'identité
        }
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
