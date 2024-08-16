package com.usoparalelo.controller.dto;

import com.usoparalelo.model.Livro;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class LivroDto {

    String titulo;
    String versao;
    LocalDateTime emprestadoAte;
    String emprestadoPara;

    public LivroDto(String titulo, String versao, String emprestadoPara, LocalDateTime emprestadoAte) {
        this.titulo = titulo;
        this.versao = versao;
        this.emprestadoAte = emprestadoAte;
        this.emprestadoPara = emprestadoPara;
    }


    public static List<LivroDto> fromList(List<Livro> livros) {
        var dtoList = new ArrayList<LivroDto>();
        for(var livro : livros){
            if(livro.getEmprestadoPara() != null)
                dtoList.add(new LivroDto(livro.getTitulo(), livro.getVersao(), livro.getEmprestadoPara().getNome(), livro.getEmprestadoAte()));
            else
                dtoList.add(new LivroDto(livro.getTitulo(), livro.getVersao(), null, null));
        }
        return dtoList;
    }
}
