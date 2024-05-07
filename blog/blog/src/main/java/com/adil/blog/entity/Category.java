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
@Table(name = "T_Categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "C_id")
    private Long id;

    @JoinColumn(name = "C_name")
    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Article> articles;

}
