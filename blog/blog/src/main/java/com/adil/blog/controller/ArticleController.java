package com.adil.blog.controller;


import com.adil.blog.dto.ArticleDTO;
import com.adil.blog.entity.Article;
import com.adil.blog.mapper.DtoConverterService;
import com.adil.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private DtoConverterService dtoConverterService;

    @GetMapping
    public List<ArticleDTO> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        return articles.stream()
                .map(dtoConverterService::convertToArticleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<ArticleDTO>> getArticlesByCategory(@PathVariable String categoryName) {
        List<Article> articles = articleService.getArticlesByCategory(categoryName);
        if (articles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ArticleDTO> articleDTOs = articles.stream()
                .map(dtoConverterService::convertToArticleDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(articleDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        Optional<Article> article = articleService.getArticleById(id);
        return article.map(value -> ResponseEntity.ok(dtoConverterService.convertToArticleDTO(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        Article savedArticle = articleService.createArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article updatedArticle) {
        Optional<Article> article = articleService.updateArticle(id, updatedArticle);
        return article.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        boolean deleted = articleService.deleteArticle(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }



}
