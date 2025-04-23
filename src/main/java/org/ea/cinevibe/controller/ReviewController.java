package org.ea.cinevibe.controller;

import jakarta.validation.constraints.Size;
import org.ea.cinevibe.model.Movie;
import org.ea.cinevibe.model.Review;
import org.ea.cinevibe.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/review")
public class ReviewController {
    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Review> save(@RequestBody @Size(min = 1, max = 5) Review review) {
        return ResponseEntity.ok(service.save(review));
    }

    @GetMapping
    public ResponseEntity<List<Review>> getReviewByMovie(@RequestParam Movie movie) {
        return ResponseEntity.ok(service.getReviewsByMovie(movie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> update(@PathVariable Long id,
                                         @RequestBody Review review) {
        return ResponseEntity.ok(service.update(id, review));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
