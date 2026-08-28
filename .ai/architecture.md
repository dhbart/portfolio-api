# Architecture — Portfolio API

## 1. Visão geral

O `portfolio-api` é o backend da plataforma de portfólio pessoal **dhbart**.

Seu objetivo é disponibilizar, por meio de uma API REST, os dados profissionais apresentados pelo frontend Angular. A API substituirá gradualmente os arquivos estáticos atualmente utilizados pelo frontend.

```text
┌─────────────────────┐
│ Angular Portfolio   │
│ Frontend             │
└──────────┬──────────┘
           │ HTTP/JSON
           ▼
┌─────────────────────┐
│ Portfolio API        │
│ Spring Boot          │
└──────────┬──────────┘
           │ JPA/JDBC
           ▼
┌─────────────────────┐
│ PostgreSQL           │
└─────────────────────┘
```

> Este documento descreve a arquitetura atualmente implementada. A integração Angular e o painel administrativo permanecem etapas futuras.

## 2. Objetivos arquiteturais

- Separar o frontend da origem dos dados.
- Centralizar o conteúdo do portfólio em uma API.
- Persistir os dados em PostgreSQL.
- Manter uma arquitetura simples, testável e evolutiva.
- Evitar que entidades de persistência sejam expostas diretamente pela API.
- Preparar o backend para futuras funcionalidades administrativas e de IA.

## 3. Stack tecnológica

- Java 25
- Spring Boot 4.0.7
- Gradle 9.7.0
- Spring Web MVC
- Spring Data JPA
- Spring Security
- Bean Validation
- PostgreSQL
- Flyway
- Springdoc OpenAPI
- Lombok
- JUnit
- Testcontainers

## 4. Organização em camadas

O backend seguirá uma arquitetura em camadas, com responsabilidades bem definidas.

```text
HTTP Request
     ↓
Controller
     ↓
Service
     ↓
Repository
     ↓
Database
```

### Controller

Responsável por:

- receber requisições HTTP;
- validar parâmetros de entrada;
- chamar os serviços de aplicação;
- retornar DTOs e códigos HTTP apropriados.

Controllers não devem conter regras de negócio.

### Service

Responsável por:

- implementar regras de negócio;
- coordenar operações entre repositórios;
- controlar transações quando necessário;
- transformar exceções de domínio em respostas apropriadas.

### Repository

Responsável pelo acesso aos dados persistidos, utilizando Spring Data JPA.

Repositories não devem conter regras de negócio complexas.

### Entity

Representa o modelo persistido no banco de dados. As entidades JPA não devem ser utilizadas diretamente como contrato da API.

### DTO

Representa os dados de entrada e saída da API. O uso de DTOs permite evoluir o banco e a API de forma independente.

### Mapper

Responsável pela conversão entre entidades e DTOs.

### Exception

Centralizará exceções de domínio, validação e erros inesperados por meio de um tratamento global.

## 5. Estrutura de pacotes

### Definitive feature structure

Each business feature is isolated:

```text
{feature}/
├── application/
│   ├── controller/
│   ├── service/
│   ├── mapper/
│   └── dto/
├── domain/
│   ├── model/
│   └── repository/
├── infrastructure/
│   └── persistence/
│       ├── entity/
│       ├── mapper/
│       └── repository/
└── shared/
```

This structure applies to `hero`, `about`, `project`, `experience`, `certification`, and `sociallink`. Application owns orchestration and DTOs. Domain owns models and repository interfaces. Infrastructure owns JPA entities, persistence mappers, and repository implementations. Global cross-cutting concerns remain outside feature packages.

Dependency direction is `application -> domain <- infrastructure`. Domain never depends on infrastructure.

### Current implementation status

The `hero`, `about`, `project`, `technology`, `experience`, `certification`, and `sociallink` features are implemented using the definitive structure. PostgreSQL is provided locally through Docker Compose, and Flyway owns their schema migrations and initial data. Project, Technology, Experience, Certification, and SocialLink data are seeded from the available portfolio sources and exposed through read-only endpoints ordered by their `displayOrder`.

Technology is an independent aggregate. Project-to-Technology is modeled through the explicit `ProjectTechnology` relationship, persisted with two `ManyToOne` associations rather than an implicit JPA `ManyToMany`. This preserves relationship ordering, allows relationship-specific rules later, and avoids coupling Technology to Project so other bounded contexts can reuse it.

