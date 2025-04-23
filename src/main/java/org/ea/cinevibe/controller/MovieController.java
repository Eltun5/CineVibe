package org.ea.cinevibe.controller;

import org.ea.cinevibe.model.Movie;
import org.ea.cinevibe.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/movie")
public class MovieController {
    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Movie> save(@RequestBody Movie movie) {
        return ResponseEntity.ok(service.save(movie));
    }

    @GetMapping
    public ResponseEntity<List<Movie>> findAll() {
        return ResponseEntity.ok(service.findALl());
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getMoviesByTitle(@RequestParam String title) {
        return ResponseEntity.ok(service.getMoviesByTitle(title));
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getMovieByReleaseYear(@RequestParam Integer releaseTime) {
        return ResponseEntity.ok(service.getMovieByReleaseYear(releaseTime));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable Long id,
                                        @RequestBody Movie movie) {
        return ResponseEntity.ok(service.update(id, movie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
