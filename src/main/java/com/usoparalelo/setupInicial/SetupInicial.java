package com.usoparalelo.setupInicial;

import com.usoparalelo.model.Biblioteca;
import com.usoparalelo.model.Livro;
import com.usoparalelo.model.Pessoa;
import com.usoparalelo.repository.BibliotecaRepository;
import com.usoparalelo.repository.LivroRepository;
import com.usoparalelo.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class SetupInicial implements CommandLineRunner {

    private final PessoaRepository pessoaRepository;

    private final LivroRepository livroRepository;

    private final BibliotecaRepository bibliotecaRepository;

    @Override
    public void run(String... args) throws Exception {
        var biblioteca = bibliotecaRepository.save(new Biblioteca("Zilma Mamede"));
        // Load initial data into the database
        var pessoa = pessoaRepository.save(new Pessoa("Julio"));
        var livros = livroRepository.saveAll(List.of(
                new Livro("Livro 1", "1"),
                new Livro("Livro 2", "1"),
                new Livro("Livro 3", "1"),
                new Livro("Livro 4", "1"),
                new Livro("Livro 5", "1"),
                new Livro("Livro 6", "1"),
                new Livro("Livro 7", "1"),
                new Livro("Livro 8", "1")
        ));
        biblioteca.setLivros(livros.stream().collect(Collectors.toSet()));
        bibliotecaRepository.save(biblioteca);

        livros.forEach( livro -> livro.setBiblioteca(biblioteca));
        livroRepository.saveAll(livros);
    }
}
