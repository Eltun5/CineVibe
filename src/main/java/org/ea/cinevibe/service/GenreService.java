package org.ea.cinevibe.service;

import lombok.extern.slf4j.Slf4j;
import org.ea.cinevibe.model.Genre;
import org.ea.cinevibe.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class GenreService {
    private final GenreRepository repository;

    public GenreService(GenreRepository repository) {
        this.repository = repository;
    }

    public Genre save(Genre genre) {
        log.info("Some user create genre.");
        return repository.save(genre);
    }

    public List<Genre> findAll() {
        return repository.findAll();
    }

    public List<Genre> getGenresByName(String name) {
        return repository.getGenresByName(name);
    }

    public List<Genre> getGenresByNames(List<String> names) {
        return repository.getGenresByNames(names);
    }

    public Genre update(Long id, Genre genre) {
        log.info("Some user update genre.");
        Genre oldGenre = repository.getReferenceById(id);

        oldGenre.setName(genre.getName());
        oldGenre.setDescription(genre.getDescription());
        oldGenre.setUpdatedAt(LocalDateTime.now());

        return repository.save(oldGenre);
    }

    public void delete(Long id) {
        log.warn("Some user delete genre.");
        repository.delete(repository.getReferenceById(id));
    }
}
