package org.ea.cinevibe.service;

import lombok.extern.slf4j.Slf4j;
import org.ea.cinevibe.mapper.ReviewMapper;
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

    public Review save(Review review) {
        log.info("Some user create review.");
        movieService.changeMovieForAddReviewAction(review);
        return repository.save(review);
    }

    public List<Review> getReviewsByMovie(Movie movie) {
        return repository.getReviewsByMovie(movie);
    }

    public Review update(Long id, Review review) {
        log.info("Some user update review.");
        Review oldReview = repository.getReferenceById(id);

        return repository.save(ReviewMapper.mapReview(oldReview, review));
    }

    public void delete(Long id) {
        log.warn("Some user delete review.");
        repository.delete(repository.getReferenceById(id));
    }
}
