package org.ea.cinevibe.mapper;

import org.ea.cinevibe.model.Comment;

import java.time.LocalDateTime;

public class CommentMapper {
    public static Comment mapComment(Comment oldComment, Comment newComment){
        oldComment.setContent(newComment.getContent());
        oldComment.setUpdatedAt(LocalDateTime.now());

        return oldComment;
    }
}
