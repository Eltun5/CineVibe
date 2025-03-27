package org.ea.cinevibe.controller;

import org.ea.cinevibe.model.MovieStaff;
import org.ea.cinevibe.model.enums.MovieStaffRole;
import org.ea.cinevibe.service.MovieStaffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/movie-staff")
public class MovieStaffController {
    private final MovieStaffService service;

    public MovieStaffController(MovieStaffService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody MovieStaff staff){
        service.save(staff);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<MovieStaff>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping
    public ResponseEntity<List<MovieStaff>> getMovieStaffsByRole(@RequestParam MovieStaffRole role){
        return ResponseEntity.ok(service.getMovieStaffsByRole(role));
    }

    @GetMapping
    public ResponseEntity<List<MovieStaff>> getMovieStaffByName(@RequestParam String name){
        return ResponseEntity.ok(service.getMovieStaffByName(name));
    }
}
