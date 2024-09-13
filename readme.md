# API REST Locadora de Ternos

Este projeto é uma API REST feito como desafio técnico para a DOIT, construída ultilizando o framework Spring Boot. A API permite realizar o cadastro, listagem, atualizar e deletar objetos do banco de dados, utilizando endpoints RESTful.

## Tecnologias utilizadas

- Java 17
- Spring Boot
- Hibernate (JPA)
- MySql
- Maven

## Pré-requisitos

Antes de rodar o projeto localmente, é necessário o download das seguintes ferramentas:

- [JDK 17](https://www.oracle.com/br/java/technologies/downloads/#java17)
- [MySql](https://dev.mysql.com/downloads/installer/)
- [Maven](https://maven.apache.org/install.html)

## Configuração do banco de dados

1. Instale o MySql e crie um banco de dados chamado 'locadoradeternos'

    create database locadoradeternos;

2. Configure usuário e senha para acessar o banco de dados. Por padrão o usuário é 'root' e a senha deixada em branco.

3. No arquivo C:\Users\joaom\Documents\LocadoraDeTernos\api-rest-locadora\src\main\resources\applicationi.properties configure da seguinte forma:

    spring.datasource.url=jdbc:mysql://localhost:3306/locadoradeternos
    spring.datasource.username=root
    spring.datasource.password=
    spring.jpa.show-sql=true
    spring.jpa.hibernate.ddl-auto=update

4. Criar a database MySql com 'create database nomeDaBase;'

5. Rodar o arquivo arquivo 'ApiRestLocadoraApplication.java'

## Endpoints da API

# Listar todos os ternos:

- URL: /api/ternos
- Método: GET
- Resposta: Retorna uma lista de todos os ternos cadastrados.

# Buscar terno pelo número de id:

- URL: /api/ternos/{id}
- Método: GET
- Resposta: Retorna o terno específico do id indicado.

# Cadastrar novo terno:

- URL: /api/ternos
- Método: POST
- Reposta: Retorna o terno cadastrado

# Atualizar terno:

- URL: api/ternos/{id}
- Método: PUT
- Resposta: Atualiza os dados do terno especificado.

# Deletar um terno:

- URL: api/ternos/{id}
- Método: DELETE
- Resposta: Deleta o terno informado e retorna true