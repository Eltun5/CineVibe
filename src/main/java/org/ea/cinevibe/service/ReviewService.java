package org.ea.cinevibe.service;

import lombok.extern.slf4j.Slf4j;
import org.ea.cinevibe.mapper.ReviewMapper;
import org.ea.cinevibe.model.Movie;
import org.ea.cinevibe.model.Review;
import org.ea.cinevibe.model.UserHelpfulReview;
import org.ea.cinevibe.repository.ReviewRepository;
import org.ea.cinevibe.repository.UserHelpfulReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ReviewService {
    private final ReviewRepository repository;

    private final MovieService movieService;

    private final UserHelpfulReviewRepository userHelpfulReviewRepository;

    private final UserService userService;

    public ReviewService(ReviewRepository repository, MovieService movieService, UserHelpfulReviewRepository userHelpfulReviewRepository, UserService userService) {
        this.repository = repository;
        this.movieService = movieService;
        this.userHelpfulReviewRepository = userHelpfulReviewRepository;
        this.userService = userService;
    }

    public Review save(Review review) {
        log.info("Some user create review.");
        movieService.changeMovieForAddReviewAction(review);
        return repository.save(review);
    }

    public Review getReviewById(Long id){
        return repository.getReferenceById(id);
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

    public Review clickedHelpful(Long reviewId, Long userId) {
        var userHelpfulReview = userHelpfulReviewRepository.
                getUserHelpfulReviewByReviewIdAndUserId(reviewId, userId);
        var review = getReviewById(reviewId);

        if (userHelpfulReview.isPresent()) {
            userHelpfulReviewRepository.delete(userHelpfulReview.get());

            review.setHelpfulCount(review.getHelpfulCount()-1);
        }else {
            var newUserHelpfulReview = UserHelpfulReview.builder()
                    .users(userService.getUserById(userId))
                    .reviews(review)
                    .build();

            userHelpfulReviewRepository.save(newUserHelpfulReview);
            review.setHelpfulCount(review.getHelpfulCount()+1);
        }
        save(review);
        return review;
    }
}
