# gerenciador-veterinário
Spring Boot + Thymeleaf + Bootstrap + MySQL

# Gerenciador de Veterinários - TDD

Projeto desenvolvido utilizando Spring Boot e JPA, aplicando o conceito de TDD (Test Driven Development).

## Ciclos Implementados

### Ciclo 1 - Busca por Nome
- Busca exata (case sensitive)
- Busca ignore case

### Ciclo 2 - LIKE
- Busca por parte do nome
- Validação com string vazia

### Ciclo 3 - Salário
- Maior que
- Menor que
- Entre valores

### Ciclo 4 - Update
- Atualização de dados
- Validação de persistência

### Ciclo 5 - Delete (Extra)
- Exclusão de registros
- Consistência dos dados

##  Banco de Dados
Utilizado H2 em memória H2 com carga inicial via import.sql

## 🛠 Tecnologias
- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- JUnit 5
