package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.Inscription.CreateInscriptionRequestBody;
import com.example.AutoEcole.api.model.Inscription.CreateInscriptionResponseBody;
import com.example.AutoEcole.bll.service.InscriptionService;
import com.example.AutoEcole.bll.service.StageService;
import com.example.AutoEcole.bll.service.UserService;
import com.example.AutoEcole.dal.domain.entity.Inscription;
import com.example.AutoEcole.dal.domain.entity.Stage;
import com.example.AutoEcole.dal.domain.entity.User;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/V1/inscriptions")

public class InscriptionController {
    private final InscriptionService inscriptionService;
    private final UserService userService;
    private final StageService stageService;

    @PostMapping("/create")
    public ResponseEntity<CreateInscriptionResponseBody> createInscription(@RequestBody CreateInscriptionRequestBody request) {
        CreateInscriptionResponseBody response = inscriptionService.createInscription(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('PARTICULIER')")
    @GetMapping("/me")
    public ResponseEntity<List<Inscription>> getInscriptionsByUser(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.getUserIdByUsername(userDetails.getUsername());
        List<Inscription> inscriptions = inscriptionService.getInscriptionsByUserId(userId);
        return ResponseEntity.ok(inscriptions);
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Inscription> getAllInscriptions(){
        return inscriptionService.getAllInscriptions();
    }
     /*

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Inscription getInscriptionById(@PathVariable Long id){
        return inscriptionService.getBookingById(id);
    }


    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteBooking(@PathVariable Long id){
        return inscriptionService.delete(id);
    }
/*
    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> updateBooking(
            @PathVariable Long id,
            @RequestBody @Valid CreateInscriptionRequestBody request) {

        Inscription inscription = inscriptionService.getBookingById(id);
        if (inscription == null) {
            return ResponseEntity.notFound().build();
        }
        User user = userService.findById(request.userId());
        Stage stage = stageService.getStageById(request.stageId());

        // Crée la nouvelle entité Booking avec les nouvelles valeurs, en conservant l'utilisateur existant
        Boolean updatedInscription = inscriptionService.update(
                id, request.toEntity(user,stage));

        return ResponseEntity.ok(updatedBooking);
    }

 */

}


