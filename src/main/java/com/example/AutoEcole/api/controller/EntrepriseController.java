package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.Entreprise.EntrepriseRegisterRequestBody;
import com.example.AutoEcole.bll.service.EntrepriseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/V1/entreprise")
public class EntrepriseController {
private final EntrepriseService entrepriseService;

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody @Valid EntrepriseRegisterRequestBody request){
        Long id = entrepriseService.register(request);
        return ResponseEntity.ok(id);
    }
}
