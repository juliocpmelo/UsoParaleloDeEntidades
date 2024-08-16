package com.usoparalelo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Biblioteca {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    @OneToMany
    private Set<Livro> livros;

    public Biblioteca(String nome) {
        this.nome = nome;
    }
}

