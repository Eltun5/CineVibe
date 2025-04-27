package org.ea.cinevibe.service;

import lombok.extern.slf4j.Slf4j;
import org.ea.cinevibe.excepions.NoDataFoundException;
import org.ea.cinevibe.model.Movie;
import org.ea.cinevibe.model.User;
import org.ea.cinevibe.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UserService {
    private final UserRepository repository;

    private final MovieService movieService;

    public UserService(UserRepository repository, MovieService movieService) {
        this.repository = repository;
        this.movieService = movieService;
    }

    public User getUserById(Long id){
        return repository.getReferenceById(id);
    }

    public User getUserByUsername(String username) {
        return repository.getUserByUsername(username).
                orElseThrow(() -> new NoDataFoundException("User"));
    }

    public User getUserByEmail(String email) {
        return repository.getUserByEmail(email).
                orElseThrow(() -> new NoDataFoundException("User"));
    }

    public void save(User user) {
        repository.save(user);
    }

    public void delete(User user) {
        log.info("Some user delete user.");
        repository.delete(user);
    }

    public User addMovieInWatchList(Long movieId) {
        User user = getCurrentUser();

        Movie movie=movieService.getMovieById(movieId);

        List<Movie> watchList = user.getWatchList();
        watchList.add(movie);
        user.setWatchList(watchList);

        return repository.save(user);
    }

    public void removeMovieInWatchList(Long movieId){
        User user = getCurrentUser();

        Movie movie=movieService.getMovieById(movieId);

        List<Movie> watchList = user.getWatchList();
        watchList.remove(movie);
        user.setWatchList(watchList);
    }

    public User getCurrentUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        return getUserByUsername(userDetails.getUsername());
    }
}
