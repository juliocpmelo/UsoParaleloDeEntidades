package com.usoparalelo.scheduler;

import com.usoparalelo.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class DevolverLivroScheduler {

    private final LivroRepository livroRepository;

    private final HashMap<Long, LocalDateTime> emprestimoLog = new HashMap<>();
    Logger logger = LoggerFactory.getLogger(DevolverLivroScheduler.class);

    @Scheduled(fixedDelay=1000)
    public void devolverLivro(){
        var livros = livroRepository.findAllEmprestado();

        logger.info("Processando " + livros.size());
        for(var l: livros){
            if(l.getEmprestadoAte().compareTo(LocalDateTime.now()) < 0) {
                logger.info("Acabou o tempo de empréstimo do livro " + l.getTitulo());

// caso tenha descomentado em AtualizarVersaoScheduler, descomente aqui também para ter algum registro do erro
//                var lastTimestamp = emprestimoLog.get(l.getId());
//                if(lastTimestamp != null){
//                    if(l.getEmprestadoAte().compareTo(lastTimestamp) == 0)
//                        logger.error("Ops, eu já devia ter processado esse livro!!!");
//                }
//                emprestimoLog.put(l.getId(), l.getEmprestadoAte());
                l.setEmprestadoAte(null);
                l.setEmprestadoPara(null);
            }
        }

        livroRepository.saveAll(livros);
    }
}
