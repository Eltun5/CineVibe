package org.ea.cinevibe.service;

import org.ea.cinevibe.model.MovieStaff;
import org.ea.cinevibe.model.enums.MovieStaffRole;
import org.ea.cinevibe.repository.MovieStaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieStaffService {
    private final MovieStaffRepository repository;

    public MovieStaffService(MovieStaffRepository repository) {
        this.repository = repository;
    }

    public void save(MovieStaff movieStaff) {
        repository.save(movieStaff);
    }

    public List<MovieStaff> findAll(){
        return repository.findAll();
    }

    public List<MovieStaff> getMovieStaffsByRole(MovieStaffRole role) {
        return repository.getMovieStaffsByRole(role);
    }

    public List<MovieStaff> getMovieStaffByName(String name) {
        return repository.getMovieStaffsByName(name);
    }
}