The backend owns shared static assets. Technology icons are vendored from Devicon under `src/main/resources/static/icons/technologies` and are publicly served by Spring Boot at `/icons/technologies/{slug}.svg`. Technology exposes only its canonical `slug`; clients resolve the asset path using their configured API base URL. The `social` and `certifications` categories are reserved under the same static asset root for future shared assets.

### Localization foundation

`Hero`, `About`, `Project`, `Experience`, and `Certification` persist a `locale VARCHAR(10)`. The public endpoints keep their existing paths and consume `Accept-Language`; locale is never placed in the URL. Controllers forward the header, services own locale resolution, and repositories execute locale predicates (`findBy...AndLocale` / `findAllByLocale...`) so languages are not loaded and filtered in memory.

Supported locales are `pt-BR`, `en-US`, and `es-ES`. If a requested supported locale has no content, services fall back to `en-US`, then `pt-BR`. Unsupported or malformed headers follow the same fallback chain and never produce a localization-related HTTP 500. Flyway `V14` adds and backfills locale columns; `V15` seeds all three locales for every localized aggregate; `V16` supplies the Spanish translations for the localized portfolio content.

Experience exposes the complete professional-history content currently maintained by the frontend: company, location, period, position, summary, detailed description paragraphs, highlights, technologies, normalized dates, current-position status, and display order. The collection endpoint is `GET /api/v1/experiences`.

Certification exposes the portfolio's educational and professional achievements through `GET /api/v1/certifications`, ordered by `displayOrder` ascending. The single Certification aggregate uses `CertificationType` to distinguish degrees, MBA, bootcamps, courses, and professional certifications.

SocialLink exposes the active contact links currently maintained by the frontend: label, display value, URL, icon, and display order. The initial seed contains LinkedIn and GitHub; the commented-out email link is not persisted. The collection endpoint is `GET /api/v1/social-links`.

### OrganizaÃ§Ã£o vigente por feature

O cÃ³digo Ã© agrupado por feature dentro de trÃªs Ã¡reas:

```text
application/{feature}       Controllers, services, mappers e DTOs
domain/{feature}            Modelos e regras de negÃ³cio
infra/config                ConfiguraÃ§Ãµes tÃ©cnicas
infra/exception             Tratamento tÃ©cnico de exceÃ§Ãµes
infra/persistence/{feature} RepositÃ³rios e implementaÃ§Ãµes de persistÃªncia
```

Novas features devem seguir essa organizaÃ§Ã£o. A estrutura global por camada descrita abaixo Ã© histÃ³rica e nÃ£o deve ser usada para novos cÃ³digos.

```text
src/main/java/dhbart/portfolioapi/
├── config/
├── controller/
├── dto/
├── entity/
├── exception/
├── mapper/
├── repository/
└── service/
```

Essa estrutura poderá ser refinada por domínio caso o projeto cresça significativamente. Por exemplo:

```text
project/
├── ProjectController.java
├── ProjectService.java
├── ProjectRepository.java
├── Project.java
└── dto/
```

Por enquanto, a organização por camada mantém a estrutura simples durante a fase inicial.

## 6. Domínio inicial

O modelo de dados será construído incrementalmente. Os principais domínios planejados são:

```text
Hero
About
Project
Technology
Experience
Certification
SocialLink
```

### Relacionamentos planejados

- Um `Project` pode utilizar várias `Technology`.
- Uma `Technology` pode estar associada a vários `Project`.
- Um `Project` poderá possuir links, imagem, descrição e informações técnicas.
- `Certification` armazenará os certificados profissionais e acadêmicos.

A relação entre projetos e tecnologias é modelada como N:N por meio da entidade explícita `ProjectTechnology`, evitando armazenar listas de tecnologias diretamente em uma coluna textual.

```text
Project ────< ProjectTechnology >──── Technology
```

Persistence features contain the JPA `FeatureEntity` and its `FeatureRepository`. Domain features contain persistence-agnostic models only. Mappers in the application layer translate `Entity -> Domain -> Response DTO` and `Request DTO -> Domain -> Entity` when write use cases are introduced.

Domain models use Lombok to reduce construction/accessor boilerplate and Jakarta Validation to express applicable business data constraints. JPA, Hibernate, and Spring Data annotations remain restricted to `{feature}/infrastructure/persistence`.

