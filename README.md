# API de Gestão de Jogo Responsável

## Descrição do Projeto

Este projeto, desenvolvido como parte da sprint de `Arquitetura de Software e Web Services`, consiste em uma API RESTful para simular uma plataforma de apostas com foco em **Jogo Responsável**. O objetivo principal não é o jogo em si, mas a implementação de ferramentas de controle que ajudam a proteger o usuário, como a definição de limites de depósito e a validação de transações contra essas regras.

A aplicação permite que um usuário se cadastre estabelecendo um limite financeiro mensal e, posteriormente, simule depósitos que são validados em tempo real contra esse limite, promovendo um ambiente mais seguro e controlado.

## Tecnologias Utilizadas

* **Java 17**: Versão da linguagem de programação.
* **Spring Boot 3.x**: Framework principal para a construção da aplicação e da API REST.
* **Spring Data JPA**: Para persistência de dados e comunicação com o banco.
* **Maven**: Ferramenta de gerenciamento de dependências e build do projeto.
* **Flyway**: Ferramenta para controle de versionamento e migração do schema do banco de dados.
* **H2 Database**: Banco de dados em memória para ambiente de desenvolvimento e testes.
* **Lombok**: Biblioteca para reduzir código boilerplate (getters, setters, construtores).
* **Postman**: Ferramenta utilizada para testar e validar os endpoints da API.

## Passos de Configuração e Execução

Siga os passos abaixo para executar a aplicação em seu ambiente local.

### Pré-requisitos

* JDK (Java Development Kit) - Versão 17 ou superior.
* Maven 3.6 ou superior (ou utilize o Maven Wrapper incluído no projeto).
* Git para clonar o repositório.

### Executando a Aplicação

1.  **Clone o repositório:**
    ```bash
    git clone [URL_DO_SEU_REPOSITORIO_GIT]
    ```

2.  **Navegue até a pasta do projeto:**
    ```bash
    cd nome-do-seu-projeto
    ```

3.  **Execute a aplicação usando o Maven Wrapper:**
    * No Linux ou macOS:
        ```bash
        ./mvnw spring-boot:run
        ```
    * No Windows (CMD ou PowerShell):
        ```bash
        mvnw.cmd spring-boot:run
        ```

4.  Após a inicialização, a API estará disponível em `http://localhost:8080`.

## Exemplos de Requisições e Respostas (Endpoints)

Aqui estão exemplos de como interagir com os principais endpoints da API.

### 1. Cadastrar Novo Usuário

Cria um novo usuário e define seu limite de depósito mensal.

* **URL:** `/usuarios`
* **Método:** `POST`
* **Corpo da Requisição (Body):**
    ```json
    {
      "nome": "Ana Clara",
      "email": "ana.clara@email.com",
      "senha": "senhaForte123!",
      "limiteDepositoMensal": 300.00
    }
    ```
* **Resposta de Sucesso (Status `201 Created`):**
    ```json
    {
        "id": 1,
        "nome": "Ana Clara",
        "email": "ana.clara@email.com",
        "limiteDepositoMensal": 300.00,
        "dataCriacao": "2025-09-21T14:10:30.123456"
    }
    ```
* **Resposta de Erro (Status `409 Conflict` - Email já existe):**
    ```json
    {
        "erro": "O email informado já está em uso."
    }
    ```

### 2. Realizar um Depósito

Simula um depósito para um usuário. A transação é validada contra o limite mensal.

* **URL:** `/transacoes/deposito`
* **Método:** `POST`
* **Corpo da Requisição (Body):**
    ```json
    {
      "usuarioId": 1,
      "valor": 100.00
    }
    ```
* **Resposta de Sucesso (Status `201 Created`):**
    ```json
    {
        "id": 1,
        "usuarioId": 1,
        "tipoTransacao": "DEPOSITO",
        "valor": 100.00,
        "dataTransacao": "2025-09-21T14:12:45.123456"
    }
    ```
* **Resposta de Erro (Status `400 Bad Request` - Limite excedido):**
    ```json
    {
        "erro": "Limite de depósito mensal excedido. Limite: 300.00"
    }
    ```
