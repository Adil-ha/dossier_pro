package com.adil.blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "T_User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "U_id")
    private Long id;

    @JoinColumn(name = "U_name")
    private String name;

    @JoinColumn(name = "U_email")
    private String email;

    @JoinColumn(name = "U_password")
    private String password;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "U_role")
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<Article> articles;
}