### Coding annotation standard

Annotations are added only when they express a real requirement. Jakarta Validation annotations such as `@NotBlank`, `@NotNull`, `@Email`, `@Pattern`, and `@Positive` must represent actual business invariants; they must not be generated automatically or added merely because a field is a `String`.

For each domain, validation is selected field by field from confirmed business rules. Persistence audit fields such as `createdAt` and `updatedAt` remain in infrastructure entities unless the business explicitly requires them in the domain.

Lombok annotations must remain minimal. Prefer `@Getter`, `@Setter`, `@NoArgsConstructor`, and `@AllArgsConstructor` when they provide value. `@Data` is prohibited, and `@Builder` is used only when object construction clearly benefits from it.

## 7. Banco de dados e migrations

Spring Boot projects using PostgreSQL and Flyway must include both the Flyway starter and the PostgreSQL-specific Flyway database module. Datasource URLs must use the `jdbc:postgresql://` scheme. Flyway owns schema creation; JPA must use `ddl-auto: validate`.

Local development uses Docker Compose. Integration tests use Testcontainers independently from Compose and must verify that the application context, Flyway migrations, persistence adapter, and database-backed use case start successfully.

O banco de dados escolhido é o PostgreSQL.

As alterações do schema deverão ser versionadas com Flyway. Cada alteração relevante deverá possuir uma migration própria, por exemplo:

```text
V1__create_hero_table.sql
V3__create_project_table.sql
V4__create_technology_table.sql
```

Regras:

- não alterar manualmente migrations já executadas;
- utilizar novas migrations para corrigir ou evoluir o schema;
- manter nomes de tabelas e colunas consistentes;
- adicionar índices quando houver necessidade comprovada de consulta;
- definir constraints para preservar a integridade dos dados.

## 8. API REST

Todos os endpoints públicos utilizarão o prefixo `/api`.

Endpoints planejados:

```http
GET /api/hero
GET /api/about
GET /api/v1/projects
GET /api/v1/projects/{slug}
GET /api/v1/experiences
GET /api/v1/social-links
GET /api/v1/certifications
GET /api/v1/technologies
```

Localized endpoints accept, for example, `Accept-Language: en-US` on the existing resource URLs.

Project responses include a `technologies` collection of `TechnologyResponse` objects. The collection is ordered by the association's `displayOrder`; the technology catalogue is independently ordered by Technology `displayOrder`. `Technology.slug` is the canonical identifier for resolving its backend-owned icon; icon URLs and filenames are not part of the API contract.

Operações de escrita, como `POST`, `PUT` e `DELETE`, serão adicionadas posteriormente para o painel administrativo.

### Convenções

- Utilizar JSON como formato de entrada e saída.
- Usar nomes de recursos no plural quando aplicável.
- Utilizar `slug` para URLs públicas de projetos.
- Retornar códigos HTTP semanticamente adequados.
- Validar payloads com Bean Validation.
- Padronizar respostas de erro.
- Não expor detalhes internos ou stack traces em produção.

## 9. Segurança

O projeto possui Spring Security como preparação para as funcionalidades administrativas.

Diretriz atual:

```text
Conteúdo público
    ↓
GET sem autenticação

Operações administrativas
    ↓
POST / PUT / DELETE protegidos
```

A API atual permanece pública: endpoints GET, health check, Swagger UI e OpenAPI não exigem autenticação. JWT e login serão introduzidos somente quando endpoints administrativos forem criados.

## 10. Tratamento de erros

A API possui um tratamento global de exceções com respostas consistentes:

```json
{
  "timestamp": "2026-08-18T12:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Project not found",
  "path": "/api/projects/example"
}
```

Mensagens internas, credenciais e stack traces não devem ser retornados ao cliente em ambiente de produção.

## 11. Testes

O projeto utilizará diferentes níveis de teste:

- testes unitários para regras de negócio;
- testes de integração para repositories e banco de dados;
- testes da camada web para controllers;
- Testcontainers para validar o comportamento com PostgreSQL real;
- testes de segurança para endpoints protegidos.

Todo novo domínio deverá, idealmente, incluir testes para o service, repository e controller conforme sua complexidade.

## 12. Documentação

A documentação da API é disponibilizada por Springdoc. Em ambiente local, Swagger UI está em `/swagger-ui/index.html` e o documento JSON em `/v3/api-docs`. Os endpoints GET atuais são descobertos automaticamente.

