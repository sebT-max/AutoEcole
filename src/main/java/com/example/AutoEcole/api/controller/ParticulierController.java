package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.Entreprise.EntrepriseLoginRequestBody;
import com.example.AutoEcole.api.model.Entreprise.EntrepriseLoginResponseBody;
import com.example.AutoEcole.api.model.Entreprise.EntrepriseRegisterRequestBody;
import com.example.AutoEcole.api.model.Particulier.ParticulierLoginResponseBody;
import com.example.AutoEcole.api.model.Particulier.ParticulierRegisterRequestBody;
import com.example.AutoEcole.bll.service.EntrepriseService;
import com.example.AutoEcole.bll.service.ParticulierService;
import com.example.AutoEcole.bll.service.UserService;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.Particulier;
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
@RequestMapping("/api/V1/particulier")
public class ParticulierController {
    private final ParticulierService particulierService;
    private final UserService userService;

    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody @Valid ParticulierRegisterRequestBody request){
        Long id = particulierService.register(request);
        return ResponseEntity.ok(id);
    }
    @PostMapping("/login")
    public ResponseEntity<ParticulierLoginResponseBody> login(@RequestBody @Valid EntrepriseLoginRequestBody request) {
        Particulier particulier = (Particulier)userService.login(request.email(), request.password());

        ParticulierLoginResponseBody particulierLoginResponseBody = ParticulierLoginResponseBody.fromEntity(particulier);
        String token = jwtUtil.generateToken(particulier);
        particulierLoginResponseBody.setToken(token);

        return ResponseEntity.ok(particulierLoginResponseBody);
    }

}