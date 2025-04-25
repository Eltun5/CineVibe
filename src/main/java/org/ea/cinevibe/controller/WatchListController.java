package org.ea.cinevibe.controller;

import org.ea.cinevibe.model.WatchList;
import org.ea.cinevibe.service.WatchListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/watchList")
public class WatchListController {
    private final WatchListService service;

    public WatchListController(WatchListService service) {
        this.service = service;
    }

    @PostMapping("/{movieId}")
    public ResponseEntity<WatchList> addMovie(@PathVariable Long movieId){
        return ResponseEntity.ok(service.add(movieId));
    }

    @GetMapping
    public ResponseEntity<WatchList> get(){
        return ResponseEntity.ok(service.getWatchList());
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> removeMovie(@PathVariable Long movieId){
        service.remove(movieId);
        return ResponseEntity.ok().build();
    }
}
