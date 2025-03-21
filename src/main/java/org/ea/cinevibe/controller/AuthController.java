package org.ea.cinevibe.controller;

import jakarta.validation.Valid;
import org.ea.cinevibe.dto.LoginRequestDTO;
import org.ea.cinevibe.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody @Valid LoginRequestDTO request){
        return authService.logIn(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logOut(@RequestHeader("Authorization") String token){
        return authService.logOut(token);
    }
}
