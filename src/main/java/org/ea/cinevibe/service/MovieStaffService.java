package org.ea.cinevibe.service;

import lombok.extern.slf4j.Slf4j;
import org.ea.cinevibe.mapper.MovieStaffMapper;
import org.ea.cinevibe.model.MovieStaff;
import org.ea.cinevibe.model.enums.MovieStaffRole;
import org.ea.cinevibe.repository.MovieStaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MovieStaffService {
    private final MovieStaffRepository repository;

    public MovieStaffService(MovieStaffRepository repository) {
        this.repository = repository;
    }

    public MovieStaff save(MovieStaff movieStaff) {
        log.info("Some user create movie staff.");
        return repository.save(movieStaff);
    }

    public List<MovieStaff> findAll() {
        return repository.findAll();
    }

    public List<MovieStaff> getMovieStaffsByRole(MovieStaffRole role) {
        return repository.getMovieStaffsByRole(role);
    }

    public List<MovieStaff> getMovieStaffByName(String name) {
        return repository.getMovieStaffsByName(name);
    }

    public MovieStaff update(Long id, MovieStaff movieStaff) {
        log.info("Some user update movie staff.");
        MovieStaff oldMovieStaff = repository.getReferenceById(id);

        return repository.save(MovieStaffMapper.mapMovieStaff(oldMovieStaff, movieStaff));
    }

    public void delete(Long id) {
        log.warn("Some user create movie staff.");
        repository.delete(repository.getReferenceById(id));
    }
}
