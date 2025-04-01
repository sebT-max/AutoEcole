package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.Entreprise.EntrepriseLoginRequestBody;
import com.example.AutoEcole.api.model.Entreprise.EntrepriseLoginResponseBody;
import com.example.AutoEcole.api.model.Entreprise.EntrepriseRegisterRequestBody;
import com.example.AutoEcole.api.model.user.LoginRequestBody;
import com.example.AutoEcole.api.model.user.LoginResponseBody;
import com.example.AutoEcole.bll.service.EntrepriseService;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.il.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/V1/company")
public class EntrepriseController {
    private final EntrepriseService entrepriseService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody @Valid EntrepriseRegisterRequestBody request){
        Long id = entrepriseService.register(request);
        return ResponseEntity.ok(id);
    }
    @PostMapping("/login")
    public ResponseEntity<EntrepriseLoginResponseBody> login(@RequestBody @Valid EntrepriseLoginRequestBody request) {
        Entreprise entreprise = entrepriseService.login(request.email(), request.password());

        EntrepriseLoginResponseBody entrepriseLoginResponseBody = EntrepriseLoginResponseBody.fromEntity(entreprise);
        String token = jwtUtil.generateToken(entreprise);
        entrepriseLoginResponseBody.setToken(token);

        return ResponseEntity.ok(entrepriseLoginResponseBody);
    }

}
