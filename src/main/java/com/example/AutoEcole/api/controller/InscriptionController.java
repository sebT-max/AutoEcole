package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.Booking.CreateInscriptionRequestBody;
import com.example.AutoEcole.api.model.Booking.CreateInscriptionResponseBody;
import com.example.AutoEcole.bll.service.BookingService;
import com.example.AutoEcole.bll.service.JourneyService;
import com.example.AutoEcole.bll.service.UserService;
import com.example.AutoEcole.dal.domain.entity.Booking;
import com.example.AutoEcole.dal.domain.entity.Journey;
import com.example.AutoEcole.dal.domain.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/V1/booking")

public class InscriptionController {
    private final BookingService bookingService;
    private final JourneyService journeyService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<CreateInscriptionResponseBody> createBooking(@RequestBody CreateInscriptionRequestBody request) {
        User user = userService.findById(request.userId());
        try {
            CreateInscriptionResponseBody response = bookingService.createBooking(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new CreateInscriptionResponseBody(e.getMessage()));
        }
    }
    @GetMapping("/all")
    @PreAuthorize("hasRole('OPERATOR')")
    public List<Booking> getAllBooking(){
        return bookingService.getAllBooking();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OPERATOR')")
    public Booking getBookingById(@PathVariable Long id){
        return bookingService.getBookingById(id);
    }

    @GetMapping("/users/{userId}")
    @PreAuthorize("hasRole('OPERATOR')")
    public List<Booking> getBookingByUserId(@PathVariable Long userId){
        return bookingService.getBookingByUserId(userId);
    }
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('OPERATOR')")
    public boolean deleteBooking(@PathVariable Long id){
        return bookingService.delete(id);
    }









    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> updateBooking(
            @PathVariable Long id,
            @RequestBody @Valid CreateInscriptionRequestBody request) {

        Booking booking = bookingService.getBookingById(id);
        if (booking == null) {
            return ResponseEntity.notFound().build();
        }
        User user = userService.findById(request.userId());
        Journey journey = journeyService.getJourneyById(request.journeyId());

        // Crée la nouvelle entité Booking avec les nouvelles valeurs, en conservant l'utilisateur existant
        Boolean updatedBooking = bookingService.update(
                id, request.toEntity(user,journey));

        return ResponseEntity.ok(updatedBooking);
    }

}


