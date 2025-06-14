<!-- ![VidaPlus Banner](https://raw.githubusercontent.com/PatrickyLucas/vida-plus-sghss/tree/main/src/docs/Monograma.png) -->

# 🏥 VidaPlus SGHSS - Sistema de Gestão Hospitalar e Serviços de Saúde

[![Java](https://img.shields.io/badge/Java-17+-red.svg?style=flat&logo=java)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat&logo=spring)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-Database-blue?logo=mysql)](https://www.mysql.com)
[![JWT](https://img.shields.io/badge/Auth-JWT-orange)](https://jwt.io)
[![Build](https://img.shields.io/badge/Build-Maven-blue)](https://maven.apache.org)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)

Sistema completo para gestão de hospitais, clínicas e profissionais de saúde, com controle de pacientes, prontuários, consultas, usuários e auditoria de ações.

> 🔧 Desenvolvido como parte do **Projeto Multidisciplinar da Uninter**, com ênfase em **desenvolvimento backend** utilizando **Java + Spring Boot**.

---

## 🚀 Funcionalidades

- Cadastro e autenticação de usuários (roles: `ADMIN`, `MEDICO`, `PACIENTE`)
- Gerenciamento de pacientes, profissionais de saúde e consultas
- Prontuário eletrônico do paciente
- Auditoria de ações (logs de operações sensíveis)
- Segurança com JWT e Spring Security
- Validações robustas e tratamento de exceções

---

## 🛠️ Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA (Hibernate)
- Spring Security (JWT)
- MySQL
- Lombok
- JUnit 5 & Mockito

---

## 📦 Instalação

### 1. Clone o repositório:

```bash
git clone https://github.com/PatrickyLucas/vida-plus-sghss.git
cd vida-plus-sghss
```

### 2. Configure o banco de dados:

- MySQL rodando localmente
- Verifique e edite o arquivo: `src/main/resources/application.properties`
- Usuário padrão: `root`
- Senha padrão: `root`
- O banco será criado automaticamente com:
  ```
  spring.jpa.hibernate.ddl-auto=create-drop
  ```

### 3. Build e execução:

```bash
 mvn spring-boot:run
```

Ou rode a classe `SghssApplication` diretamente pela sua IDE.

---

## 🔐 Autenticação

O sistema utiliza **JWT** para autenticação.

- Para acessar endpoints protegidos:
  - Faça login via `POST /api/auth/login`
  - Utilize o token retornado no header:

```http
Authorization: Bearer <seu_token>
```

---

## 📚 Exemplos de Endpoints

### 🔑 Autenticação

**Login**  
`POST /api/auth/login`

```json
{
  "username": "admin",
  "password": "senha123"
}
```

---

### 👨‍⚕️ Pacientes

**Listar pacientes**  
`GET /api/pacientes`

**Criar paciente**  
`POST /api/pacientes`

```json
{
  "nome": "João Silva",
  "cpf": "12345678901",
  "dataNascimento": "1990-01-01",
  "historicoClinico": "Sem alergias"
}
```

---

### 📁 Prontuários

**Buscar prontuário por paciente**  
`GET /api/prontuarios/{pacienteId}`

---

### 📅 Consultas

**Agendar consulta**  
`POST /api/consultas`

```json
{
  "pacienteId": 1,
  "profissionalId": 2,
  "data": "2024-07-01T10:00:00",
  "status": "AGENDADA"
}
```

---

## 🧪 Testes

Execute os testes unitários com:

```bash
 mvn test
```

---

## 📝 Auditoria

Todas as ações sensíveis são registradas na **tabela de auditoria**, contendo:

- Usuário
- Ação realizada
- Detalhes da operação
- Data e hora

---

## 👤 Autor

**Patricky Lucas**

[![GitHub](https://img.shields.io/badge/GitHub-@PatrickyLucas-181717?logo=github)](https://github.com/PatrickyLucas)

---

## 📄 Licença

Este projeto está sob a licença **MIT**.  
Consulte o arquivo [`LICENSE`](./LICENSE) para mais detalhes.

---

<p align="center"><em>Dúvidas ou sugestões? Abra uma issue ou entre em contato!</em></p>
