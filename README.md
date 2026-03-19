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
| Lombok | Redução de boilerplate (getters, builders, etc.) |
| SpringDoc OpenAPI 2.5.0 | Geração de documentação Swagger |
| Maven | Gerenciador de dependências e build |

---

## **Arquitetura do Projeto**
A aplicação segue a arquitetura em camadas padrão do Spring Boot:

```
Controller  →  Service  →  Repository  →  Banco de Dados (PostgreSQL)
    ↑
  DTO (entrada de dados)
```

- **Controller**: recebe as requisições HTTP e delega ao Service
- **Service**: contém as regras de negócio
- **Repository**: interface com o banco de dados via Spring Data JPA
- **Entity**: classes mapeadas como tabelas do banco de dados
- **DTO**: objetos de transferência de dados para entradas nas requisições
- **Exception**: tratamento de exceções customizadas

---

## **Estrutura de Pastas**
```
vet-clinic-api/
├── src/
│   └── main/
│       ├── java/com/giullia/agenda/
│       │   ├── AgendaApiApplication.java      # Classe principal
│       │   ├── controller/                    # Controllers REST
│       │   │   ├── AgendamentoController.java
│       │   │   ├── ClienteController.java
│       │   │   └── PetController.java
│       │   ├── dto/                           # Objetos de entrada
│       │   │   ├── AgendamentoRequest.java
│       │   │   └── PetRequest.java
│       │   ├── entity/                        # Entidades JPA
│       │   │   ├── Agendamento.java
│       │   │   ├── Cliente.java
│       │   │   ├── Pet.java
│       │   │   ├── Servico.java
│       │   │   └── StatusAgendamento.java
│       │   ├── exception/                     # Exceções customizadas
│       │   │   ├── GlobalExceptionHandler.java
│       │   │   └── HorarioIndisponivelException.java
│       │   ├── repository/                    # Interfaces JPA
│       │   ├── service/                       # Regras de negócio
│       │   └── config/                        # Configurações gerais
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

### Serviço
Representa o tipo de atendimento realizado pela clínica.

| Campo | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `id` | Long | — | Identificador único |
| `nome` | String | ✅ | Nome do serviço |
| `descricao` | String | ❌ | Descrição detalhada |
| `preco` | BigDecimal | ✅ | Valor cobrado |
| `duracaoMinutos` | Integer | ✅ | Duração estimada |

### Agendamento
Relaciona um Pet a um Serviço em um horário específico.

| Campo | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `id` | Long | — | Identificador único |
| `pet` | Pet | ✅ | Pet a ser atendido |
| `servico` | Servico | ✅ | Serviço agendado |
| `dataHora` | LocalDateTime | ✅ | Data e hora do atendimento |
| `status` | StatusAgendamento | ✅ | `AGENDADO` ou `CANCELADO` |

---

## **Endpoints da API**

### Pets — `/pets`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/pets` | Cadastrar novo pet |
| `GET` | `/pets` | Listar todos os pets |
| `GET` | `/pets/{id}` | Buscar pet por ID |
| `PUT` | `/pets/{id}` | Atualizar dados do pet |
| `DELETE` | `/pets/{id}` | Remover pet |

### Clientes — `/clientes`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/clientes` | Cadastrar novo cliente |
| `GET` | `/clientes` | Listar todos os clientes |
| `GET` | `/clientes/{id}` | Buscar cliente por ID |
| `DELETE` | `/clientes/{id}` | Remover cliente |

### Agendamentos — `/agendamentos`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/agendamentos` | Criar novo agendamento |
| `GET` | `/agendamentos` | Listar todos os agendamentos |
| `PUT` | `/agendamentos/{id}/cancelar` | Cancelar agendamento |

---

## **Exemplos de Requisição**

### Cadastrar um Pet

```http
POST /pets
Content-Type: application/json

{
  "nome": "Rex",
  "especie": "Cão",
  "raca": "Labrador",
  "idade": 3,
  "peso": 28.5
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
  "peso": 28.5
}
```

---

### Cadastrar um Cliente

```http
POST /clientes
Content-Type: application/json

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
  "telefone": "11999998888"
}
```

---

### Criar um Agendamento

```http
POST /agendamentos
Content-Type: application/json

{
  "petId": 1,
  "servicoId": 2,
  "dataHora": "2025-04-10T14:00:00"
}
```

**Resposta:**
```json
{
  "id": 1,
  "pet": { "id": 1, "nome": "Rex" },
  "servico": { "id": 2, "nome": "Consulta" },
  "dataHora": "2025-04-10T14:00:00",
  "status": "AGENDADO"
}
```

---

### Cancelar um Agendamento

```http
PUT /agendamentos/1/cancelar
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

| # | Melhoria | Descrição |
|---|---|---|
| 1 | Autenticação com Spring Security | Proteção dos endpoints com autenticação de usuário |
| 2 | JWT para autenticação stateless | Tokens de acesso sem estado para escalabilidade |
| 3 | Endpoints administrativos | Rotas protegidas com controle de perfil de acesso |
| 4 | DTOs para saída de dados | Uso de DTOs de resposta para desacoplar entidades da API |
| 5 | Tratamento global de exceções | Respostas de erro padronizadas e mensagens amigáveis |
| 6 | Paginação de resultados | Suporte a `page`, `size` e `sort` nas listagens |
| 7 | Integração com frontend web responsivo | Interface web para consumo da API |

---

## **Objetivo do Projeto**
Este projeto foi desenvolvido com foco em **aprendizado prático** de desenvolvimento back-end com Java e Spring Boot. Os principais temas explorados foram:

- Estrutura e design de **APIs REST**
- **Boas práticas de arquitetura** com Spring Boot (camadas, injeção de dependência, separação de responsabilidades)
- **Modelagem de domínio** com entidades JPA e relacionamentos
- **Persistência de dados** com Spring Data JPA e PostgreSQL
- Uso de **DTOs** para entrada de dados e desacoplamento
- **Documentação da API** com SpringDoc OpenAPI / Swagger UI