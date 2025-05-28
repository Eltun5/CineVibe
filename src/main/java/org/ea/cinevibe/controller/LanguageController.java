package org.ea.cinevibe.controller;

import org.ea.cinevibe.model.Language;
import org.ea.cinevibe.service.LanguageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/language")
public class LanguageController {
    private final LanguageService service;

    public LanguageController(LanguageService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Language> save(@RequestBody String name) {
        return ResponseEntity.ok(service.save(name));
    }

    @GetMapping
    public ResponseEntity<List<Language>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Language> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Language>> searchByName(@PathVariable String name) {
        return ResponseEntity.ok(service.searchByName(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Language> update(@PathVariable Long id,
                                           @RequestBody String name) {
        return ResponseEntity.ok(service.update(id, name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
