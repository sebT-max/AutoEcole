package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.Entreprise.EntrepriseLoginRequestBody;
import com.example.AutoEcole.api.model.Entreprise.EntrepriseLoginResponseBody;
import com.example.AutoEcole.api.model.Entreprise.EntrepriseRegisterRequestBody;
import com.example.AutoEcole.api.model.Particulier.ParticulierLoginRequestBody;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/V1/particulier")
public class ParticulierController {
    private final ParticulierService particulierService;
    private final UserService userService;

    private final JwtUtil jwtUtil;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> register(
            @RequestPart("request") @Valid ParticulierRegisterRequestBody request,
            @RequestPart(name = "files", required = false) List<MultipartFile> files) {
        Long id = particulierService.register(request, files); // <-- on envoie les fichiers au service
        return ResponseEntity.ok(id);
    }

    @PostMapping("/login")
    public ResponseEntity<ParticulierLoginResponseBody> login(@RequestBody @Valid ParticulierLoginRequestBody request) {
        Particulier particulier = (Particulier)userService.login(request.email(), request.password());

        ParticulierLoginResponseBody particulierLoginResponseBody = ParticulierLoginResponseBody.fromEntity(particulier);
        String token = jwtUtil.generateToken(particulier);
        particulierLoginResponseBody.setToken(token);

        return ResponseEntity.ok(particulierLoginResponseBody);
    }

}