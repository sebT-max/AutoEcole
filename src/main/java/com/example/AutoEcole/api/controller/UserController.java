package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.api.model.user.*;
import com.example.AutoEcole.bll.service.RoleService;
import com.example.AutoEcole.bll.service.UserService;
import com.example.AutoEcole.dal.domain.entity.Role;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.il.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/V1/users")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final RoleService roleService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseBody> login(@RequestBody @Valid LoginRequestBody request){
        User user = userService.login(request.email(), request.password());
        LoginResponseBody loginResponseBody = LoginResponseBody.fromEntity(user);
        String token = jwtUtil.generateToken(user);
        loginResponseBody.setToken(token);
        return ResponseEntity.ok(loginResponseBody);
    }

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody @Valid RegisterRequestBody request){
        Long id = userService.register(request);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('PARTICULIER') or hasRole('ENTREPRISE')")
    public ResponseEntity<UserResponseBody> me(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(
                new UserResponseBody(
                        user.getId(),
                        user.getLastname(),
                        user.getFirstname(),
                        user.getEmail(),
                        user.getRole()
                )
        );
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseBody>> getAll(){
        return ResponseEntity.ok(
                userService.findAll().stream()
                        .map(UserResponseBody::fromEntity)
                        //.map(user -> UserResponseBody.fromEntity(user))
                        .toList()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseBody> get(@PathVariable Long id){
        UserResponseBody user = UserResponseBody.fromEntity(userService.findById(id));
        return ResponseEntity.ok(user);
    }
/*
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<Boolean> update(@PathVariable Long id, @RequestBody @Valid RegisterRequestBody request){
        Role role = roleService.findById(request.role().getId());
        boolean response = userService.update(id, request.toEntity(role));
        return ResponseEntity.ok(response);
    }

 */

    @PreAuthorize("hasRole('PARTICULIER')")
    @GetMapping("/particulier/dashboard")
    public ResponseEntity<String> espaceClient() {
        return ResponseEntity.ok("Bienvenue dans l’espace client !");
    }

    @PreAuthorize("hasRole('ENTREPRISE')")
    @GetMapping("/entreprise/dashboard")
    public ResponseEntity<String> espaceEntreprise() {
        return ResponseEntity.ok("Bienvenue dans l’espace entreprise !");
    }
}
