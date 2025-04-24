package org.ea.cinevibe.repository;

import org.ea.cinevibe.model.Movie;
import org.ea.cinevibe.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> getReviewsByMovie(Movie movie);
}
