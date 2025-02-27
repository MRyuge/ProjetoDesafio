# 📌 Desafio Frontend - Angular

Este é o frontend do projeto **Desafio**, desenvolvido em **Angular** com autenticação via **JWT**, comunicação com um backend **Spring Boot** e proteção de rotas com **AuthGuard**. O projeto inclui funcionalidades como adição, edição, exclusão e busca de contatos, além de uma interface responsiva e amigável com **Bootstrap**.

---

## 🚀 Tecnologias Utilizadas

- **Angular** (versão mais recente)
- **TypeScript**
- **Bootstrap** para estilização e layout responsivo
- **RxJS** para manipulação de fluxos reativos
- **JWT (JSON Web Token)** para autenticação
- **Guards e Interceptors** para segurança das rotas
- **Reactive Forms** para validação robusta de formulários
- **Alerts do Bootstrap** para exibição de mensagens de sucesso e erro

---

## 📂 Estrutura do Projeto

```plaintext
desafio-frontend/
│── src/
│ ├── app/
│ │ ├── components/
│ │ │ ├── login/ # Tela de login
│ │ │ ├── lista-contatos/ # Tela de contatos
│ │ ├── services/
│ │ │ ├── auth.service.ts # Serviço de autenticação
│ │ ├── guards/
│ │ │ ├── auth.guard.ts # Proteção de rotas
│ │ ├── interceptors/
│ │ │ ├── auth-interceptor.service.ts # Intercepta requisições HTTP
│ │ ├── app-routing.module.ts # Rotas do sistema
│ │ ├── app.module.ts # Configuração principal
│── README.md
│── angular.json
│── package.json
│── tsconfig.json
```

---

## ✅ Pré-requisitos

Antes de começar, instale as seguintes dependências:

- **Node.js**: [Baixe e instale aqui](https://nodejs.org/)
- **Angular CLI** (caso ainda não tenha instalado):
  
  ```sh
  npm install -g @angular/cli
  ```

---

## 🛠 Como Rodar o Projeto

Clone este repositório:

```sh
git clone https://github.com/seu-usuario/desafio-frontend.git
cd desafio-frontend
```

Instale as dependências:

```sh
npm install
```

Inicie o servidor Angular:

```sh
ng serve
```

O projeto estará acessível em: [http://localhost:4200](http://localhost:4200)

---

## 🔑 Autenticação e Segurança

- O login é feito via JWT e o token é armazenado no `localStorage`.
- Todas as requisições são interceptadas para incluir o JWT.
- Somente usuários autenticados podem acessar a lista de contatos.
- O **AuthGuard** protege rotas privadas.
- A funcionalidade de logout remove o token do `localStorage` e redireciona o usuário para a página de login.

---

## 🔀 Rotas

| Rota         | Protegida? | Descrição                        |
|-------------|------------|--------------------------------|
| `/login`    | ❌ Não     | Tela de login                   |
| `/contatos` | ✅ Sim     | Lista de contatos (somente usuários logados) |

---

## 🎨 Interface do Usuário

### 🔐 Login
- Tela simples para autenticação de usuários.
- Após o login bem-sucedido, o usuário é redirecionado para a lista de contatos.

### 📇 Lista de Contatos
- **🔍 Barra de Busca**: Permite filtrar contatos por nome, email ou celular.
- **📢 Mensagens Estilizadas**: Exibe mensagens de sucesso ou erro usando Alerts do Bootstrap.
- **⚙️ Botões de Ação**: Cada contato possui botões `Editar`, `Excluir` e `Favoritar`.
- **🚪 Logout**: Um botão `Sair` no topo da página permite que o usuário faça logout.

---

## 📜 Endpoints da API (Backend)

| Método | Endpoint                | Descrição                          |
|--------|-------------------------|----------------------------------|
| `POST` | `/api/auth/login`       | Realiza login e retorna JWT.      |
| `POST` | `/api/auth/register`    | Registra um novo usuário.         |
| `GET`  | `/api/contatos`         | Retorna lista de contatos (requer JWT). |
| `POST` | `/api/contatos`         | Adiciona um novo contato.         |
| `PUT`  | `/api/contatos/:id`     | Atualiza um contato existente.    |
| `DELETE` | `/api/contatos/:id`   | Exclui um contato.                |

---

## 🛠 Possíveis Erros e Soluções

### ❌ Erro: CORS (Cross-Origin Request Blocked)

**Solução:**
No backend Spring Boot, adicione:

```java
@CrossOrigin(origins = "http://localhost:4200")
```

No `AuthController` ou configure globalmente no `WebConfig`.

### ❌ Erro: Token Inválido ou Expirado

**Solução:**
- Certifique-se de que o token JWT está sendo armazenado corretamente no `localStorage`.
- Verifique se o token está sendo enviado nas requisições via interceptor.

---

## 🎨 Personalização

Se quiser mudar as cores do tema, edite o arquivo `src/styles.scss`:

```scss
$primary-color: #007bff;
$secondary-color: #6c757d;
$background-color: #f8f9fa;
```

Você também pode personalizar os estilos dos componentes diretamente nos arquivos `.component.css`.

---

## 🚀 Melhorias Futuras

- 📌 Implementar paginação na lista de contatos.
- 🔍 Adicionar ordenação e filtros avançados.
- ✅ Criar testes unitários e de integração.
- 🔔 Substituir `alert` por notificações mais modernas (ex.: `ngx-toastr`).

---

Desenvolvido por **[Ryuge]** 🚀

