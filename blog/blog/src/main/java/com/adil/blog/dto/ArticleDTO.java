package com.adil.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
    private Long id;
    private String title;
    private String image;
    private String content;
    private LocalDate createdAt;
    private UserDTO user;
    private List<CategoryDTO> categories;
    private List<CommentDTO> comments;
}
