package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.Inscription.CreateInscriptionRequestBody;
import com.example.AutoEcole.api.model.Inscription.CreateInscriptionResponseBody;
import com.example.AutoEcole.bll.service.FileService;
import com.example.AutoEcole.bll.service.InscriptionService;
import com.example.AutoEcole.bll.service.StageService;
import com.example.AutoEcole.bll.service.UserService;
import com.example.AutoEcole.dal.domain.entity.Inscription;

import jakarta.validation.Valid;
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
    private final FileService fileService;

    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreateInscriptionResponseBody> createInscription(
            @RequestPart("request") CreateInscriptionRequestBody request,
            @RequestPart(name = "files", required = false) List<MultipartFile> files) throws IOException {

        CreateInscriptionResponseBody response = inscriptionService.createInscription(request, files);
        return ResponseEntity.ok(response);
    }


//        try {
//            // Vérifier que le fichier est bien un PDF
//            if (file != null && !file.isEmpty() &&
//                    !file.getContentType().equals("application/pdf")) {
//                return ResponseEntity.ok("TriBLop");
////                return ResponseEntity.badRequest()
////                        .body(new CreateInscriptionResponseBody(
////                                "Le fichier doit être au format PDF", null, null, null, null, null, null));
//
//
//            // Sauvegarde de la lettre (retourne null si aucun fichier fourni)
//            String fileName = fileService.saveFile(file);
//
//            // Création de l'inscription
////            CreateInscriptionResponseBody inscription = inscriptionService.createInscription(request, fileName);
//
//            return ResponseEntity.ok("Ca fonctionne");
//        } catch (IOException e) {
//            return ResponseEntity.ok("BIBLOP");
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body(new CreateInscriptionResponseBody(
////                            "Erreur lors du téléchargement de la lettre", null, null, null, null, null, null));
//        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body(new CreateInscriptionResponseBody(
////                            "Erreur lors de l'inscription", null, null, null, null, null, null));
//            return ResponseEntity.ok("BIBLOP");
//        }



      /*
    @PostMapping(value="/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createInscription(
            @RequestPart("request") CreateInscriptionRequestBody request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        try {
            // Vérifier que le fichier est bien un PDF
            if (file != null && !file.isEmpty() &&
                    !"application/pdf".equals(file.getContentType())) {
                return ResponseEntity.badRequest().body("Le fichier doit être au format PDF");
            }

            // Sauvegarde de la lettre (retourne null si aucun fichier fourni)
            String fileName = (file != null) ? fileService.saveFile(file) : null;

            // Création de l'inscription
            // CreateInscriptionResponseBody inscription = inscriptionService.createInscription(request, fileName);

            return ResponseEntity.ok("Inscription créée avec succès !");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors du téléchargement du fichier");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'inscription");
        }
    }

*/
//      @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//      public ResponseEntity<CreateInscriptionResponseBody> createInscription(
//              @RequestPart("request") CreateInscriptionRequestBody request,
//              @RequestPart(value = "file", required = false) MultipartFile file) {
//          try {
//              System.out.println("Données reçues : " + request);
//
//              // Vérification du fichier
//              if (file != null && !file.isEmpty() &&
//                      !"application/pdf".equals(file.getContentType())) {
//                  return ResponseEntity.badRequest().body(new CreateInscriptionResponseBody(
//                          "Le fichier doit être au format PDF",
//                          null, null, null, null, null, null
//                  ));
//              }
//
//              // Sauvegarde du fichier et récupération du nom
//              String fileName = (file != null) ? fileService.saveFile(file) : null;
//
//              // 🔥 Appel du service avec le bon paramètre `fileName`
//              CreateInscriptionResponseBody response = inscriptionService.createInscription(request, fileName);
//
//              return ResponseEntity.ok(response);
//
//          } catch (IOException e) {
//              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                      .body(new CreateInscriptionResponseBody(
//                              "Erreur lors du téléchargement du fichier",
//                              null, null, null, null, null, null
//                      ));
//          } catch (Exception e) {
//              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                      .body(new CreateInscriptionResponseBody(
//                              "Erreur lors de l'inscription",
//                              null, null, null, null, null, null
//                      ));
//          }
//      }

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
    public Inscription validateInscriptionById(@PathVariable Long id){
        return inscriptionService.validateInscriptionById(id);
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


