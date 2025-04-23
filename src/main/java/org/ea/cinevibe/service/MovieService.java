package org.ea.cinevibe.service;

import lombok.extern.slf4j.Slf4j;
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
        return repository.save(movie);
    }

    public List<Movie> findALl() {
        return repository.findAll();
    }

    public List<Movie> getMoviesByTitle(String title) {
        return repository.getMovieByTitle(title);
    }

    public List<Movie> getMovieByReleaseYear(Integer releaseYear) {
        return repository.getMovieByReleaseYear(releaseYear);
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
