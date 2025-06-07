# 🏥 VidaPlus SGHSS - Sistema de Gestão Hospitalar e Serviços de Saúde

Sistema completo de gestão hospitalar desenvolvido como parte do Projeto Multidisciplinar da Uninter, com ênfase em desenvolvimento backend utilizando Java + Spring Boot. O sistema oferece funcionalidades para gerenciar pacientes, profissionais de saúde, consultas, prontuários e autenticação com segurança baseada em JWT.

---

## 🚀 Funcionalidades

- ✅ **Gestão de Pacientes**  
  Cadastro, listagem, atualização e exclusão.

- ✅ **Gestão de Profissionais de Saúde**  
  Cadastro, listagem, atualização e exclusão.

- ✅ **Gestão de Prontuários**  
  Criação, consulta, atualização e exclusão de registros médicos.

- ✅ **Gestão de Consultas**  
  Agendamento, visualização e gerenciamento de consultas.

- 🔐 **Autenticação e Autorização**  
  Login seguro com JWT e controle de acesso baseado em papéis: `ADMIN`, `MEDICO`, `PACIENTE`.

- 📋 **Validação de Dados**  
  Validações robustas nos DTOs para garantir integridade dos dados.

- 📘 **Documentação da API**  
  Disponível via Swagger/OpenAPI para facilitar testes e integração.

---

## 🛠️ Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Security + JWT
- Spring Data JPA + Hibernate
- Lombok
- Swagger / OpenAPI
- Banco de Dados Relacional MySQL

---

## 🧪 Como Executar o Projeto

### 1. Clone o repositório

```bash
git clone https://github.com/PatrickyLucas/vida-plus-sghss.git
cd vida-plus-sghss
```

### 2. Configure o banco de dados

Edite o arquivo `src/main/resources/application.properties` com as credenciais corretas do seu banco de dados.

### 3. Compile e execute o projeto

Com Maven:

```bash
./mvnw spring-boot:run
```

### 4. Acesse a documentação da API

Abra no navegador:

```
http://localhost:8085/swagger-ui/index.html
```

---

## 🧱 Estrutura do Projeto

```
├── controller/         # Endpoints REST
├── service/            # Regras de negócio
├── model/              # Entidades JPA
├── repository/         # Interfaces de acesso a dados
├── dto/                # DTOs de entrada e saída
├── exception/          # Tratamento global de exceções
├── security/           # Configuração de segurança (JWT)
```

---

## 🔐 Segurança

- Autenticação baseada em **JWT**
- Controle de acesso por **papéis**
- Endpoints protegidos conforme o perfil do usuário

---

## 🤝 Contribuição

Contribuições são bem-vindas!  
Sinta-se à vontade para abrir **issues** ou enviar um **pull request** com melhorias.

---

## 📄 Licença

Este projeto está licenciado sob os termos da **Licença MIT**.

---

Desenvolvido com ❤️ por Patricky Lucas de Freitas
