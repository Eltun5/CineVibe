package org.ea.cinevibe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/")
public class MainController {

    @GetMapping
    public ResponseEntity<String> mainPage() {
        log.info("init ");
        return ResponseEntity.ok("Welcome CineVibe!!!");
    }
}
