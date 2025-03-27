package org.ea.cinevibe.service;

import org.ea.cinevibe.model.Genre;
import org.ea.cinevibe.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository repository;

    public GenreService(GenreRepository repository) {
        this.repository = repository;
    }

    public void save(Genre genre) {
        repository.save(genre);
    }

    public List<Genre> getGenresByName(String name) {
        return repository.getGenresByName(name);
    }
}
