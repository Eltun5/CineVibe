package org.ea.cinevibe.controller;

import lombok.extern.slf4j.Slf4j;
import org.ea.cinevibe.security.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/")
public class MainController {
    private final JwtService jwtService;

    public MainController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<String> mainPage() {
        log.info("init ");
        return ResponseEntity.ok("Welcome CineVibe!!!");
    }
}
