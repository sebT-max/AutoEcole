package com.example.AutoEcole.api.controller;
import com.example.AutoEcole.api.model.Particulier.ParticulierLoginRequestBody;
import com.example.AutoEcole.api.model.Particulier.ParticulierLoginResponseBody;
import com.example.AutoEcole.api.model.Particulier.ParticulierRegisterRequestBody;
import com.example.AutoEcole.api.model.Particulier.ParticulierRegisterResponseBody;
import com.example.AutoEcole.api.model.PrivateLink.PrivateLinkResponse;
import com.example.AutoEcole.bll.service.ParticulierService;
import com.example.AutoEcole.bll.service.PrivateLinkService;
import com.example.AutoEcole.bll.service.UserService;
import com.example.AutoEcole.dal.domain.entity.Particulier;
import com.example.AutoEcole.il.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/V1/particulier")
public class ParticulierController {
    private final ParticulierService particulierService;
    private final UserService userService;
    private final PrivateLinkService privateLinkService;

    private final JwtUtil jwtUtil;

//    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<ParticulierRegisterResponseBody> register(
//            @RequestPart("request") @Valid ParticulierRegisterRequestBody request,
//            @RequestPart(name = "files", required = false) List<MultipartFile> files) {
////        Long id = particulierService.register(request, files); // <-- on envoie les fichiers au service
////        return ResponseEntity.ok(id);
//        Particulier saved = particulierService.register(request, files); // change ton service pour renvoyer l'entité complète
//        String token = jwtUtil.generateToken(saved); // génération du token
//        ParticulierRegisterResponseBody response = ParticulierRegisterResponseBody.fromEntity(saved, token);
//        return ResponseEntity.ok(response);
//    }
    @PostMapping("/register")
    public ResponseEntity<ParticulierRegisterResponseBody> register(@RequestBody @Valid ParticulierRegisterRequestBody request) {
//        Long id = particulierService.register(request, files); // <-- on envoie les fichiers au service
//        return ResponseEntity.ok(id);
        Particulier saved = particulierService.register(request); // change ton service pour renvoyer l'entité complète
        String token = jwtUtil.generateToken(saved); // génération du token
        ParticulierRegisterResponseBody response = ParticulierRegisterResponseBody.fromEntity(saved, token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ParticulierLoginResponseBody> login(@RequestBody @Valid ParticulierLoginRequestBody request) {
        Particulier particulier = (Particulier)userService.login(request.email(), request.password());

        ParticulierLoginResponseBody particulierLoginResponseBody = ParticulierLoginResponseBody.fromEntity(particulier);
        String token = jwtUtil.generateToken(particulier);
        particulierLoginResponseBody.setToken(token);

        return ResponseEntity.ok(particulierLoginResponseBody);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/private-links")
    public List<PrivateLinkResponse> getAllLinks() {
        return privateLinkService.getAllLinks();
    }

}