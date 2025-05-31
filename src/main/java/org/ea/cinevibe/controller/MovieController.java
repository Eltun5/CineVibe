package org.ea.cinevibe.controller;

import lombok.RequiredArgsConstructor;
import org.ea.cinevibe.dto.MovieFilterRequestDTO;
import org.ea.cinevibe.dto.MovieRequestDTO;
import org.ea.cinevibe.dto.MovieResponseDTO;
import org.ea.cinevibe.model.Movie;
import org.ea.cinevibe.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/movie")
public class MovieController {
    private final MovieService service;

    @PostMapping
    public ResponseEntity<Movie> save(@RequestPart MovieRequestDTO request,
                                      MultipartFile file) throws IOException {
        return ResponseEntity.ok(service.save(request, file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<MovieResponseDTO> getMoviesByComplexFilter(@RequestBody MovieFilterRequestDTO filterRequestDTO) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(service.getComplexFilter(filterRequestDTO).get());
    }

    @GetMapping("/search/{title}{pageNum}")
    public ResponseEntity<MovieResponseDTO> search(@PathVariable String title,
                                                   @PathVariable Integer pageNum) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(service.search(title, pageNum).get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable Long id,
                                        @RequestPart MovieRequestDTO requestDTO,
                                        MultipartFile file) throws IOException {
        return ResponseEntity.ok(service.update(id, requestDTO, file));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
