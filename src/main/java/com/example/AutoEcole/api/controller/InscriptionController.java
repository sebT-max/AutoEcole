package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.Inscription.CreateInscriptionRequestBody;
import com.example.AutoEcole.api.model.Inscription.CreateInscriptionResponseBody;
import com.example.AutoEcole.bll.service.InscriptionService;
import com.example.AutoEcole.bll.service.StageService;
import com.example.AutoEcole.bll.service.UserService;
import com.example.AutoEcole.dal.domain.entity.Inscription;
import com.example.AutoEcole.dal.domain.entity.Stage;
import com.example.AutoEcole.dal.domain.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/V1/inscription")

public class InscriptionController {
    private final InscriptionService inscriptionService;
    private final UserService userService;
    private final StageService stageService;

    @PostMapping("/create")
    public ResponseEntity<CreateInscriptionResponseBody> createInscription(@RequestBody CreateInscriptionRequestBody request) {
        User user = userService.findById(request.user().getId());
        try {
            CreateInscriptionResponseBody response = inscriptionService.createInscription(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new CreateInscriptionResponseBody(e.getMessage()));
        }
    }
    @GetMapping("/all")
    @PreAuthorize("hasRole('OPERATOR')")
    public List<Inscription> getAllInscriptions(){
        return inscriptionService.getAllInscriptions();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OPERATOR')")
    public Inscription getInscriptionById(@PathVariable Long id){
        return inscriptionService.getBookingById(id);
    }

    @GetMapping("/users/{userId}")
    @PreAuthorize("hasRole('OPERATOR')")
    public List<Inscription> getInscriptionsByUserId(@PathVariable Long userId){
        return inscriptionService.getInscriptionsByUserId(userId);
    }
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('OPERATOR')")
    public boolean deleteBooking(@PathVariable Long id){
        return inscriptionService.delete(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> updateBooking(
            @PathVariable Long id,
            @RequestBody @Valid CreateInscriptionRequestBody request) {

        Inscription inscription = inscriptionService.getBookingById(id);
        if (inscription == null) {
            return ResponseEntity.notFound().build();
        }
        User user = userService.findById(request.user().getId());
        Stage stage = stageService.getStageById(request.stageId());

        // Crée la nouvelle entité Booking avec les nouvelles valeurs, en conservant l'utilisateur existant
        Boolean updatedBooking = inscriptionService.update(
                id, request.toEntity(user,stage));

        return ResponseEntity.ok(updatedBooking);
    }

}


