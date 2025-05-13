# TechManage API – Sistema de Gerenciamento de Usuários

API RESTful desenvolvida com Java + Spring Boot como parte de um desafio técnico. O sistema permite criar, consultar, atualizar e remover usuários, com validações e persistência em banco de dados relacional (H2).

---
## Requisitos

Antes de rodar o projeto, certifique-se de ter as seguintes ferramentas instaladas:

- OpenJDK 17
- Maven 3.8.7
- Docker *(Se for rodar com container)*
- Git
---
## Tecnologias utilizadas

- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 Database (em memória)
- Bean Validation (Jakarta)
- JUnit 5 + Mockito
- Postman (para testes de API)

---

## Como rodar localmente

**Clone o repositório:**
```bash
git clone https://github.com/Fernandak/techManage.git
```

**Execute o projeto:**
```bash
./mvnw spring-boot:run
```
## 🐳 Rodar com Docker

###  Build da imagem
```bash
docker build -t techmanage-api .
```
```bash
docker run -p 8080:8080 techmanage-api
```
---

## Endpoints da API

### ➕ Criar usuário
`POST /api/users`  
Cria um novo usuário

### 📚 Listar todos os usuários
`GET /api/users`  
Retorna todos os usuários cadastrados

### 🔍 Buscar por id
`GET /api/users/{id}`  
Retorna um usuário pelo ID. Retorna erro 404 se não encontrado

### ✏️ Atualizar usuário
`PUT /api/users/{id}`  
Atualiza os dados de um usuário existente.

### 🗑️ Deletar usuário
`DELETE /api/users/{id}`  
Exclui um usuário.


## 🔗 Collection do Postman

Todos os endpoints da API já estão prontos para uso na collection:
[Clique aqui para acessar a collection](https://www.postman.com/blue-crescent-757535/workspace/tech-manage/collection/19646716-6af16ea5-22e9-40a8-aade-ae8b6687bef9?action=share&creator=19646716)


**Para acessar o H2 Console:**
```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:techmanagedb
User: sa
Password: (em branco)
```

---


## Testes

Execute todos os testes com:

```bash
./mvnw test
```

Inclui:
- Testes unitários do service
- Testes unitários do controller
- Testes de integração do controller

---