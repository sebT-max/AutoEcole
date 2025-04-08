package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.DemandeDevis.CreateDemandeDevisRequestBody;
import com.example.AutoEcole.api.model.DemandeDevis.CreateDemandeDevisResponseBody;
import com.example.AutoEcole.api.model.Devis.CreateDevisRequestBody;
import com.example.AutoEcole.api.model.Devis.CreateDevisResponseBody;
import com.example.AutoEcole.bll.service.DemandeDevisService;
import com.example.AutoEcole.bll.service.DevisService;
import com.example.AutoEcole.dal.domain.entity.DemandeDevis;
import com.example.AutoEcole.dal.domain.entity.Inscription;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/V1/demandeDevis")
public class DemandeDevisController {
    private final DemandeDevisService demandeDevisService;

    @PostMapping("/create")
    public ResponseEntity<CreateDemandeDevisResponseBody> createDevis(@RequestBody CreateDemandeDevisRequestBody request){
        return ResponseEntity.ok(demandeDevisService.createDemandeDevis(request));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CreateDemandeDevisResponseBody>> getAllDemandeDevis() {
        return ResponseEntity.ok(demandeDevisService.getAllDemandeDevis());
    }
}
