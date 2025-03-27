package org.ea.cinevibe.repository;

import org.ea.cinevibe.model.Comment;
import org.ea.cinevibe.model.Review;
import org.ea.cinevibe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getCommentsByUser(User user);

    List<Comment> getCommentsByReview(Review review);
}
