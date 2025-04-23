package org.ea.cinevibe.controller;

import org.ea.cinevibe.model.Comment;
import org.ea.cinevibe.model.Review;
import org.ea.cinevibe.model.User;
import org.ea.cinevibe.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/comment")
public class CommentController {
    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Comment> save(@RequestBody Comment comment) {
        return ResponseEntity.ok(service.save(comment));
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getCommentsByUser(@RequestParam User user) {
        return ResponseEntity.ok(service.getCommentsByUser(user));
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getCommentsByReview(@RequestParam Review review) {
        return ResponseEntity.ok(service.getCommentsByReview(review));
    }
}
