package org.ea.cinevibe.service;

import lombok.extern.slf4j.Slf4j;
import org.ea.cinevibe.mapper.MovieMapper;
import org.ea.cinevibe.model.Movie;
import org.ea.cinevibe.model.Review;
import org.ea.cinevibe.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MovieService {
    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public Movie save(Movie movie) {
        if (movie.getReleaseYear() < 1850) {
            log.error("Release year cannot be less than 1850!!!");
            throw new RuntimeException("Release year cannot be less than 1850!!!");
        }
        log.info("Some user create movie.");
        return repository.save(movie);
    }

    public List<Movie> findALl() {
        return repository.findAll();
    }

    public Movie getMovieById(Long id){
        return repository.getReferenceById(id);
    }

    public List<Movie> getMoviesByTitle(String title) {
        return repository.getMovieByTitle(title);
    }

    public List<Movie> getMoviesByReleaseYear(Integer releaseYear) {
        return repository.getMovieByReleaseYear(releaseYear);
    }

    public Movie update(Long id, Movie movie) {
        log.info("Some user update movie.");
        Movie oldMovie = repository.getReferenceById(id);

        return repository.save(MovieMapper.mapMovie(oldMovie, movie));
    }

    public void delete(Long id) {
        log.warn("Some user delete movie.");
        repository.delete(repository.getReferenceById(id));
    }

    public void changeMovieForAddReviewAction(Review review) {
        Movie movie = repository.getReferenceById(review.getMovie().getId());
        double newAvgRating = movie.getAverageRating() * movie.getReviewCount();
        movie.setReviewCount(movie.getReviewCount() + 1);
        newAvgRating = (newAvgRating + review.getRating()) / movie.getReviewCount();
        movie.setAverageRating(newAvgRating);

        repository.save(movie);
    }
}
