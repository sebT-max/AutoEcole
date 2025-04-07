package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.DemandeDevis.CreateDemandeDevisRequestBody;
import com.example.AutoEcole.api.model.DemandeDevis.CreateDemandeDevisResponseBody;
import com.example.AutoEcole.api.model.Devis.CreateDevisRequestBody;
import com.example.AutoEcole.api.model.Devis.CreateDevisResponseBody;
import com.example.AutoEcole.bll.service.DemandeDevisService;
import com.example.AutoEcole.bll.service.DevisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/V1/demandeDevis")
public class DemandeDevisController {
    private final DemandeDevisService demandeDevisService;

    @PostMapping("/create")
    public ResponseEntity<CreateDemandeDevisResponseBody> createDevis(@RequestBody CreateDemandeDevisRequestBody request){
        return ResponseEntity.ok(demandeDevisService.createDemandeDevis(request));
    }
}
