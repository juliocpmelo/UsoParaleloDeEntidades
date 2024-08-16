package com.usoparalelo.controller;

import com.usoparalelo.controller.dto.LivroDto;
import com.usoparalelo.repository.LivroRepository;
import com.usoparalelo.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BibliotecaController {

    private final LivroRepository livroRepository;
    private final PessoaRepository pessoaRepository;

    @GetMapping(value="livros")
    public List<LivroDto> getLivros(@RequestParam Long bibliotecaId){
        var livros = livroRepository.findAllLivrosByBibliotecaId(bibliotecaId);
        return LivroDto.fromList(livros);
    }

    @PostMapping(value="emprestarLivro")
    public String emprestarLivro(@RequestParam Long pessoaId, @RequestParam Long livroId){
        var livro = livroRepository.findById(livroId).get();
        var pessoa = pessoaRepository.findById(pessoaId).get();

        livro.setEmprestadoPara(pessoa);
        livro.setEmprestadoAte(LocalDateTime.now().plusSeconds(2));

        livroRepository.save(livro);

        return "emprestado " + livro.getTitulo();
    }
}
