package com.usoparalelo.repository;

import com.usoparalelo.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("""
        select livro 
        from Livro livro 
        left join fetch livro.emprestadoPara
        where livro.biblioteca.id = :bibliotecaId
        """)
    List<Livro> findAllLivrosByBibliotecaId(Long bibliotecaId);

    @Query("""
        select livro 
        from Livro livro 
        where livro.emprestadoPara is not null
        """)
    List<Livro> findAllEmprestado();


}
