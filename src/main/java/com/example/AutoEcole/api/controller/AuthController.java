package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.user.RegisterRequestBody;
import com.example.AutoEcole.bll.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CreateUserRequestBody requestBody) {
        if (!requestBody.isAcceptTerms()) {
            return ResponseEntity.badRequest().body("Vous devez accepter les conditions d'utilisation.");
        }

        userService.registerUser(requestBody);
        return ResponseEntity.ok("Inscription réussie !");
    }
}