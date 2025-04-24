package org.ea.cinevibe.repository;

import org.ea.cinevibe.model.UserHelpfulReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserHelpfulReviewRepository extends JpaRepository<UserHelpfulReview, Long> {
    @Query("from user_helpful_review uhr where reviews = :reviewId and users = :userId")
    Optional<UserHelpfulReview> getUserHelpfulReviewByReviewIdAndUserId(Long reviewId, Long userId);
}
