package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.PrivateLink.PrivateLinkRequest;
import com.example.AutoEcole.api.model.PrivateLink.PrivateLinkResponse;
import com.example.AutoEcole.bll.service.PrivateLinkService;
import com.example.AutoEcole.dal.domain.entity.PrivateLink;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/V1/privateLinks")
@RequiredArgsConstructor
public class PrivateLinkController {

    private final PrivateLinkService privateLinkService;

    @PostMapping("/create")
    public ResponseEntity<PrivateLinkResponse> createPrivateLink(@RequestBody PrivateLinkRequest request) {
        PrivateLinkResponse response = privateLinkService.createPrivateLink(request.stageId(), request.entrepriseId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<PrivateLink> validateLink(@PathVariable String token) {
        PrivateLink link = privateLinkService.getValidLink(token);
        return ResponseEntity.ok(link);
    }
}
