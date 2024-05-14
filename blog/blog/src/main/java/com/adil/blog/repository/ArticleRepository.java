package com.adil.blog.repository;

import com.adil.blog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT a FROM Article a JOIN a.categories c WHERE c.name = :categoryName")
    List<Article> findByCategoryName(@Param("categoryName") String categoryName);
}

