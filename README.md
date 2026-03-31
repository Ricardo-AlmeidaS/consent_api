# Consents API

API REST para gerenciamento de consentimentos, construída com Spring Boot, PostgreSQL, Flyway e documentação OpenAPI/Swagger.

## Tecnologias

- Java 21
- Spring Boot 3.5.x
- Maven
- PostgreSQL 16
- Flyway
- Springdoc OpenAPI (Swagger UI)
- Testcontainers (testes)

## Pré-requisitos

- JDK 21
- Docker e Docker Compose (opcional, para subir tudo via container)
- Maven (ou usar o wrapper `./mvnw`)

## Configuração

O projeto utiliza variáveis de ambiente para o banco:

- `POSTGRES_USER`
- `POSTGRES_PASSWORD`
- `POSTGRES_DB`
- `SPRING_DATASOURCE_URL` (opcional em execução local sem Docker)
- `SPRING_DATASOURCE_USERNAME` (opcional)
- `SPRING_DATASOURCE_PASSWORD` (opcional)

Crie seu arquivo de variáveis local a partir do exemplo versionado:

No Windows (PowerShell):

```powershell
Copy-Item .env.example .env
```

No Linux/macOS:

```bash
cp .env.example .env
```

O arquivo `.env` fica ignorado no Git, e o `.env.example` serve como referência de credenciais para execução local.

## Como executar

### Docker Compose (app + PostgreSQL)

Na raiz do projeto:

```bash
docker compose up --build
```

Aplicação disponível em:

- API: `http://localhost:8099`
- Swagger UI: `http://localhost:8099/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8099/v3/api-docs`

A API sobe por padrão na porta `8099` (configurado em `src/main/resources/application.yml`).

## Endpoints principais

Base URL:

`http://localhost:8099/api`

- `POST /consents` - cria consentimento
- `GET /consents/{id}` - busca consentimento por ID
- `GET /consents` - lista consentimentos com paginação
- `PUT /consents/{id}` - atualiza status/data de expiração
- `DELETE /consents/revoke/{id}` - revoga consentimento
- `DELETE /consents/delete/{id}` - remove consentimento

## Exemplos de uso da API

### 1) Criar consentimento

URL:

`POST http://localhost:8099/api/consents`

Request:

```json
{
  "cpf": "12345678901",
  "expirationDate": "2026-12-31T23:59:59"
}
```

Resposta esperada (`201 Created`):

```json
{
  "id": "8bbd7c2d-3a6b-4f8d-9fd2-4d2f45d0a111",
  "cpf": "12345678901",
  "status": "ACTIVE",
  "date": "2026-03-31T18:30:00",
  "expiration_date": "2026-12-31T23:59:59"
}
```

### 2) Buscar consentimento por ID

URL:

`GET http://localhost:8099/api/consents/{id}`

Resposta esperada (`200 OK`):

```json
{
  "id": "8bbd7c2d-3a6b-4f8d-9fd2-4d2f45d0a111",
  "cpf": "12345678901",
  "status": "ACTIVE",
  "date": "2026-03-31T18:30:00",
  "expiration_date": "2026-12-31T23:59:59"
}
```

### 3) Listar consentimentos (paginação)

URL:

`GET http://localhost:8099/api/consents?page=0&size=10&sort=date,desc`

Request:

```json
{
  "page": 0,
  "size": 10,
  "sort": [
    "date"
  ]
}
```

Resposta esperada (`200 OK`):

```json
{
  "content": [
    {
      "id": "8bbd7c2d-3a6b-4f8d-9fd2-4d2f45d0a111",
      "cpf": "12345678901",
      "status": "ACTIVE",
      "date": "2026-03-31T18:30:00",
      "expiration_date": "2026-12-31T23:59:59"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1,
  "totalPages": 1
}
```

### 4) Atualizar consentimento

URL:

`PUT http://localhost:8099/api/consents/{id}`

Request:

```json
{
  "status": "REVOKED",
  "expiration_date": "2026-10-01T00:00:00"
}
```

Resposta esperada (`200 OK`):

```json
{
  "id": "8bbd7c2d-3a6b-4f8d-9fd2-4d2f45d0a111",
  "cpf": "12345678901",
  "status": "REVOKED",
  "date": "2026-03-31T18:30:00",
  "expiration_date": "2026-10-01T00:00:00"
}
```

### 5) Revogar consentimento por endpoint dedicado

URL:

`DELETE http://localhost:8099/api/consents/revoke/{id}`

Resposta esperada (`200 OK`):

```json
{
  "id": "8bbd7c2d-3a6b-4f8d-9fd2-4d2f45d0a111",
  "cpf": "12345678901",
  "status": "REVOKED",
  "date": "2026-03-31T18:30:00",
  "expiration_date": "2026-10-01T00:00:00"
}
```

### 6) Excluir consentimento

URL:

`DELETE http://localhost:8099/api/consents/delete/{id}`

Resposta esperada:

- `204 No Content`

## Testes

Executar testes:

No Windows:

```powershell
.\mvnw.cmd test
```

No Linux/macOS:

```bash
./mvnw test
```

## Observações

- A documentação interativa da API fica em `http://localhost:8099/swagger-ui.html`.
- Se estiver usando uma versão incompatível do `springdoc`, o Swagger pode falhar em runtime. Prefira versões atualizadas e compatíveis com o Spring Boot do projeto.
