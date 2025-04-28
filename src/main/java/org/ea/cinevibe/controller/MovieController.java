package org.ea.cinevibe.controller;

import org.ea.cinevibe.dto.MovieResponseDTO;
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

    @GetMapping("/{pageNum}")
    public ResponseEntity<MovieResponseDTO> findAll(@PathVariable int pageNum) {
        return ResponseEntity.ok(service.findALl(pageNum));
    }

    @GetMapping("/{title}{pageNum}")
    public ResponseEntity<MovieResponseDTO> getMoviesByTitle(@PathVariable String title,
                                                             @PathVariable int pageNum) {
        return ResponseEntity.ok(service.getMoviesByTitle(title, pageNum));
    }

    @GetMapping("/{releaseTime}{pageNum}")
    public ResponseEntity<MovieResponseDTO> getMoviesByReleaseYear(@PathVariable Integer releaseTime,
                                                                   @PathVariable int pageNum) {
        return ResponseEntity.ok(service.getMoviesByReleaseYear(releaseTime, pageNum));
    }

    @GetMapping("/{genres}{pageNum}")
    public ResponseEntity<MovieResponseDTO> getMoviesByGenre(@PathVariable List<String> genres,
                                                             @PathVariable int pageNum) {
        return ResponseEntity.ok(service.getMoviesByGenre(genres, pageNum));
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
