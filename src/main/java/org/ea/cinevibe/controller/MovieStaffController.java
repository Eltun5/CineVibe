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
    public ResponseEntity<MovieStaff> save(@RequestBody MovieStaff staff) {
        return ResponseEntity.ok(service.save(staff));
    }

    @GetMapping
    public ResponseEntity<List<MovieStaff>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{role}")
    public ResponseEntity<List<MovieStaff>> getMovieStaffsByRole(@PathVariable MovieStaffRole role) {
        return ResponseEntity.ok(service.getMovieStaffsByRole(role));
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<MovieStaff>> getMovieStaffByName(@PathVariable String name) {
        return ResponseEntity.ok(service.getMovieStaffByName(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieStaff> update(@PathVariable Long id,
                                             @RequestBody MovieStaff movieStaff) {
        return ResponseEntity.ok(service.update(id, movieStaff));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