Além deste documento, mudanças arquiteturais relevantes deverão ser registradas no próprio projeto, preferencialmente por meio de decisões arquiteturais documentadas.

## 13. Evolução planejada

### V2.1 — API e persistência

- configurar PostgreSQL;
- criar migrations;
- implementar os domínios do portfólio;
- disponibilizar endpoints de leitura;
- integrar o frontend Angular;
- remover progressivamente os dados estáticos.

### V2.2 — Assistente de IA

Adicionar um assistente especializado em responder perguntas sobre a experiência, projetos e qualificações de Daniel, utilizando uma base de conhecimento controlada.

### V2.3 — Administração

Adicionar painel administrativo para gerenciar o conteúdo do portfólio sem editar o código-fonte.

## 14. Decisões e princípios

- O backend será mantido em um repositório separado do frontend.
- PostgreSQL será a fonte persistente dos dados.
- A API será RESTful e baseada em JSON.
- O conteúdo público será separado das operações administrativas.
- A implementação será incremental, começando pelo domínio `Hero`.
- A simplicidade atual deve ser preservada até que a complexidade real justifique novas abstrações.
### Developer tooling: icon synchronization

Technology icons are developer-managed static assets, not runtime data. The
support tool under support/icons is split into provider, downloader, storage,
synchronizer, and cli responsibilities. DeviconProvider resolves the
canonical Technology.slug to a Devicon URL; the downloader only handles HTTP
and SVG validation; storage only writes local files; and the synchronizer
coordinates these ports and produces a report.

The Gradle task syncIcons reads technology slugs from the current Flyway
technology seed without starting Spring Boot or connecting to PostgreSQL. This
keeps synchronization a manual developer action and prevents any runtime
download or external icon URL from entering the application contract. The
resulting SVG files are versioned under
src/main/resources/static/icons/technologies and served by the existing
backend-owned static asset contract.

The seed reader is intentionally isolated so it can later be replaced by a
database-independent catalog source if technology data moves out of Flyway.
Devicon does not provide an icon for every portfolio concept, so unsupported
slugs are reported for follow-up instead of being assigned misleading assets.

### Backend testing architecture

Sprint T1 completed the backend testing foundation. Unit tests cover business behavior in services and locale resolution. Repository integration tests use independent PostgreSQL Testcontainers databases, production Flyway migrations, and Hibernate schema validation. Controller integration tests use `@SpringBootTest` with MockMvc against the real application context.

The integration suite verifies all portfolio features, localized queries and fallback behavior, ordering, project slugs, ProjectTechnology foreign-key relationships, seed integrity, error contracts, CORS and OPTIONS handling, public security rules, actuator health, and actual Caffeine cache population. Tests are organized under `src/test/java` by feature and responsibility. DTOs, entities, generated Lombok/MapStruct code, and repository mocks in repository tests are intentionally excluded.

Services use an empty-string cache key when `Accept-Language` is omitted. This preserves the public fallback behavior while avoiding unsupported null keys in Caffeine. Cache entries expire after ten minutes and are bounded to 256 entries.

## 15. Knowledge Platform architecture

The portfolio backend is evolving into a Knowledge Platform. AI concerns are isolated from portfolio persistence features and separated into ingestion, storage, retrieval, and generation:

```text
Knowledge Sources (PDF, DOCX, Markdown)
        ↓
Local n8n ingestion → Chunking → Embeddings
        ↓
Supabase pgvector → Portfolio API → Retrieval Layer → RetrievalService
        ↓
Spring AI → OpenAI → Angular Chat
```

Ingestion and storage are external to the production backend. The backend consumes relevant indexed knowledge through the independent Retrieval Layer and delegates natural-language generation to Spring AI and OpenAI.

### Retrieval Layer

The existing `assistant/retrieval` package is the boundary between `AssistantService` and the future vector store:

```text
AssistantService
       ↓
RetrievalService
       ↓
KnowledgeRepository
       ↓
Future vector-store adapter
       ↓
Supabase pgvector
```

`KnowledgeChunk` represents indexed knowledge without JPA or provider-specific types. `KnowledgeRetrievalRepository` owns the pgvector SQL, while `RetrievalService` orchestrates embedding and retrieval. `RetrievalProperties` centralizes `top-k` and maximum context length.

