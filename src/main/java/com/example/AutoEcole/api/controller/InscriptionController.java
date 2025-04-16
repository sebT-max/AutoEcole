package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.Document.DocumentDTO;
import com.example.AutoEcole.api.model.Inscription.CreateInscriptionRequestBody;
import com.example.AutoEcole.api.model.Inscription.CreateInscriptionResponseBody;
import com.example.AutoEcole.bll.service.CloudinaryService;
import com.example.AutoEcole.bll.service.InscriptionService;
import com.example.AutoEcole.bll.service.StageService;
import com.example.AutoEcole.bll.service.UserService;
import com.example.AutoEcole.dal.domain.entity.Inscription;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/V1/inscriptions")
public class InscriptionController {
    private final InscriptionService inscriptionService;
    private final UserService userService;
    private final StageService stageService;
    private final CloudinaryService fileService;

    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreateInscriptionResponseBody> createInscription(
            @RequestPart("request") CreateInscriptionRequestBody request,
            @RequestPart(name = "files", required = false) List<MultipartFile> files) throws IOException {

        CreateInscriptionResponseBody response = inscriptionService.createInscription(request, files);
        return ResponseEntity.ok(response);
    }

//
//    @GetMapping("/file/{fileName}")
//    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
//        try {
//            Resource file = fileService.getFileAsResource(fileName);
//
//            return ResponseEntity.ok()
//                    .contentType(MediaType.APPLICATION_PDF) // Force le PDF
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
//                    .body(file);
//        } catch (FileNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }



    @PreAuthorize("hasRole('PARTICULIER') or hasRole('ENTREPRISE')")
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

    @DeleteMapping("/delete/{id}")
    public boolean deleteInscription(@PathVariable Long id){
        return inscriptionService.delete(id);
    }

    @PatchMapping("/{id}/validate")
    @PreAuthorize("hasRole('ADMIN')")
    public CreateInscriptionResponseBody validateInscriptionById(@PathVariable Long id){
        Inscription inscription = inscriptionService.validateInscriptionById(id);
        return mapToResponseBody(inscription);
    }
    private CreateInscriptionResponseBody mapToResponseBody(Inscription inscription) {
        return new CreateInscriptionResponseBody(
                inscription.getId(),
                inscription.getUser().getId(),
                inscription.getStage().getId(),
                inscription.getStageType(),
                inscription.getInscriptionStatut(),
                inscription.getDocuments().stream()
                        .map(doc -> new DocumentDTO(
                                doc.getId(),
                                doc.getFileName(),
                                doc.getType(),
                                doc.getFileUrl(),
                                doc.getUser().getId(),
                                doc.getInscription().getId(),
                                doc.getUploadedAt()
                        ))
                        .toList()
        );
    }
    /*

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Inscription getInscriptionById(@PathVariable Long id){
        return inscriptionService.getBookingById(id);
    }

     */



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


