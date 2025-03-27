package org.ea.cinevibe.service;

import lombok.extern.slf4j.Slf4j;
import org.ea.cinevibe.model.Movie;
import org.ea.cinevibe.model.Review;
import org.ea.cinevibe.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReviewService {
    private final ReviewRepository repository;

    private final MovieService movieService;

    public ReviewService(ReviewRepository repository, MovieService movieService) {
        this.repository = repository;
        this.movieService = movieService;
    }

    public void save(Review review) {
        if (review.getRating() > 5 || review.getRating() < 1) {
            log.error("Rating is invalid!!");
            throw new RuntimeException("Rating is invalid!!");
        }
        repository.save(review);
        movieService.changeMovieForAddReviewAction(review);
    }

    public List<Review> getReviewsByMovie(Movie movie) {
        return repository.getReviewsByMovie(movie);
    }
}
