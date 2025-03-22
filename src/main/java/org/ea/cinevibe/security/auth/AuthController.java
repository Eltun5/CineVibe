package org.ea.cinevibe.security.auth;

import jakarta.validation.Valid;
import org.ea.cinevibe.dto.LoginRequestDTO;
import org.ea.cinevibe.dto.RegisterRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequestDTO request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody @Valid LoginRequestDTO request) {
        return authService.logIn(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logOut(@RequestHeader("Authorization") String token) {
        return authService.logOut(token);
    }
}
