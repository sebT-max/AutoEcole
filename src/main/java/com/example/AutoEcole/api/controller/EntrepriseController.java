package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.Entreprise.*;
import com.example.AutoEcole.api.model.user.LoginRequestBody;
import com.example.AutoEcole.api.model.user.LoginResponseBody;
import com.example.AutoEcole.bll.service.EntrepriseService;
import com.example.AutoEcole.bll.service.PrivateLinkService;
import com.example.AutoEcole.bll.service.UserService;
import com.example.AutoEcole.dal.domain.entity.Entreprise;
import com.example.AutoEcole.dal.domain.entity.PrivateLink;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.il.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/V1/company")
public class EntrepriseController {
    private final UserService userService;
    private final EntrepriseService entrepriseService;
    private final PrivateLinkService privateLinkService;
    private final JwtUtil jwtUtil;

//    @PostMapping("/register")
//    public ResponseEntity<Long> register(@RequestBody @Valid EntrepriseRegisterRequestBody request){
//        Long id = entrepriseService.register(request);
//        return ResponseEntity.ok(id);
//    }
//    @PostMapping("/register")
//    public ResponseEntity<EntrepriseRegisterResponseBody> register(@RequestBody @Valid EntrepriseRegisterRequestBody request) {
//        // 1. Inscription
//        Long id = entrepriseService.register(request);
//
//        // 2. Authentifier l'utilisateur (comme dans le login)
//        Entreprise entreprise = (Entreprise) userService.login(request.email(), request.password());
//
//        // 3. Générer le token JWT
//        String token = jwtUtil.generateToken(entreprise);
//
//        // 4. Construire la réponse avec les infos + token
//        EntrepriseRegisterResponseBody responseBody = EntrepriseRegisterResponseBody.fromEntity(entreprise);
//        responseBody.setToken(token);
//
//        return ResponseEntity.ok(responseBody);
//    }
    @PostMapping("/register")
    public ResponseEntity<EntrepriseRegisterResponseBody> register(@RequestBody @Valid EntrepriseRegisterRequestBody request){
        Entreprise saved = entrepriseService.register(request); // change ton service pour renvoyer l'entité complète
        String token = jwtUtil.generateToken(saved); // génération du token
        EntrepriseRegisterResponseBody response = EntrepriseRegisterResponseBody.fromEntity(saved, token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<EntrepriseLoginResponseBody> login(@RequestBody @Valid EntrepriseLoginRequestBody request) {
        Entreprise entreprise = (Entreprise) userService.login(request.email(), request.password());

        EntrepriseLoginResponseBody entrepriseLoginResponseBody = EntrepriseLoginResponseBody.fromEntity(entreprise);
        String token = jwtUtil.generateToken(entreprise);
        entrepriseLoginResponseBody.setToken(token);

        return ResponseEntity.ok(entrepriseLoginResponseBody);
    }
    @PostMapping("/inscription/{token}")
    public ResponseEntity<?> registerViaPrivateLink(@PathVariable String token,
                                                    @Valid @RequestBody EmployeeInscriptionForm form) {
        PrivateLink link = privateLinkService.getValidLink(token);

        // On passe ensuite le lien et le form au service qui crée le user
        userService.registerEmployeeViaPrivateLink(form, link);

        return ResponseEntity.ok("Inscription réussie !");
    }
    @GetMapping("/email/{email}")
    public Entreprise getCompanyByEmail(@PathVariable String email) {
        return entrepriseService.getEntrepriseByEmail(email);
    }
}
