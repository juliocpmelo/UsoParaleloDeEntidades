# Acesso Paralelo à Entidades em Bancos de dados

Este repositório mostra um exemplo simples do problema causado pelo uso em paralelo
de dados armazenados em um banco de dados.

## Clases do projeto

O projeto é composto por 3 Entidades (Biblioteca, Livro e Pessoa), a ideia é implementar
um serviço de empréstimo de livros da biblioteca.

Para que o ambiente execute com múltiplas threads, foi adicionada uma configuração do thread pool na classe
[SchedulerConfig](src/main/java/com/usoparalelo/config/SchedulerConfig.java). Essa configuração é necessária
para que os @Scheduled executem em threads paralelas ao invés de executarem em uma thread única (O padrão do spring).

Dois schedulers são usados para simular o problema do uso paralelo de entidades do banco.
O scheduler [AtualizarVersaoScheduler](src/main/java/com/usoparalelo/scheduler/AtualizarVersaoScheduler.java)
faz a atualização da versão dos livros a cada 1 segundo usando um uuid aleatório a cada execução; enquanto
o scheduler [DevolverLivroScheduler](src/main/java/com/usoparalelo/scheduler/DevolverLivroScheduler.java)
executa a cada 1 segundo o processamento dos livros que foram emprestados, para que sejam atualizados como
devolvidos.

Para facilitar a reprodução do problema o projeto tem alguns dados pré inseridos, verifique o seu banco de dados
ou a classe [SetupInicial](src/main/java/com/usoparalelo/setupInicial/SetupInicial.java) para alterar
ou adicionar novos dados.

## Executando o projeto

O projeto usa maven, para executar apenas faça `mvn clean install` e execute o jar gerado. O projeto
precisa que exista um banco de dados Postgres executando, verifique as configurações no [application.properties](src/main/resources/application.properties).
O servidor executa na porta 8080, os endpoints implementados são:

- Listar livros da biblioteca
  - Endpoint: `livros`
  - Parâmetros: `bibliotecaId`
  - Exemplo de uso: `curl --location 'localhost:8080/livros?bibliotecaId=1'`
- Emprestar livros
  - Endpoint: `emprestarLivro`
  - Parâmetros: `pessoaId`, `livroId`
  - Exemplo de uso: `curl --location --request POST 'localhost:8080/emprestarLivro?pessoaId=1&livroId=3'`

## O problema do uso do método "save"

Para simular o problema, descomente o bloco try/catch sleep a partir da linha 32 do [AtualizarVersaoScheduler](src/main/java/com/usoparalelo/scheduler/AtualizarVersaoScheduler.java).
Para visualizar melhor os momentos que o problema ocorre, opcionalmente, descomente o log a partir
da linha 30 do [DevolverLivroScheduler](src/main/java/com/usoparalelo/scheduler/DevolverLivroScheduler.java).

Após descomentados os pontos do código, use o endpoint "Emprestar Livros", até que o sistema imprima
a mensagem de erro `"Ops, eu já devia ter processado esse livro!!!"`.

## O que ocorre?

A utilização do método save, em entidades usadas em paralelo, pode causar instabilidade no estado do sistema
pois uma ação realizada por uma thread pode ser "desfeita" por outra thread de forma "transparente". O que pode
causar vários problemas em sistemas que possuem muitas entidades compartilhadas entre muitas threads.

## Possíveis soluções

Abaixo algumas soluções possíveis:

- Hibernate @DynamicSave
- Isolar entidades por feature
- Isolar uso de campos por feature
- Usar semáforos de acesso à entidades