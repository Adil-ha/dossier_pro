package com.adil.blog.controller;


import com.adil.blog.entity.Article;
import com.adil.blog.mapper.DtoConverterService;
import com.adil.blog.service.ArticleService;
import com.adil.blog.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ArticleController.class)
@AutoConfigureMockMvc(addFilters = false)
class ArticleControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private DtoConverterService dtoConverterService;

    @MockBean
    private UserService userService;

    @Test
    void getAllArticles_ReturnsListOfArticles() throws Exception {
        Article article = new Article();
        article.setId(1L);
        article.setTitle("Test Article");
        article.setContent("Test Content");

        when(articleService.getAllArticles()).thenReturn(Collections.singletonList(article));

        mockMvc.perform(MockMvcRequestBuilders.get("/articles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Test Article"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("Test Content"))
                .andDo(print());
    }

    @Test
    void getArticlesByCategory_ReturnsListOfArticlesByCategory() throws Exception {
        Article article = new Article();
        article.setId(1L);
        article.setTitle("Test Article");
        article.setContent("Test Content");

        when(articleService.getArticlesByCategory("test")).thenReturn(Collections.singletonList(article));

        mockMvc.perform(MockMvcRequestBuilders.get("/articles/category/test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Test Article"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("Test Content"));
    }

    @Test
    void getArticleById_ReturnsArticleById() throws Exception {
        Article article = new Article();
        article.setId(1L);
        article.setTitle("Test Article");
        article.setContent("Test Content");

        when(articleService.getArticleById(1L)).thenReturn(Optional.of(article));

        mockMvc.perform(MockMvcRequestBuilders.get("/articles/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Article"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Test Content"));
    }

    @Test
    void createArticle_CreatesNewArticle() throws Exception {
        Article article = new Article();
        article.setId(1L);
        article.setTitle("Test Article");
        article.setContent("Test Content");

        when(articleService.createArticle(any(Article.class))).thenReturn(article);

        mockMvc.perform(MockMvcRequestBuilders.post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(article)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Article"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Test Content"));
    }

    @Test
    void updateArticle_UpdatesExistingArticle() throws Exception {
        Article article = new Article();
        article.setId(1L);
        article.setTitle("Updated Article");
        article.setContent("Updated Content");

        when(articleService.updateArticle(1L, article)).thenReturn(Optional.of(article));

        mockMvc.perform(MockMvcRequestBuilders.put("/articles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(article)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated Article"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Updated Content"));
    }

    @Test
    void deleteArticle_DeletesArticleById() throws Exception {
        when(articleService.deleteArticle(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/articles/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(articleService).deleteArticle(1L);
    }

}