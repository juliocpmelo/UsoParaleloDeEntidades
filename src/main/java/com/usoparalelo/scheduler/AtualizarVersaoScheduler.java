package com.usoparalelo.scheduler;

import com.usoparalelo.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AtualizarVersaoScheduler {

    private final LivroRepository livroRepository;

    Logger logger = LoggerFactory.getLogger(AtualizarVersaoScheduler.class);

    @Scheduled(fixedDelay=1000)
    public void atualizaVersao(){
        var livros = livroRepository.findAll();

        for(var l: livros){
            UUID uuid = UUID.randomUUID();
            l.setVersao(uuid.toString());

// descomente aqui e adicione breakpoints para entender o problema
//            if(l.getEmprestadoAte() != null) {
//                logger.info("Forçando bug em 'atualizarversao'");
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
        }


        livroRepository.saveAll(livros);
    }
}
