package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.api.model.Inscription.CreateInscriptionRequestBody;
import com.example.AutoEcole.api.model.Inscription.CreateInscriptionResponseBody;
import com.example.AutoEcole.bll.exception.access.AccessDeniedException;
import com.example.AutoEcole.bll.service.InscriptionService;
import com.example.AutoEcole.dal.domain.entity.Inscription;
import com.example.AutoEcole.dal.domain.entity.Stage;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.dal.repository.InscriptionRepository;
import com.example.AutoEcole.dal.repository.StageRepository;
import com.example.AutoEcole.dal.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class InscriptionServiceImpl implements InscriptionService {

    private final UserRepository userRepository;
    private final InscriptionRepository inscriptionRepository;
    private final StageRepository stageRepository;

    @Override
    public CreateInscriptionResponseBody createBooking(CreateInscriptionRequestBody request) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Récupérer le stage via son ID
        Stage stage = stageRepository.findById(request.stageId())
                .orElseThrow(() -> new RuntimeException("Voyage non trouvé"));


        if (stage.getCapacity() < request.nbrPerson()) {
            // Calculer la capacité restante et inclure dans le message d'erreur
            int remainingCapacity = stage.getCapacity();
            throw new RuntimeException("Capacité insuffisante. Il reste " + remainingCapacity + " places disponibles.");
        }

        // Créer l'inscription
        Inscription inscription = new Inscription();
        inscription.setUser(user);
        inscription.setStage(stage);inscription.setDateOfInscription(request.dateOfInscription());

        // Sauvegarder la réservation
        inscriptionRepository.save(inscription);

        // Mettre à jour la capacité du voyage
        stage.setCapacity(stage.getCapacity() - request.nbrPerson());
        stageRepository.save(stage);

        // Retourner la réponse
        return new CreateInscriptionResponseBody(
                "Réservation effectuée avec succès !",
                inscription.getId(),
                inscription.getStage(),
                inscription.getDateOfInscription(),
                inscription.getNbrPerson()
        );
    }

    @Override
    public List<Inscription> getAllInscriptions() {
        // Récupération de l'utilisateur authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Recherche de l'utilisateur en base
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérification si il s'agit d'un opérateur
        boolean isAdmin = user.getRole().getName().equals("ADMIN") || user.getRole().getName().equals("CLIENT");

        if (!isAdmin) {
            throw new AccessDeniedException("Accès refusé : vous n'avez pas les permissions nécessaires.");
        }

        // Si l'utilisateur est ADMIN ou TECHNICIEN, il peut voir tous les tickets
        return inscriptionRepository.findAll();
    }

    @Override
    public List<Inscription> getInscriptionsByUserId(Long userId){
        List<Inscription> bookings = inscriptionRepository.findByUserIdWithDetails(userId);
        if (bookings.isEmpty()) {
            throw new RuntimeException("Aucune réservation trouvée pour l'utilisateur avec l'ID : " + userId);
        }
        return bookings;
    }


    @Override
    public Inscription getBookingById(Long id){
        Inscription inscriptionById = inscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvé"));

        return inscriptionById;
    }

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
