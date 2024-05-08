package com.adil.blog;

import com.adil.blog.entity.*;
import com.adil.blog.repository.ArticleRepository;
import com.adil.blog.repository.CategoryRepository;
import com.adil.blog.repository.CommentRepository;
import com.adil.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;


@Component
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository, CategoryRepository categoryRepository, ArticleRepository articleRepository, CommentRepository commentRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;

        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        try{


            // Ajout des utilisateurs
            User user1 = User.builder()
                    .name("John Doe")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("123"))
                    .role(UserRole.ROLE_ADMIN)
                    .build();

            User user2 = User.builder()
                    .name("Bob Smith")
                    .email("bob@example.com")
                    .password(passwordEncoder.encode("123"))
                    .role(UserRole.ROLE_USER)
                    .build();

            userRepository.saveAll(Arrays.asList(user1, user2));

            // Ajout des catégories
            Category category1 = Category.builder()
                    .name("Chiens")
                    .build();

            Category category2 = Category.builder()
                    .name("Chats")
                    .build();

            Category category3 = Category.builder()
                    .name("Oiseaux")
                    .build();

            categoryRepository.saveAll(Arrays.asList(category1, category2, category3));

            // Ajout des articles
            Article article1 = Article.builder()
                    .title("Les races de chiens les plus populaires")
                    .image("dogs.jpg")
                    .content("Lorem ipsum dolor sit amet, consectetur adipiscing elit...")
                    .createdAt(LocalDate.parse("2024-05-01"))
                    .user(user1)
                    .categories(Collections.singletonList(category1))
                    .build();

            Article article2 = Article.builder()
                    .title("Conseils pour prendre soin de votre chat")
                    .image("cat.jpg")
                    .content("Lorem ipsum dolor sit amet, consectetur adipiscing elit...")
                    .createdAt(LocalDate.parse("2024-04-25"))
                    .user(user2)
                    .categories(Collections.singletonList(category2))
                    .build();

            Article article3 = Article.builder()
                    .title("Guide pour choisir le bon perroquet")
                    .image("parrot.jpg")
                    .content("Lorem ipsum dolor sit amet, consectetur adipiscing elit...")
                    .createdAt(LocalDate.parse("2024-04-20"))
                    .user(user1)
                    .categories(Collections.singletonList(category3))
                    .build();

            articleRepository.saveAll(Arrays.asList(article1, article2, article3));

            // Ajout des commentaires
            Comment comment1 = Comment.builder()
                    .content("Super article ! J'ai appris beaucoup sur les chiens.")
                    .createdAt(LocalDate.parse("2024-05-03"))
                    .user(user2)
                    .article(article1)
                    .build();

            Comment comment2 = Comment.builder()
                    .content("Merci pour ces conseils utiles.")
                    .createdAt(LocalDate.parse("2024-04-26"))
                    .user(user1)
                    .article(article2)
                    .build();

            Comment comment3 = Comment.builder()
                    .content("Je suis passionné par les perroquets, merci pour ce guide.")
                    .createdAt(LocalDate.parse("2024-04-22"))
                    .user(user2)
                    .article(article3)
                    .build();

            commentRepository.saveAll(Arrays.asList(comment1, comment2, comment3));

        }catch(DataIntegrityViolationException e){
            System.out.println("Erreur de contrainte d'unicité : " + e.getMessage());
        }

    }
}
