# Desafio - Backend

## 📌 Descrição
Este é o backend do projeto **Desafio**, desenvolvido em **Java Spring Boot**, com autenticação **JWT**, banco de dados **PostgreSQL** (produção) e **H2** (desenvolvimento).

---

## 🛠️ **Tecnologias Utilizadas**
- **Java 17**
- **Spring Boot 3.2.2**
- **Spring Security**
- **JWT (Json Web Token)**
- **Hibernate**
- **PostgreSQL / H2**
- **Lombok**

---

## ⚙️ **Configuração do Ambiente**

### 🔹 **1. Pré-requisitos**
Certifique-se de ter instalado:
- [JDK 17](https://adoptium.net/)
- [Maven 3.8+](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/) (se for usar em produção)

---

### 🔹 **2. Configuração do Banco de Dados**
O sistema suporta **PostgreSQL** para produção e **H2** para desenvolvimento.

#### **📀 Opção 1: Banco H2 (Desenvolvimento)**
O banco de dados H2 será criado automaticamente no diretório `./data/desafiotodb`.

##### **📂 Configuração (`application.yml`)**
```yaml
spring:
  datasource:
    url: jdbc:h2:file:./data/desafiotodb;INIT=CREATE SCHEMA IF NOT EXISTS DESAFIO\;SET SCHEMA DESAFIO
    driver-class-name: org.h2.Driver
    username: sa
    password: password
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: update
  h2:
    console:
      enabled: true
      path: /desafio-h2-console
```

📄 **Acesse o H2 Console:**  
- URL: [http://localhost:8080/desafio-h2-console](http://localhost:8080/desafio-h2-console)  
- **JDBC URL:** `jdbc:h2:file:./data/desafiotodb`  
- **Usuário:** `sa`  
- **Senha:** `password`  

---

#### **📀 Opção 2: Banco PostgreSQL (Produção)**
Antes de rodar a aplicação, crie o banco **`desafio`** no PostgreSQL.

##### **📂 Configuração (`application.yml`)**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/desafio
    username: postgres
    password: admin
    driver-class-name: org.postgresql.Driver
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: update
```

---

## 🚀 **Rodando a Aplicação**

### **🔹 1. Compilar o projeto**
```bash
mvn clean install
```

### **🔹 2. Executar a aplicação**
```bash
mvn spring-boot:run
```

A API estará disponível em: **`http://localhost:8080`**  

---

## 🔑 **Autenticação e Segurança**
A aplicação utiliza **JWT (Json Web Token)** para autenticação.

### **📌 1. Cadastro de Usuário**
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "admin",
  "password": "admin"
}
```

### **📌 2. Login**
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin"
}
```
💡 **Resposta esperada (200 OK)**
```json
{
  "token": "eyJhbGciOiJIUzI1..."
}
```

---

## 📂 **Endpoints da API**

### 🔹 **Usuário**
| Método | Endpoint             | Descrição                   | Autenticação |
|--------|----------------------|----------------------------|--------------|
| POST   | `/api/auth/register` | Criar um novo usuário      | ❌ Não requer |
| POST   | `/api/auth/login`    | Autenticar e obter token   | ❌ Não requer |

### 🔹 **Contatos**
| Método | Endpoint              | Descrição                     | Autenticação |
|--------|-----------------------|-------------------------------|--------------|
| GET    | `/api/contatos`       | Listar todos os contatos     | ✅ Requer token |
| GET    | `/api/contatos/{id}`  | Buscar contato por ID        | ✅ Requer token |
| POST   | `/api/contatos`       | Criar novo contato           | ✅ Requer token |
| PUT    | `/api/contatos/{id}`  | Atualizar contato por ID     | ✅ Requer token |
| DELETE | `/api/contatos/{id}`  | Remover contato por ID       | ✅ Requer token |

📄 **Para acessar as rotas protegidas, envie o token no Header:**
```http
Authorization: Bearer SEU_TOKEN
```

---

## 💪 **Possíveis Erros e Soluções**
| Erro | Motivo | Solução |
|------|--------|---------|
| `401 Unauthorized` | Token inválido ou ausente | Verifique se o token JWT está correto |
| `403 Forbidden` | Usuário sem permissão | Confirme se o usuário tem acesso |
| `500 Internal Server Error` | Erro inesperado no servidor | Consulte os logs para mais detalhes |

---

## 🤖 **Desenvolvedor**
- **Autor:** [Ryuge](https://github.com/MRyuge)  
- **Contato:** ryuge@yahoo.com  


