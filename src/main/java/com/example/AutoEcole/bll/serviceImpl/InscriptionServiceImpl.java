package com.example.AutoEcole.bll.serviceImpl;

import com.cloudinary.Cloudinary;
import com.example.AutoEcole.Exception.AccessDeniedException.AccessDeniedException;
import com.example.AutoEcole.Exception.StageNotFoundException.StageNotFoundException;
import com.example.AutoEcole.Exception.UserNotFound.UserNotFoundException;
import com.example.AutoEcole.api.model.Document.DocumentDTO;
import com.example.AutoEcole.api.model.Document.DocumentMapper;
import com.example.AutoEcole.api.model.Inscription.CreateInscriptionRequestBody;
import com.example.AutoEcole.api.model.Inscription.CreateInscriptionResponseBody;
import com.example.AutoEcole.api.model.Inscription.InscriptionListResponse;
import com.example.AutoEcole.api.model.Inscription.InscriptionMapper;
import com.example.AutoEcole.bll.service.CloudinaryService;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InscriptionServiceImpl implements InscriptionService {

    private final UserRepository userRepository;
    private final InscriptionRepository inscriptionRepository;
    private final StageRepository stageRepository;
    private final CodePromoRepository codePromoRepository;
    private final CloudinaryService cloudinaryService;
    private final DocumentRepository documentRepository; // Ajouter le repository des documents
    private final DocumentMapper documentMapper;
    private final InscriptionMapper inscriptionMapper;


    @Override
    public CreateInscriptionResponseBody createInscription(CreateInscriptionRequestBody request, List<MultipartFile> files) throws IOException {

        // Récupérer l'utilisateur authentifié depuis le contexte de sécurité
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user;
        if (principal instanceof User) {
            user = (User) principal;
        } else {
            throw new UserNotFoundException("User not authenticated");
        }

        // Vérifier l'existence du stage
        if (request.getStageId() == null) {
            throw new IllegalArgumentException("L'ID du stage ne peut pas être nul");
        }

        Stage stage = stageRepository.findById(request.getStageId())
                .orElseThrow(() -> new StageNotFoundException("Stage non trouvé"));

        // Créer une nouvelle inscription
        Inscription inscription = new Inscription();
        inscription.setUser(user);
        inscription.setStage(stage);
        inscription.setStageType(request.getStageType());
        inscription.setInscriptionStatut(InscriptionStatut.EN_ATTENTE);

        // Sauvegarde initiale pour obtenir l'ID (si nécessaire pour la relation)
        inscriptionRepository.save(inscription);

        // Préparer l'ensemble des documents
        Set<Document> documentSet = new HashSet<>();

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    try {
                        Map<String, String> uploadResult = cloudinaryService.upload(file);
                        String url = uploadResult.get("url");

                        // Validation si l'URL est valide (en général Cloudinary renverra une URL correcte si le fichier est bien uploadé)
                        if (url == null || url.isEmpty()) {
                            throw new IOException("L'URL du fichier est vide ou invalide.");
                        }

                        DocumentType documentType = determineDocumentType(file);

                        Document document = Document.builder()
                                .fileName(file.getOriginalFilename())
                                .fileUrl(url)  // URL récupérée de Cloudinary
                                .uploadedAt(LocalDateTime.now())
                                .user(user)
                                .inscription(inscription)
                                .type(documentType)
                                .build();

                        documentRepository.save(document);
                        documentSet.add(document);

                    } catch (IOException e) {
                        throw new RuntimeException("Erreur lors de l'enregistrement du fichier : " + file.getOriginalFilename(), e);
                    }
                }
            }
        }

        // Lier les documents à l'inscription et sauvegarder à nouveau si nécessaire
        if (!documentSet.isEmpty()) {
            inscription.setDocuments(documentSet);
            inscriptionRepository.save(inscription); // Optionnel si cascade OK
        }

        // Mapper les documents vers leurs DTOs et les convertir en List
        List<DocumentDTO> documentDtos = documentSet.stream()
                .map(documentMapper::toDto)
                .collect(Collectors.toList()); // Conversion en List

        return new CreateInscriptionResponseBody(
                inscription.getId(),
                user.getId(),
                stage.getId(),
                request.getStageType(),
                inscription.getInscriptionStatut(),
                documentDtos // 👈 ici, tu passes maintenant une List
        );
    }



    private static final Map<String, DocumentType> KEYWORD_TO_TYPE = Map.ofEntries(
            Map.entry("recto", DocumentType.PERMIS_RECTO),
            Map.entry("verso", DocumentType.PERMIS_VERSO),
            Map.entry("piece-identite-recto", DocumentType.PIECE_IDENTITE_RECTO),
            Map.entry("piece-identite-verso", DocumentType.PIECE_IDENTITE_VERSO),
            Map.entry("48n", DocumentType.LETTRE_48N)
    );

    private DocumentType determineDocumentType(MultipartFile file) {
        String fileName = Objects.requireNonNull(file.getOriginalFilename()).toLowerCase();

        return KEYWORD_TO_TYPE.entrySet().stream()
                .filter(entry -> fileName.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(DocumentType.PIECE_IDENTITE_RECTO); // Valeur par défaut
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
    public List<InscriptionListResponse> getAllInscriptions() {
        // Récupération de l'utilisateur authentifié
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user == null) {
            throw new UserNotFoundException("Utilisateur non authentifié");
        }

        // Vérifie si l'utilisateur est un administrateur
        boolean isAdmin = user.getRole().getName().equals("ADMIN");

        if (!isAdmin) {
            throw new AccessDeniedException("Accès refusé : vous n'avez pas les permissions nécessaires.");
        }

        // Récupère toutes les inscriptions et les mappe en DTOs
        return inscriptionRepository.findAll()
                .stream()
                .map(inscriptionMapper::toListResponse)
                .collect(Collectors.toList());
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
