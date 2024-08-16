package com.usoparalelo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
//@DynamicUpdate /*um dos fixes possíveis*/
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String titulo;

    private String versao;

    @ManyToOne
    private Biblioteca biblioteca;

    @ManyToOne
    private Pessoa emprestadoPara;

    private LocalDateTime emprestadoAte;
    public Livro(String titulo, String versao) {
        this.titulo = titulo;
        this.versao = versao;
    }
}
