# Self-checkout -- JuMarket backend

Backend do sistema de auto-atendimento para o JuMarket. (Projeto de solução ao desafio TQI Bootcamp Kotlin BE Developer)

## :hammer_and_wrench: Tecnologias

- Linguagem Kotlin
- Spring Web MVC
- Spring Data Jpa
- Flyway
- Docker & Docker compose
- Gradle
- MariaDB

## :zap: Rodando o projeto

Para iniciar o projeto primeiro é necessário um banco de dados MariaDB rodando. Fornecemos um arquivo
docker-compose para provisionar um container docker para o banco de dados. Vá para a raiz do projeto e
execute o seguinte comando no terminal para provisionar o container:

```sh
docker compose up -d
```

Em seguida, para rodar o projeto, basta rodar o seguinte comando:

```sh
./gradlew bootRun (ou gradlew.bat bootRun)
```

O backend estará disponível em `http://localhost:8080`
