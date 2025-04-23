package org.ea.cinevibe.service;

import lombok.extern.slf4j.Slf4j;
import org.ea.cinevibe.mapper.CommentMapper;
import org.ea.cinevibe.model.Comment;
import org.ea.cinevibe.model.Review;
import org.ea.cinevibe.model.User;
import org.ea.cinevibe.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CommentService {
    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public Comment save(Comment comment) {
        log.info("Some user create comment.");
        return repository.save(comment);
    }

    public List<Comment> getCommentsByUser(User user) {
        return repository.getCommentsByUser(user);
    }

    public List<Comment> getCommentsByReview(Review review) {
        return repository.getCommentsByReview(review);
    }

    public Comment update(Long id, Comment comment) {
        log.info("Some user update comment.");
        Comment oldComment = repository.getReferenceById(id);

        return repository.save(CommentMapper.mapComment(oldComment, comment));
    }

    public void delete(Long id) {
        log.warn("Some user delete comment.");
        repository.delete(repository.getReferenceById(id));
    }
}
