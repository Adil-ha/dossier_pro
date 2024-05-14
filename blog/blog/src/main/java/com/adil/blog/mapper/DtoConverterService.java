package com.adil.blog.mapper;

import com.adil.blog.dto.ArticleDTO;
import com.adil.blog.dto.CategoryDTO;
import com.adil.blog.dto.CommentDTO;
import com.adil.blog.dto.UserDTO;
import com.adil.blog.entity.Article;
import com.adil.blog.entity.Category;
import com.adil.blog.entity.Comment;
import com.adil.blog.entity.User;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DtoConverterService {

    public ArticleDTO convertToArticleDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setImage(article.getImage());
        articleDTO.setContent(article.getContent());
        articleDTO.setCreatedAt(article.getCreatedAt());
        articleDTO.setUser(convertToUserDTO(article.getUser()));
        articleDTO.setCategories(article.getCategories().stream()
                .map(this::convertToCategoryDTO)
                .collect(Collectors.toList()));
        articleDTO.setComments(article.getComments().stream()
                .map(this::convertToCommentDTO)
                .collect(Collectors.toList()));
        return articleDTO;
    }

    public CategoryDTO convertToCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    public CommentDTO convertToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setCreatedAt(comment.getCreatedAt());
        commentDTO.setUser(convertToUserDTO(comment.getUser()));
        return commentDTO;
    }

    public UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
