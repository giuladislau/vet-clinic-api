# 🐾 Vet Clinic API

A **Vet Clinic API** é uma aplicação back-end que permite o gerenciamento completo de clientes, pets, serviços veterinários e agendamentos. O sistema expõe endpoints RESTful para operações de cadastro, consulta, atualização e cancelamento, com persistência em banco de dados PostgreSQL e documentação interativa via Swagger UI.

---

## **Tecnologias**

| Tecnologia | Finalidade |
|---|---|
| Java 17 | Linguagem principal |
| Spring Boot 4.0.2 | Framework base da aplicação |
| Spring Web MVC | Camada HTTP e controllers REST |
| Spring Data JPA | Persistência e mapeamento objeto-relacional |
| Spring Validation | Validação de dados de entrada |
| Spring Security | Configuração de segurança (base) |
| PostgreSQL | Banco de dados relacional |
| MapStruct | Mapeamento automático entre DTOs e Entidades |
| JWT (JSON Web Token) | Autenticação stateless |
| Lombok | Redução de boilerplate (getters, builders, etc.) |
| SpringDoc OpenAPI 2.5.0 | Geração de documentação Swagger |
| Maven | Gerenciador de dependências e build |

---

## **Arquitetura do Projeto**
A aplicação utiliza uma arquitetura orientada a domínios (feature-based) que agrupa os componentes por funcionalidade em vez das camadas MVC tradicionais:

```
Controller  →  Service  →  Repository  →  Banco de Dados (PostgreSQL)
    ↑             ↑
  DTO         MapStruct
```

- **Controller**: recebe as requisições HTTP, valida as permissões (via Spring Security) e delega ao Service.
- **Service**: contém as regras de negócio e lançamentos de exceção customizadas.
- **Repository**: interface com o banco de dados via Spring Data JPA.
- **Mapper**: classes MapStruct para conversão entre `Data Transfer Objects (DTO)` e `Entities`.
- **Entity**: classes mapeadas como tabelas do banco de dados (recurso de soft delete implementado para inativação lógica).
- **Exception**: exceções customizadas tratadas pelo `GlobalExceptionHandler`, que padroniza os erros com corpos HTTP detalhados.

---

## **Estrutura de Pastas**
```
vet-clinic-api/
├── src/
│   └── main/
│       ├── java/com/giullia/agenda/
│       │   ├── AgendaApiApplication.java      # Classe principal
│       │   ├── agendamento/                   # Feature de agendamentos
│       │   ├── auth/                          # Segurança e JWT
│       │   ├── cliente/                       # Feature de clientes
│       │   ├── common/exception/              # Tratamento global de erros
│       │   ├── config/                        # Configurações gerais (Spring Security, OpenAPI)
│       │   ├── pet/                           # Feature de pets
│       │   └── servico/                       # Feature de serviços da clínica
│       └── resources/
│           └── application.properties
└── pom.xml
```

---

## **Modelo de Domínio**

### Cliente
Representa o tutor responsável pelo pet.

| Campo | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `id` | Long | — | Identificador único |
| `nome` | String | ✅ | Nome completo do cliente |
| `email` | String | ✅ | E-mail único do cliente |
| `telefone` | String | ❌ | Telefone de contato |
| `ativo` | Boolean | ✅ | Flag de inativação lógica (soft delete) |

### Pet
Representa o animal atendido pela clínica.

| Campo | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `id` | Long | — | Identificador único |
| `nome` | String | ✅ | Nome do pet |
| `especie` | String | ✅ | Espécie (ex: cão, gato) |
| `raca` | String | ❌ | Raça do animal |
| `idade` | Integer | ❌ | Idade em anos |
| `peso` | Double | ❌ | Peso em kg |
| `cliente` | Cliente | ✅ | Tutor responsável (Lazy load) |
| `ativo` | Boolean | ✅ | Flag de inativação lógica (soft delete) |

### Serviço
Representa o tipo de atendimento realizado pela clínica.

| Campo | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `id` | Long | — | Identificador único |
| `nome` | String | ✅ | Nome do serviço |
| `descricao` | String | ❌ | Descrição detalhada |
| `preco` | BigDecimal | ✅ | Valor cobrado |
| `duracaoMinutos` | Integer | ✅ | Duração estimada |
| `ativo` | Boolean | ✅ | Flag de inativação lógica (soft delete) |

### Agendamento
Relaciona um Pet a um Serviço em um horário específico.

| Campo | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `id` | Long | — | Identificador único |
| `pet` | Pet | ✅ | Pet a ser atendido |
| `servico` | Servico | ✅ | Serviço agendado |
| `dataHora` | LocalDateTime | ✅ | Data e hora do atendimento (deve ser no futuro) |
| `status` | StatusAgendamento | ✅ | `AGENDADO` ou `CANCELADO` |

---

## **Endpoints da API**
*Nota: Todos os endpoints estão protegidos por JWT e agrupados sob o path `/api/v1`.*

### Autenticação — `/api/v1/auth`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/api/v1/auth/login` | Realizar login e obter token JWT |

### Pets — `/api/v1/pets`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/api/v1/pets` | Cadastrar novo pet |
| `GET` | `/api/v1/pets` | Listar pets (suporta paginação) |
| `GET` | `/api/v1/pets/{id}` | Buscar pet por ID |
| `PUT` | `/api/v1/pets/{id}` | Atualizar dados do pet |
| `DELETE` | `/api/v1/pets/{id}` | Remover pet (soft delete) |

