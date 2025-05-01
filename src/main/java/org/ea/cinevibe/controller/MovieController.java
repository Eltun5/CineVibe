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

    @GetMapping("/{title}{genres}{releaseYear}{pageNum}{sortByTitle}{sortByReleaseYear}")
    public ResponseEntity<MovieResponseDTO> getMoviesByComplexFilter(@PathVariable String title,
                                                                     @PathVariable List<String> genres,
                                                                     @PathVariable Integer releaseYear,
                                                                     @PathVariable int pageNum,
                                                                     @PathVariable boolean sortByTitle,
                                                                     @PathVariable boolean sortByReleaseYear) {
        return ResponseEntity.ok(service.
                getMoviesComplexFilter(title, releaseYear,
                        genres, pageNum,
                        sortByTitle, sortByReleaseYear));
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
