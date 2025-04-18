package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.PrivateLink.PrivateLinkRequest;
import com.example.AutoEcole.api.model.PrivateLink.PrivateLinkResponse;
import com.example.AutoEcole.api.model.PrivateLink.PrivateLinkValidationResponse;
import com.example.AutoEcole.bll.service.PrivateLinkService;
import com.example.AutoEcole.bll.service.UserService;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.Inscription;
import com.example.AutoEcole.dal.domain.entity.PrivateLink;
import com.example.AutoEcole.dal.domain.entity.User;
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

    @GetMapping("/validate/{token}")
    public ResponseEntity<PrivateLinkValidationResponse> validateLink(@PathVariable String token) {
        try {
            // Récupère le lien validé
            PrivateLinkValidationResponse response = privateLinkService.validateAndGetInfo(token);

            // Retourne une réponse de validation avec succès
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            // Si une exception est lancée (par exemple, lien expiré ou usage dépassé), retourne un message d'erreur
            return ResponseEntity.status(e.getStatusCode())
                    .body(new PrivateLinkValidationResponse(null, null, null, false, 0));
        }
    }
}
