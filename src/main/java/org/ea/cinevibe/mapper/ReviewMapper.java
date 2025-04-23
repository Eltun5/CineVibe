package org.ea.cinevibe.mapper;

import org.ea.cinevibe.model.Review;

import java.time.LocalDateTime;

public class ReviewMapper {
    public static Review mapReview(Review oldReview,Review newReview){
        oldReview.setContainsSpoiler(newReview.isContainsSpoiler());
        oldReview.setRating(newReview.getRating());
        oldReview.setContent(newReview.getContent());
        oldReview.setUpdatedAt(LocalDateTime.now());

        return oldReview;
    }
}
