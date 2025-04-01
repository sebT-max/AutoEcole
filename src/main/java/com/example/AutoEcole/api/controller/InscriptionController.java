package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.Inscription.CreateInscriptionRequestBody;
import com.example.AutoEcole.api.model.Inscription.CreateInscriptionResponseBody;
import com.example.AutoEcole.bll.service.FileService;
import com.example.AutoEcole.bll.service.InscriptionService;
import com.example.AutoEcole.bll.service.StageService;
import com.example.AutoEcole.bll.service.UserService;
import com.example.AutoEcole.dal.domain.entity.Inscription;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/V1/inscriptions")
public class InscriptionController {
    private final InscriptionService inscriptionService;
    private final UserService userService;
    private final StageService stageService;

    private final FileService fileService;

    @PostMapping(value="/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreateInscriptionResponseBody> createInscription(
            @RequestPart("request") CreateInscriptionRequestBody request,
            @RequestParam(value = "file", required = false) MultipartFile file)  {

        try {
            // Vérifier que le fichier est bien un PDF
            if (file != null && !file.isEmpty() &&
                    !file.getContentType().equals("application/pdf")) {
                return ResponseEntity.badRequest()
                        .body(new CreateInscriptionResponseBody(
                                "Le fichier doit être au format PDF", null, null, null, null, null, null));
            }

            // Sauvegarde de la lettre (retourne null si aucun fichier fourni)
            String fileName = fileService.saveFile(file);

            // Création de l'inscription
            CreateInscriptionResponseBody inscription = inscriptionService.createInscription(request, fileName);

            return ResponseEntity.ok(inscription);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CreateInscriptionResponseBody(
                            "Erreur lors du téléchargement de la lettre", null, null, null, null, null, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CreateInscriptionResponseBody(
                            "Erreur lors de l'inscription", null, null, null, null, null, null));
        }
    }

    @GetMapping("/file/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        try {
            Resource file = fileService.getFileAsResource(fileName);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF) // Force le PDF
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                    .body(file);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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


