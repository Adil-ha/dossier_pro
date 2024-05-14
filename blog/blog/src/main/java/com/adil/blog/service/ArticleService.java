package com.adil.blog.service;

import com.adil.blog.entity.Article;
import com.adil.blog.entity.User;
import com.adil.blog.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public Article createArticle(Article article) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        article.setUser(user);
        return articleRepository.save(article);
    }

    public Optional<Article> updateArticle(Long id, Article updatedArticle) {
        Optional<Article> existingArticleOptional = articleRepository.findById(id);
        if (existingArticleOptional.isEmpty()) {
            return Optional.empty();
        }
        updatedArticle.setId(id);
        return Optional.of(articleRepository.save(updatedArticle));
    }

    public boolean deleteArticle(Long id) {
        if (articleRepository.existsById(id)) {
            articleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Article> getArticlesByCategory(String categoryName) {
        return articleRepository.findByCategoryName(categoryName);
    }


}
