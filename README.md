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

Em seguida, para rodar o projeto, basta executar o seguinte comando:

```sh
./gradlew bootRun (ou gradlew.bat bootRun)
```

O backend estará disponível em `http://localhost:8080`

## :white_check_mark: Testes

Para rodar todos os testes de unidade e de integração, basta executar o seguinte comando

```sh
./gradlew test (ou gradlew.bat test)
```

## :memo: Documentação

A documentação da API, seguindo a especificação OpenAPI v3, está disponível [aqui](./docs/api-docs.json) e no endereço [https://localhost:8080/docs](https://localhost:8080/docs)
quando o servidor de desenvolvimento está online.

## :camera_flash: Sobre mim

Sou João Pedro Gomes, desenvolvedor FullStack com experiência em JavaScript/TypeScript, Node.js, React.js e outros frameworks JavaScript e, graças a
[TQI](https://www.tqi.com.br/), com experiência em Kotlin e Spring. Sou formado em Licenciatura em Física no Instituto Federal do Piauí e, na
área da programação, sou autodidata. Comecei a estudar programação por conta própria em 2018 e aprendi o que sei de programação com os cursos gratuitos no YouTube e bootcamps online de instituições como
a [Rockeseat](https://www.rocketseat.com.br/) e a [DIO](https://www.dio.me). Neste mês, julho de 2023, completo 5 meses de trabalho na área da
tecnologia. Tenho paixão pelo desenvolvimento e pela tecnologia e entusiasmo em aprender coisas novas. Busco no momento atual aprimorar meus
conhecimentos e oportunidades de adquirir mais experiência profissional.

Se quiser conhecer meus trabalhos, veja mais no meu perfil do Github [aqui](https://github.com/JP-Go)

## TODO

- [x] Regra de negócio: Carrinho não pode ser pago mais de uma vez
- [x] Regra de negócio: Produtos não podem ser adicionados a carrinhos já pagos
- [x] Regra de negócio: Não pode adicionar o mesmo produto ao carrinho mais de uma vez
- [x] Regra de negócio: Um produto pode ter uma ou nenhuma categoria
- [x] Regra de negócio: Um item do carrinho corresponde a um produto e a quantidade daquele produto comprada
- [x] Regra de negócio: O consumidor deve poder alterar a quantidade comprada de um produto
- [x] Regra de negício: O consumidor deve poder remover um produto do carrinho
