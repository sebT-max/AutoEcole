package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.CodePromo.CreateCodePromoRequestBody;
import com.example.AutoEcole.api.model.CodePromo.CreateCodePromoResponseBody;

import com.example.AutoEcole.bll.service.CodePromoService;
import com.example.AutoEcole.bll.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/V1/code-promos")

public class CodePromoController {
    private final UserService userService;
    private final CodePromoService codePromoService;

    @PostMapping("/create")
    public ResponseEntity<CreateCodePromoResponseBody> createCodePromo(@RequestBody CreateCodePromoRequestBody request) {
        return ResponseEntity.ok(codePromoService.createCodePromo(request));
    }

}
