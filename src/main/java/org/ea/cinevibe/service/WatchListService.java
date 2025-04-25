package org.ea.cinevibe.service;

import lombok.extern.slf4j.Slf4j;
import org.ea.cinevibe.model.Movie;
import org.ea.cinevibe.model.User;
import org.ea.cinevibe.model.WatchList;
import org.ea.cinevibe.repository.WatchListRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class WatchListService {
    private final WatchListRepository repository;

    private final UserService userService;

    private final MovieService movieService;

    public WatchListService(WatchListRepository repository, UserService userService, MovieService movieService) {
        this.repository = repository;
        this.userService = userService;
        this.movieService = movieService;
    }

    public WatchList add(Long movieId) {
        Optional<WatchList> watchList = getCurrentWatchList();

        Movie movie=movieService.getMovieById(movieId);

        if (watchList.isPresent()){
            WatchList watchList1 = watchList.get();
            Set<Movie> movies = watchList1.getMovies();
            movies.add(movie);
            watchList1.setMovies(movies);
            repository.save(watchList1);
            return watchList1;
        }else {
            Set<Movie> movies=new HashSet<>();
            movies.add(movie);
            WatchList watchList1 = WatchList.builder()
                    .movies(movies)
                    .user(getCurrentUser())
                    .build();
            repository.save(watchList1);
            return watchList1;
        }
    }

    public WatchList getWatchList(){
        Optional<WatchList> currentWatchList = getCurrentWatchList();

        if (currentWatchList.isEmpty()){
            repository.save(WatchList.builder().user(getCurrentUser()).build());
        }

        return getCurrentWatchList().orElseThrow();
    }

    public void remove(Long movieId){
        Optional<WatchList> watchListByUser = getCurrentWatchList();

        Movie movie=movieService.getMovieById(movieId);

        watchListByUser.ifPresent(watchList -> {
            Set<Movie> movies = watchList.getMovies();
            movies.remove(movie);
            watchList.setMovies(movies);
            repository.save(watchList);
        });
    }

    private Optional<WatchList> getCurrentWatchList(){
        return repository.getWatchListByUser(getCurrentUser());
    }

    private User getCurrentUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        return userService.getUserByUsername(userDetails.getUsername());
    }
}