V3.2 prepares these contracts only. There is no vector search, pgvector query, embedding generation, RAG context assembly, or document ingestion in the backend. PDF, DOCX, and Markdown flow through local n8n, chunking, embedding generation, and Supabase before production consumes the resulting indexed knowledge.

# Architecture

## Sprint 4.0 — Hybrid Retrieval

Chat retrieval now uses two independent sources. `HybridRetrievalService` is the only retrieval entry point used by chat and orchestrates `StructuredRetrievalService` (existing feature application services backed by PostgreSQL) before `VectorRetrievalService` (the existing embedding and pgvector path).

```text
AssistantService -> ChatService -> HybridRetrievalService
                                  ├─ StructuredRetrievalService -> portfolio services -> PostgreSQL
                                  └─ VectorRetrievalService -> RetrievalService -> pgvector
                                  -> ContextBuilder -> Context -> PromptBuilder -> OpenAI
```

Structured retrieval uses lightweight question terms to select only relevant aggregates. It prioritizes profile, experience, projects, certifications, technologies, and social links for factual portfolio questions. Vector retrieval remains complementary for descriptive knowledge such as ERP, leadership, migrations, CV material, and notes. Structured sections are inserted first; duplicate vector content is discarded before prompt assembly.

`Context` is source-neutral. `PromptBuilder` receives the assembled object and only formats ordered sections and the bounded context; it does not know how data was stored. Retrieval failures are isolated: one successful branch is sufficient, while both failures are propagated as a retrieval error. Retrieval logs contain counts and timings, never credentials or prompt contents.

The design keeps future providers extensible: a web, GitHub, blog, or LinkedIn provider can be added to the hybrid orchestration without changing prompt generation. The trade-off is a small keyword classifier rather than a second LLM/router call; this avoids extra latency, embeddings, and SQL reads while remaining easy to extend.

## Sprint 3.3 — AI Processing Service

Document ingestion remains the responsibility of n8n. After n8n stores a document and its chunks, it calls the backend processing endpoint. The backend owns provider access, embedding persistence, status transitions and error recovery.

```text
n8n
  ↓
knowledge.documents
knowledge.chunks (embedding IS NULL)
  ↓ POST /api/v1/admin/knowledge/process/{documentId}
Spring Boot Processing Service
  ↓
OpenAI Embeddings (text-embedding-3-small)
  ↓
knowledge.chunks.embedding (pgvector)
  ↓
Chat API (future retrieval integration)
```

`KnowledgeProcessingService` depends only on `EmbeddingService` and `KnowledgeProcessingRepository`. `OpenAiEmbeddingService` is the sole OpenAI embeddings adapter, so a future Azure OpenAI, OpenRouter or Ollama adapter can replace it without changing orchestration or persistence.

## Sprint 3.4 — Retrieval (RAG)

The implemented RAG path preserves the assistant boundaries:

```text
User
  ↓
AssistantController
  ↓
ChatService
  ↓
RetrievalService
  ↓
EmbeddingService
  ↓
KnowledgeRetrievalRepository → pgvector
  ↓
PromptBuilder
  ↓
OpenAI
  ↓
Response
```

All vector SQL is owned by `KnowledgeRetrievalRepository`. Retrieval returns chunks in similarity order, and `PromptBuilder` stops context assembly at `assistant.ai.retrieval.max-context-length`. Empty retrieval adds the internal `No relevant knowledge was found.` notice without failing the request.

The endpoint returns `202 Accepted` and processing runs asynchronously. Existing embeddings are never regenerated. A provider or persistence error marks the document `FAILED`, records `error_message` and sets `processing_finished_at`.

## Security Hardening Sprint 14

Spring Security applies a stateless API-key boundary to every `/api/v1/admin/**` request. Clients must provide `X-API-KEY`; the expected value is bound from `portfolio.security.admin-api-key` and can be overridden with `PORTFOLIO_ADMIN_API_KEY`. Missing, blank, or invalid keys return HTTP 401. Validation lives in the reusable `AdminApiKeyFilter`, not in controllers, and compares key bytes in constant time.

The portfolio resources, `/icons/**`, Swagger/OpenAPI, `/actuator/health`, and `POST /api/v1/assistant/chat` remain public. Basic Authentication, JWT, OAuth, rate limiting, and abuse protection remain deferred.
