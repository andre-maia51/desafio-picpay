# Desafio Back-end PicPay Simplificado

![Java](https://img.shields.io/badge/Java-21-blue?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.3-green?logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue?logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-red?logo=apache-maven)

## 📖 Sobre o Projeto

Este repositório contém a solução para o [Desafio de Back-end proposto pela PicPay](https://github.com/PicPay/picpay-desafio-backend), desenvolvido com Java e o ecossistema Spring.

O objetivo foi construir uma API RESTful para simular transferências financeiras entre usuários, que podem ser de dois tipos: Comuns (COMMON) e Lojistas (MERCHANT). O sistema implementa regras de negócio essenciais, como a validação de saldo, a proibição de lojistas enviarem dinheiro, e a integração com serviços externos para autorização de transações e notificação de usuários.

## ✨ Features

-   **Gerenciamento de Usuários**: Criação e listagem de usuários.
-   **Transferências Financeiras**: Realização de transações entre usuários com as devidas validações.
-   **Validação de Regras de Negócio**:
    -   Usuários do tipo `MERCHANT` não podem iniciar transações.
    -   Validação de saldo insuficiente antes de efetuar a transferência.
-   **Integração com Serviços Externos**:
    -   Consulta a um serviço de autorização externo para aprovar transações.
    -   Utilização de um serviço externo para notificar os usuários após uma transação bem-sucedida.
-   **Gerenciamento de Banco de Dados com Migrations**: O schema do banco de dados é criado e versionado utilizando Flyway.
-   **Tratamento de Exceções Centralizado**: Utilização de `@RestControllerAdvice` para fornecer respostas de erro claras e padronizadas.

## 🛠️ Tecnologias Utilizadas

O projeto foi construído utilizando as seguintes tecnologias:

* **Java 21**
* **Spring Boot 3.5.3**
* **Spring Data JPA**: Para persistência de dados.
* **PostgreSQL**: Como banco de dados relacional.
* **Flyway**: Para gerenciamento e versionamento das migrations do banco de dados.
* **Maven**: Como gerenciador de dependências e build do projeto.
* **Lombok**: Para reduzir código boilerplate em entidades e DTOs.

## 🚀 Como Executar o Projeto

Siga os passos abaixo para configurar e executar o projeto em seu ambiente local.

### Pré-requisitos

-   **JDK 21** ou superior
-   **Maven 3.x**
-   **PostgreSQL** rodando em uma instância local ou via Docker

### Configuração

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/seu-usuario/seu-repositorio.git](https://github.com/seu-usuario/seu-repositorio.git)
    cd seu-repositorio
    ```

2.  **Configure o Banco de Dados:**
    -   Crie um banco de dados no PostgreSQL chamado `picpay_desafio_db`.
    -   Abra o arquivo `src/main/resources/application.properties` e, se necessário, altere as credenciais de acesso ao seu banco de dados.
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/picpay_desafio_db
        spring.datasource.username=seu-usuario-aqui
        spring.datasource.password=sua-senha-aqui
        ```

3.  **Execute a Aplicação:**
    -   Você pode executar a aplicação utilizando o Maven:
        ```bash
        mvn spring-boot:run
        ```
    -   Ou pode importar o projeto na sua IDE preferida (IntelliJ, Eclipse, etc.) e executá-lo a partir da classe principal `PicpaydesafioApplication.java`.

Ao iniciar, o Flyway criará automaticamente todas as tabelas necessárias no banco de dados.

## 📡 API Endpoints

A aplicação expõe os seguintes endpoints:

### Usuários

#### Criar um novo usuário
-   **Método**: `POST`
-   **URL**: `/users`
-   **Body (Exemplo)**:
    ```json
    {
        "firstName": "João",
        "lastName": "Silva",
        "document": "123.456.789-00",
        "balance": 500.00,
        "email": "joao.silva@email.com",
        "password": "senhaSegura123",
        "userType": "COMMON"
    }
    ```

#### Listar todos os usuários
-   **Método**: `GET`
-   **URL**: `/users`

### Transações

#### Realizar uma nova transação
-   **Método**: `POST`
-   **URL**: `/transactions`
-   **Body (Exemplo)**:
    ```json
    {
        "value": 50.00,
        "senderId": 1,
        "receiverId": 2
    }
    ```

## 📂 Estrutura do Projeto

O projeto segue uma estrutura de pacotes padrão para aplicações Spring Boot, visando a separação de responsabilidades:
-   `com.andre.picpaydesafio`
    -   `controller`: Camada responsável por expor os endpoints da API e lidar com as requisições HTTP.
    -   `domain`: Contém as entidades (`entities`), os DTOs (`dto`) e os enums (`enums`).
    -   `infra`: Configurações de infraestrutura, como o `ControllerExceptionHandler`.
    -   `repository`: Interfaces do Spring Data JPA para acesso ao banco de dados.
    -   `service`: Camada onde reside toda a lógica de negócio da aplicação.

## 👨‍💻 Autor

**André Maia**

-   [GitHub](https://github.com/andre-maia51)
-   [LinkedIn](https://www.linkedin.com/in/andre-maia-cunha/)
