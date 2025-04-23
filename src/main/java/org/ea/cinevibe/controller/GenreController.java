package org.ea.cinevibe.controller;

import org.ea.cinevibe.model.Genre;
import org.ea.cinevibe.service.GenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/genre")
public class GenreController {
    private final GenreService service;

    public GenreController(GenreService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Genre> save(@RequestBody Genre genre) {
        return ResponseEntity.ok(service.save(genre));
    }

    @GetMapping
    public ResponseEntity<List<Genre>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<Genre>> findAll(@PathVariable String name) {
        return ResponseEntity.ok(service.getGenresByName(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> update(@PathVariable Long id,
                                        @RequestBody Genre genre) {
        return ResponseEntity.ok(service.update(id, genre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
