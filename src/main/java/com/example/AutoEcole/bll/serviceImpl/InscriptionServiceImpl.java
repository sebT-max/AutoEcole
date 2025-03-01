package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.api.model.Booking.CreateInscriptionRequestBody;
import com.example.AutoEcole.api.model.Booking.CreateInscriptionResponseBody;
import com.example.AutoEcole.bll.exception.access.AccessDeniedException;
import com.example.AutoEcole.bll.service.BookingService;
import com.example.AutoEcole.dal.domain.entity.Booking;
import com.example.AutoEcole.dal.domain.entity.Journey;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.dal.repository.BookingRepository;
import com.example.AutoEcole.dal.repository.JourneyRepository;
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
public class InscriptionServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final JourneyRepository journeyRepository;

    @Override
    public CreateInscriptionResponseBody createBooking(CreateInscriptionRequestBody request) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Récupérer le voyage via son ID
        Journey journey = journeyRepository.findById(request.journeyId())
                .orElseThrow(() -> new RuntimeException("Voyage non trouvé"));


        if (journey.getCapacity() < request.nbrPerson()) {
            // Calculer la capacité restante et inclure dans le message d'erreur
            int remainingCapacity = journey.getCapacity();
            throw new RuntimeException("Capacité insuffisante. Il reste " + remainingCapacity + " places disponibles.");
        }

        // Créer la réservation
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setJourney(journey);
        booking.setDate(request.date());
        booking.setNbrPerson(request.nbrPerson());

        // Sauvegarder la réservation
        bookingRepository.save(booking);

        // Mettre à jour la capacité du voyage
        journey.setCapacity(journey.getCapacity() - request.nbrPerson());
        journeyRepository.save(journey);

        // Retourner la réponse
        return new CreateInscriptionResponseBody(
                "Réservation effectuée avec succès !",
                booking.getId(),
                booking.getJourney().getDestination(),
                booking.getDate(),
                booking.getNbrPerson()
        );
    }

    @Override
    public List<Booking> getAllBooking() {
        // Récupération de l'utilisateur authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Recherche de l'utilisateur en base
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérification si il s'agit d'un opérateur
        boolean isOperator = user.getRole().getName().equals("OPERATOR") || user.getRole().getName().equals("PASSENGER");

        if (!isOperator) {
            throw new AccessDeniedException("Accès refusé : vous n'avez pas les permissions nécessaires.");
        }

        // Si l'utilisateur est ADMIN ou TECHNICIEN, il peut voir tous les tickets
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> getBookingByUserId(Long userId){
        List<Booking> bookings = bookingRepository.findByUserIdWithDetails(userId);
        if (bookings.isEmpty()) {
            throw new RuntimeException("Aucune réservation trouvée pour l'utilisateur avec l'ID : " + userId);
        }
        return bookings;
    }


    @Override
    public Booking getBookingById(Long id){
        Booking bookingById = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvé"));

        return bookingById;
    }

    @Override
    @Transactional
    public boolean update(Long id, Booking booking) {
        Booking bookingToUpdate = getBookingById(id);
        if (bookingToUpdate == null) {
            throw new EntityNotFoundException("Réservation avec l'ID " + id + " non trouvée");
        }
        try {
            bookingToUpdate.setUser(bookingToUpdate.getUser());
            // Mettre à jour les champs nécessaires
            bookingToUpdate.setJourney(booking.getJourney());
            bookingToUpdate.setDate(booking.getDate());
            bookingToUpdate.setNbrPerson(booking.getNbrPerson());

            // Sauvegarder la réservation mise à jour
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la réservation", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;  // Booking not found
    }

//    public Optional<Booking> getBookingWithDetails(Long id) {
//        return bookingRepository.findByIdWithDetails(id);
//    }

}
