package org.ea.cinevibe.controller;

import org.ea.cinevibe.model.User;
import org.ea.cinevibe.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/watchList")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/{movieId}")
    public ResponseEntity<User> addMovie(@PathVariable Long movieId){
        return ResponseEntity.ok(service.addMovieInWatchList(movieId));
    }

    @GetMapping
    public ResponseEntity<User> get(){
        return ResponseEntity.ok(service.getCurrentUser());
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> removeMovie(@PathVariable Long movieId){
        service.removeMovieInWatchList(movieId);
        return ResponseEntity.ok().build();
    }
}
