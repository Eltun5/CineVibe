package org.ea.cinevibe.controller;

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

    @GetMapping
    public ResponseEntity<List<Review>> getReviewByMovie(@RequestParam Movie movie){
        return ResponseEntity.ok(service.getReviewsByMovie(movie));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestParam Review review){
        service.save(review);
        return ResponseEntity.ok().build();
    }

}
