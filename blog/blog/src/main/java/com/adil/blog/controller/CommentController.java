package com.adil.blog.controller;


import com.adil.blog.dto.CommentDTO;
import com.adil.blog.entity.Article;
import com.adil.blog.entity.Comment;
import com.adil.blog.entity.User;
import com.adil.blog.mapper.DtoConverterService;
import com.adil.blog.service.ArticleService;
import com.adil.blog.service.CommentService;
import com.adil.blog.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
    private CommentService commentService;

    private UserService userService;

    private ArticleService articleService;

    private DtoConverterService dtoConverterService;

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Optional<Comment> comment = commentService.getCommentById(id);
        return comment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody Map<String, Object> payload) {
        Long userId = ((Number) payload.get("userId")).longValue();
        Long articleId = ((Number) payload.get("articleId")).longValue();
        String content = (String) payload.get("content");

        Optional<User> userOpt = userService.findById(userId);
        Optional<Article> articleOpt = articleService.getArticleById(articleId);

        if (userOpt.isPresent() && articleOpt.isPresent()) {
            Comment comment = Comment.builder()
                    .content(content)
                    .user(userOpt.get())
                    .article(articleOpt.get())
                    .createdAt(LocalDate.now())
                    .build();
            Comment savedComment = commentService.createComment(comment);

            // Convertir en CommentDTO
            CommentDTO commentDTO = dtoConverterService.convertToCommentDTO(savedComment);

            return ResponseEntity.status(HttpStatus.CREATED).body(commentDTO);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment updatedComment) {
        Optional<Comment> comment = commentService.updateComment(id, updatedComment);
        return comment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        boolean deleted = commentService.deleteComment(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
