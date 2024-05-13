package com.adil.blog.service;


import com.adil.blog.entity.Comment;
import com.adil.blog.entity.User;
import com.adil.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public Comment createComment(Comment comment) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    public Optional<Comment> updateComment(Long id, Comment updatedComment) {
        Optional<Comment> existingCommentOptional = commentRepository.findById(id);
        if (existingCommentOptional.isEmpty()) {
            return Optional.empty();
        }
        updatedComment.setId(id); // Assure que l'ID du commentaire à mettre à jour est correctement défini
        return Optional.of(commentRepository.save(updatedComment));
    }

    public boolean deleteComment(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
