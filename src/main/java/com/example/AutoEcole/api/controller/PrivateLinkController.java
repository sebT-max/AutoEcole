package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.Entreprise.EmployeeInscriptionForm;
import com.example.AutoEcole.api.model.PrivateLink.PrivateLinkRequest;
import com.example.AutoEcole.api.model.PrivateLink.PrivateLinkResponse;
import com.example.AutoEcole.api.model.PrivateLink.PrivateLinkValidationResponse;
import com.example.AutoEcole.bll.service.PrivateLinkService;
import com.example.AutoEcole.bll.service.UserService;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.Inscription;
import com.example.AutoEcole.dal.domain.entity.PrivateLink;
import com.example.AutoEcole.dal.domain.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/V1/privateLinks")
@RequiredArgsConstructor
public class PrivateLinkController {

    private final PrivateLinkService privateLinkService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<PrivateLinkResponse> createPrivateLink(@RequestBody PrivateLinkRequest request) {
        PrivateLinkResponse response = privateLinkService.createPrivateLink(request.stageId(), request.entrepriseId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/privateLinks")
    public ResponseEntity<List<PrivateLinkResponse>> getMyPrivateLinks(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();

//        // Vérification stricte du rôle
//        if (!(currentUser instanceof Entreprise)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }

        Entreprise entreprise = (Entreprise) currentUser; // Downcast
        List<PrivateLinkResponse> links = privateLinkService.getPrivateLinksForEntreprise(entreprise);

        return ResponseEntity.ok(links); // Retourne les données transformées par le service
    }
    @GetMapping("/{token}")
    public ResponseEntity<PrivateLinkValidationResponse> getLinkDetails(@PathVariable String token) {
        PrivateLinkValidationResponse response = privateLinkService.validateAndGetInfo(token);
        return ResponseEntity.ok(response);
    }

}