### Clientes — `/api/v1/clientes`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/api/v1/clientes` | Cadastrar novo cliente |
| `GET` | `/api/v1/clientes` | Listar clientes (suporta paginação) |
| `GET` | `/api/v1/clientes/{id}` | Buscar cliente por ID |
| `PUT` | `/api/v1/clientes/{id}` | Atualizar dados do cliente |
| `DELETE` | `/api/v1/clientes/{id}` | Remover cliente (soft delete) |

### Serviços — `/api/v1/servicos`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/api/v1/servicos` | Criar novo serviço |
| `GET` | `/api/v1/servicos` | Listar serviços (suporta paginação) |
| `GET` | `/api/v1/servicos/{id}` | Buscar serviço por ID |
| `PUT` | `/api/v1/servicos/{id}` | Atualizar serviço |
| `DELETE` | `/api/v1/servicos/{id}` | Remover serviço (soft delete) |

### Agendamentos — `/api/v1/agendamentos`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/api/v1/agendamentos` | Criar novo agendamento |
| `GET` | `/api/v1/agendamentos` | Listar agendamentos (suporta paginação) |
| `PATCH` | `/api/v1/agendamentos/{id}/cancelar` | Cancelar agendamento (idempotente) |

---

## **Exemplos de Requisição**

*Lembre-se: Todas as requisições (exceto o login) exigem o header `Authorization: Bearer <seu_token_jwt>`.*

### Realizar Login

```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "admin@vetclinic.com",
  "senha": "admin"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR...",
  "tipo": "Bearer",
  "usuario": {
    "id": 1,
    "email": "admin@vetclinic.com",
    "roles": ["ROLE_ADMIN"]
  }
}
```

---

### Cadastrar um Pet

```http
POST /api/v1/pets
Content-Type: application/json
Authorization: Bearer <token>

{
  "nome": "Rex",
  "especie": "Cão",
  "raca": "Labrador",
  "idade": 3,
  "peso": 28.5,
  "clienteId": 1
}
```

**Resposta:**
```json
{
  "id": 1,
  "nome": "Rex",
  "especie": "Cão",
  "raca": "Labrador",
  "idade": 3,
  "peso": 28.5,
  "cliente": {
    "id": 1,
    "nome": "Maria Silva"
  },
  "ativo": true
}
```

---

### Cadastrar um Cliente

```http
POST /api/v1/clientes
Content-Type: application/json
Authorization: Bearer <token>

{
  "nome": "Maria Silva",
  "email": "maria@email.com",
  "telefone": "11999998888"
}
```

**Resposta:**
```json
{
  "id": 1,
  "nome": "Maria Silva",
  "email": "maria@email.com",
  "telefone": "11999998888",
  "ativo": true
}
```

---

### Criar um Agendamento

```http
POST /api/v1/agendamentos
Content-Type: application/json
Authorization: Bearer <token>

{
  "petId": 1,
  "servicoId": 2,
  "dataHora": "2026-04-10T14:00:00"
}
```

**Resposta:**
```json
{
  "id": 1,
  "pet": { "id": 1, "nome": "Rex" },
  "servico": { "id": 2, "nome": "Consulta" },
  "dataHora": "2026-04-10T14:00:00",
  "status": "AGENDADO"
}
```

---

### Cancelar um Agendamento

```http
PATCH /api/v1/agendamentos/1/cancelar
Authorization: Bearer <token>
```

**Resposta:** `200 OK` (sem corpo)

---

## **Como Executar o Projeto**

### Pré-requisitos
- Java 17+
- Maven 3.8+
- PostgreSQL instalado e rodando

### Configurar o Banco de Dados
Crie o banco de dados no PostgreSQL:

```sql
CREATE DATABASE agenda_db;
```

Ajuste as credenciais no arquivo `src/main/resources/application.properties` conforme o seu ambiente:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/agenda_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### Executar a Aplicação

```bash
# Clonar o repositório
git clone https://github.com/seu-usuario/vet-clinic-api.git
cd vet-clinic-api

# Buildar e executar com Maven
./mvnw spring-boot:run
```

A API ficará disponível em: **`http://localhost:8080`**

---

## **Documentação da API (Swagger)**
Com a aplicação em execução, acesse a documentação interativa via Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

A documentação permite visualizar todos os endpoints, modelos de dados e realizar requisições diretamente pelo navegador.

---

## **Roadmap**

| # | Melhoria | Status |
|---|---|---|
| 1 | Autenticação com Spring Security | 🟢 Concluído |
| 2 | JWT para autenticação stateless | 🟢 Concluído |
| 3 | Controle de perfil de acesso (Roles) | 🟢 Concluído |
| 4 | DTOs para entrada e saída de dados | 🟢 Concluído |
| 5 | Tratamento global de exceções | 🟢 Concluído |
| 6 | Paginação de resultados | 🟢 Concluído |
| 7 | Integração com frontend web responsivo | 🟡 Pendente |
| 8 | Testes Unitários e de Integração | 🟡 Pendente |

---

## **Objetivo do Projeto**
Este projeto foi desenvolvido com foco em **aprendizado prático** de desenvolvimento back-end com Java e Spring Boot. Os principais temas explorados foram:

- Estrutura e design de **APIs REST**
- **Boas práticas de arquitetura** com Spring Boot (camadas, injeção de dependência, separação de responsabilidades)
- **Modelagem de domínio** com entidades JPA e relacionamentos
- **Persistência de dados** com Spring Data JPA e PostgreSQL
- Uso de **DTOs** para entrada de dados e desacoplamento
- **Documentação da API** com SpringDoc OpenAPI / Swagger UI