package org.ea.cinevibe.mapper;

import org.ea.cinevibe.model.Movie;

import java.time.LocalDateTime;

public class MovieMapper {
    public static Movie mapMovie(Movie oldMovie, Movie newMovie){
        oldMovie.setTitle(newMovie.getTitle());
        oldMovie.setSynopsis(newMovie.getSynopsis());
        oldMovie.setGenres(newMovie.getGenres());
        oldMovie.setReleaseYear(newMovie.getReleaseYear());
        oldMovie.setStaffs(newMovie.getStaffs());
        oldMovie.setPosterImageUrl(newMovie.getPosterImageUrl());
        oldMovie.setUpdatedAt(LocalDateTime.now());

        return oldMovie;
    }
}
