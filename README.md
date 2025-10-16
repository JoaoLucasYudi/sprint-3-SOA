# API de Gestão de Jogo Responsável

## Descrição do Projeto

Este projeto, desenvolvido como parte da sprint de **Arquitetura de Software e Web Services**, consiste em uma API RESTful para simular uma plataforma de apostas com foco em **Jogo Responsável**. O objetivo principal não é o jogo em si, mas a implementação de ferramentas de controle que ajudam a proteger o usuário, como a definição de limites de depósito e a validação de transações contra essas regras.

A aplicação foi aprimorada para incluir um robusto sistema de segurança utilizando **JSON Web Tokens (JWT)** para autenticação e autorização, garantindo que apenas usuários autenticados possam realizar operações financeiras.

## Features Principais

-   **Cadastro de Usuários:** Permite que novos usuários se registrem na plataforma.
-   **Controle de Limite de Depósito:** Cada usuário define um limite de depósito mensal no momento do cadastro.
-   **Validação de Transações:** Toda tentativa de depósito é validada em tempo real contra o limite mensal do usuário.
-   **Autenticação Segura com JWT:** Endpoints sensíveis são protegidos e exigem um token JWT válido para acesso.
-   **Criptografia de Senhas:** As senhas dos usuários são armazenadas de forma segura no banco de dados usando o algoritmo BCrypt.
-   **Documentação Interativa:** A API é totalmente documentada com Swagger/OpenAPI, permitindo fácil visualização e teste dos endpoints.
-   **Testes Automatizados:** O projeto conta com testes unitários e de integração para garantir a qualidade e o funcionamento das regras de negócio.

## Tecnologias Utilizadas

-   **Java 17:** Versão da linguagem de programação.
-   **Spring Boot 3.x:** Framework principal para a construção da API REST.
-   **Spring Security:** Para implementação da autenticação e autorização.
-   **JWT (JSON Web Tokens):** Para a criação de tokens de acesso stateless.
-   **Spring Data JPA:** Para persistência de dados e comunicação com o banco.
-   **Maven:** Ferramenta de gerenciamento de dependências e build.
-   **Flyway:** Ferramenta para controle de versionamento e migração do schema do banco de dados.
-   **H2 Database:** Banco de dados em memória para ambiente de desenvolvimento.
-   **Lombok:** Biblioteca para reduzir código boilerplate (getters, setters, etc.).
-   **SpringDoc OpenAPI (Swagger UI):** Para documentação automática da API.
-   **JUnit 5 & Mockito:** Para a escrita de testes unitários e de integração.

## Passos de Configuração e Execução

Siga os passos abaixo para executar a aplicação em seu ambiente local.

### Pré-requisitos

-   JDK (Java Development Kit) - Versão 17 ou superior.
-   Maven 3.6 ou superior (ou utilize o Maven Wrapper incluído no projeto).
-   Git para clonar o repositório.

### Executando a Aplicação

1.  **Clone o repositório:**
    ```shell
    git clone [URL_DO_SEU_REPOSITORIO_GIT]
    ```

2.  **Navegue até a pasta do projeto:**
    ```shell
    cd nome-do-seu-projeto
    ```

3.  **Execute a aplicação usando o Maven Wrapper:**
    -   No Linux ou macOS:
        ```shell
        ./mvnw spring-boot:run
        ```
    -   No Windows (CMD ou PowerShell):
        ```shell
        mvnw.cmd spring-boot:run
        ```

Após a inicialização, a API estará disponível em `http://localhost:8080`.

## Documentação da API (Swagger UI)

A API possui uma documentação interativa completa gerada com SpringDoc e Swagger UI. Para acessá-la, inicie a aplicação e navegue até:

**[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

Nesta interface, você pode visualizar todos os endpoints, seus parâmetros, schemas de resposta e testá-los diretamente.

## Segurança e Autenticação

A API utiliza JWT para proteger seus endpoints. O fluxo de autenticação é o seguinte:

#### 1. Obter um Token de Acesso

Envie uma requisição `POST` para o endpoint `/login` com o email e a senha do usuário cadastrado.

-   **URL:** `/login`
-   **Método:** `POST`
-   **Corpo da Requisição (Body):**
    ```json
    {
      "email": "usuario.cadastrado@email.com",
      "senha": "senhaDoUsuario"
    }
    ```

-   **Resposta de Sucesso (Status `200 OK`):**
    ```json
    {
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOi..."
    }
    ```
Copie o valor do token retornado.

#### 2. Acessar Endpoints Protegidos

Para acessar endpoints protegidos (como `/transacoes/deposito`), você deve incluir o token no cabeçalho `Authorization` de cada requisição.

-   **Header:** `Authorization`
-   **Valor:** `Bearer SEU_TOKEN_JWT_COPIADO_AQUI`

## Exemplos de Requisições (Endpoints)

#### 1. Cadastrar Novo Usuário (Público)

-   **URL:** `/usuarios`
-   **Método:** `POST`
-   **Corpo da Requisição (Body):**
    ```json
    {
      "nome": "Ana Clara",
      "email": "ana.clara@email.com",
      "senha": "senhaForte123!",
      "limiteDepositoMensal": 300.00
    }
    ```
-   **Resposta de Sucesso (Status `201 Created`):**
    ```json
    {
        "id": 1,
        "nome": "Ana Clara",
        "email": "ana.clara@email.com",
        "limiteDepositoMensal": 300.00,
        "dataCriacao": "2025-10-15T22:00:00.123456"
    }
    ```

#### 2. Realizar um Depósito (Protegido)

-   **URL:** `/transacoes/deposito`
-   **Método:** `POST`
-   **Cabeçalho (Header):** `Authorization: Bearer <seu_token_jwt>`
-   **Corpo da Requisição (Body):**
    ```json
    {
      "usuarioId": 1,
      "valor": 100.00
    }
    ```
-   **Resposta de Sucesso (Status `201 Created`):**
    ```json
    {
        "id": 1,
        "usuarioId": 1,
        "tipoTransacao": "DEPOSITO",
        "valor": 100.00,
        "dataTransacao": "2025-10-15T22:05:00.123456"
    }
    ```
-   **Resposta de Erro (Status `400 Bad Request` - Limite excedido):**
    ```json
    {
        "erro": "Limite de depósito mensal excedido. Limite: 300.00"
    }
    ```

## Testes Automatizados

O projeto possui testes unitários e de integração para garantir a qualidade e o correto funcionamento das regras de negócio e dos endpoints.

Para executar todos os testes automatizados, utilize o seguinte comando na raiz do projeto:

-   **No Linux ou macOS:**
    ```shell
    ./mvnw test
    ```
-   **No Windows:**
    ```shell
    mvnw.cmd test
    ```
