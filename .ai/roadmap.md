# Roadmap — Portfolio API

## Visão do produto

Transformar o portfólio pessoal **dhbart** em uma plataforma full stack, com conteúdo persistido em banco de dados, API REST, integração com Angular, assistente de IA e painel administrativo.

```text
V1
Portfólio frontend com dados estáticos
        ↓
V2.1
API REST + PostgreSQL
        ↓
V2.2
Assistente de IA especializado
        ↓
V2.3
Painel administrativo
```

## Status atual

Sprint T1 — Backend Testing Phase: **concluída**. The backend now has unit, PostgreSQL/Flyway repository, controller, localization, security, CORS, health, and Caffeine cache integration coverage. The next product phase remains Angular integration.

Arquitetura definitiva: cada feature possui `application`, `domain`, `infrastructure` e `shared`; repositÃ³rios de domÃ­nio sÃ£o implementados pela infraestrutura.

Os modelos de domÃ­nio seguem ADR-018: Lombok e Jakarta Validation sÃ£o permitidos no domÃ­nio; JPA permanece restrito Ã  infraestrutura.

Arquitetura vigente: organizaÃ§Ã£o por feature com as Ã¡reas `application`, `domain` e `infra`, conforme ADR-016.

**Fase atual:** Platform Hardening concluído; próxima etapa: integração Angular.

Já concluído:

- [x] Criar o projeto Spring Boot;
- [x] Configurar Java 25;
- [x] Configurar Gradle 9.7.0;
- [x] Adicionar dependências de web, persistência, segurança, validação, migrations, OpenAPI e testes;
- [x] Criar `../README.md`;
- [x] Criar `architecture.md`;
- [x] Criar este roadmap.

---

## V2.1 — API e persistência

### Objetivo

Substituir os dados estáticos do frontend Angular por dados fornecidos pelo `portfolio-api` e persistidos em PostgreSQL.

### Fase 1 — Fundação do projeto

Status: **concluída**

- [x] Criar o projeto Spring Boot;
- [x] Configurar Java 25 e Gradle 9.7.0;
- [x] Configurar o nome da aplicação;
- [x] Adicionar dependências essenciais;
- [x] Criar teste inicial de contexto;
- [x] Documentar a arquitetura e o roadmap.

Critério de conclusão:

- a aplicação compila;
- o contexto do Spring inicia;
- o teste inicial passa.

### Fase 2 — Configuração de ambiente

Status: **concluída**

- [x] Criar configuração para PostgreSQL;
- [x] Definir variáveis de ambiente;
- [x] Criar perfil local;
- [x] Criar perfil de testes;
- [x] Definir configurações para desenvolvimento e produção;
- [x] Configurar CORS para o frontend Angular;
- [x] Adicionar health check da aplicação.

Critério de conclusão:

- a aplicação inicia com uma configuração local documentada;
- credenciais não ficam versionadas;
- a conexão com PostgreSQL é validada automaticamente.

### Fase 3 — Banco de dados e migrations

Status: **concluída**

- [x] Configurar o Flyway;
- [x] Criar a migration inicial;
- [x] Criar tabelas do domínio;
- [x] Definir chaves primárias e estrangeiras;
- [x] Definir índices necessários;
- [x] Definir constraints de integridade;
- [x] Criar dados iniciais para desenvolvimento.

Ordem inicial sugerida:

```text
Hero
About
Technology
Project
ProjectTechnology
Experience
Certification
SocialLink
```

Critério de conclusão:

- um banco vazio pode ser criado apenas executando as migrations;
- o schema pode ser recriado de forma previsível;
- os dados iniciais são reproduzíveis.

### Fase 4 — Primeiro domínio: Hero

Status: **concluída**

- [x] Criar entidade `HeroEntity`;
- [x] Criar migration de `Hero`;
- [x] Criar contrato `HeroRepository` no domínio e implementação na infraestrutura;
- [x] Criar DTO de resposta;
- [x] Criar mappers de aplicação e persistência;
- [x] Criar `HeroService`;
- [x] Criar `HeroController`;
- [x] Implementar `GET /api/v1/hero`;
- [x] Criar testes unitários;
- [x] Criar teste de integração com Testcontainers.

Critério de conclusão:

```http
GET /api/hero
```

retorna os dados persistidos no PostgreSQL com contrato documentado e testes automatizados.

### Fase 5 — Demais domínios

Status: **em andamento** (Certification e SocialLink concluídos)

Implementar progressivamente:

- [x] `About`;
- [x] `Technology`;
- [x] `Project`;
- [x] relacionamento `ProjectTechnology`;
- [x] `Experience`;
- [x] `Certification`;
- [x] `SocialLink`.

Sprint concluída — `SocialLink`:

- criada a estrutura feature-first de aplicação, domínio e infraestrutura;
- criada a migration `V8__create_and_seed_social_link_table.sql`;
- adicionados os links ativos de LinkedIn e GitHub do catálogo do frontend;
- implementado o endpoint `GET /api/v1/social-links`, ordenado por `displayOrder`.

Sprint concluída — `Project`:

- criada a estrutura feature-first de aplicação, domínio e infraestrutura;
- criada a migration `V3__create_project_table.sql`;
- adicionados os cinco projetos iniciais do catálogo do frontend;
- implementados os endpoints `GET /api/v1/projects` e `GET /api/v1/projects/{slug}`;
- a listagem é ordenada por `displayOrder`.

Sprint concluída — `Experience`:

- criada a estrutura feature-first de aplicação, domínio e infraestrutura;
- criadas as migrations `V5__create_experience_table.sql`, `V6__insert_data_experience_table.sql` e `V7__expand_experience_content.sql`;
- adicionadas as quatro experiências profissionais reais do catálogo do frontend;
- implementado o endpoint `GET /api/v1/experiences`;
- a listagem é ordenada por `displayOrder` descendente;
- preservados localização, período, resumo, descrições detalhadas, destaques e tecnologias.

Sprint concluída — `Certification`:

- criada a estrutura feature-first de aplicação, domínio e infraestrutura;
- criada a enum `CertificationType` para distinguir degrees, MBA, bootcamps, courses e certifications;
- criadas as migrations `V9__create_certification_table.sql` e `V10__insert_data_certification_table.sql`;
- adicionadas as conquistas educacionais e profissionais disponíveis na documentação do portfólio;
- implementado o endpoint `GET /api/v1/certifications`, ordenado por `displayOrder` ascendente;
- mantido um único agregado `Certification`, conforme ADR-026.

Sprint 8 concluída — `Technology`:

- extraída a Technology de Project para uma feature independente;
- criadas as migrations `V11__create_technology_and_project_technology_tables.sql` e `V12__seed_technology_and_project_technology.sql`;
- adicionadas as 21 tecnologias únicas do catálogo Angular;
- criada a relação explícita `ProjectTechnology`, ordenada por `displayOrder`;
- implementado o endpoint `GET /api/v1/technologies`;
- atualizados os endpoints de Project para retornar objetos `TechnologyResponse`.
- mantidas as operações de Project como leitura pública, conforme ADR-023 atualizado e ADRs 027–028.

Endpoints de leitura disponíveis:

```http
GET /api/v1/about
GET /api/v1/projects
GET /api/v1/projects/{slug}
GET /api/v1/experiences
GET /api/v1/social-links
GET /api/v1/certifications
GET /api/v1/technologies
```

Critério de conclusão:

- cada domínio possui persistência, service, controller, DTOs e testes proporcionais à sua complexidade;
- o frontend consegue obter todo o conteúdo necessário por meio da API.

### Fase 6 — Integração com o Angular

Status: **pendente**

- [ ] Criar services HTTP no frontend;
- [ ] Integrar o endpoint `Hero`;
- [ ] Integrar `About`;
- [ ] Integrar `Projects`;
- [ ] Integrar `Experience`;
- [ ] Integrar `Certifications`;
- [ ] Integrar `SocialLinks`;
- [ ] Remover gradualmente os arquivos da pasta `data/`;
- [ ] Implementar estados de carregamento e erro;
- [ ] Validar a experiência em desenvolvimento e produção.

Critério de conclusão:

- o frontend não depende mais dos dados estáticos da pasta `data/`;
- o conteúdo exibido vem da API;
- falhas da API possuem tratamento visual adequado.

### Sprint 10 — Platform Hardening

Status: **concluída**

- [x] Substituir `ResponseStatusException` por exceções customizadas;
- [x] Centralizar respostas de erro com `@RestControllerAdvice`;
- [x] Eliminar o N+1 da listagem de Projects;
- [x] Manter todos os endpoints GET públicos;
- [x] Preparar a infraestrutura de segurança sem JWT ou login;
- [x] Verificar e documentar Swagger/OpenAPI;
- [x] Sincronizar README, arquitetura, roadmap e checklist.

JWT e login permanecem adiados até a criação dos endpoints administrativos.

### Sprint 11 — Static Assets & Technology Icons

Status: **concluída**

- [x] Tornar `Technology.slug` o identificador canônico dos ícones;
- [x] Remover `icon` do domínio, contrato da API e persistência;
- [x] Adicionar assets SVG Devicon em `static/icons/technologies`;
- [x] Reservar as categorias `social` e `certifications`;
- [x] Expor assets estáticos pelo Spring Boot em `/icons/technologies/{slug}.svg`;
- [x] Remover a coluna obsoleta com migration Flyway forward-only;
- [x] Documentar ownership do backend e o contrato reutilizável pelos frontends.

### Fase 7 — Documentação, qualidade e deploy

Status: **pendente**

- [x] Documentar endpoints com OpenAPI;
- [x] Padronizar respostas de erro;
- [x] Adicionar testes de integração com Testcontainers;
- [ ] Criar Dockerfile;
- [ ] Criar configuração Docker Compose para desenvolvimento;
- [ ] Configurar pipeline de CI;
- [ ] Publicar a API;
- [ ] Publicar o banco de dados;
- [ ] Configurar variáveis de ambiente no ambiente de produção;
- [ ] Configurar HTTPS;
- [ ] Atualizar o README com instruções de deploy.

Critério de conclusão:

- a aplicação pode ser executada em um ambiente limpo seguindo a documentação;
- build, testes e empacotamento são executados automaticamente;
- frontend, backend e banco estão acessíveis em ambiente de produção.

---

## V2.2 — Assistente de IA

### Objetivo

Adicionar um assistente capaz de responder perguntas sobre a experiência profissional, projetos, certificações e competências de Daniel.

### Entregas

- [ ] Definir escopo de perguntas permitidas;
- [ ] Criar base de conhecimento sobre Daniel;
- [ ] Definir estratégia de ingestão dos conteúdos;
- [ ] Avaliar embeddings e busca semântica;
- [ ] Criar endpoint de conversa;
- [ ] Implementar controle de contexto da conversa;
- [ ] Criar respostas para perguntas fora do escopo;
- [ ] Adicionar limites de uso e proteção contra abuso;
- [ ] Integrar o assistente ao frontend;
- [ ] Criar conjunto de perguntas para avaliação;
- [ ] Monitorar qualidade e custo das respostas.

### Regra de escopo

O assistente deve responder prioritariamente sobre:

- Daniel Bartholdy;
- experiência profissional;
- projetos;
- tecnologias;
- certificações;
- liderança;
- análise de negócios;
- produto e ERP.

Para perguntas fora desse escopo, deve informar claramente sua finalidade.

Critério de conclusão:

- o assistente responde com base em informações verificáveis da base de conhecimento;
- não inventa experiências ou qualificações;
- informa quando não possui dados suficientes;
- está integrado ao portfólio com uma experiência simples e clara.

---

## V2.3 — Painel administrativo

### Objetivo

Permitir que o conteúdo do portfólio seja atualizado sem alterar o código-fonte.

### Entregas

- [ ] Definir modelo de usuário administrativo;
- [ ] Implementar autenticação;
- [ ] Implementar autorização;
- [ ] Criar endpoints protegidos;
- [ ] Criar CRUD de projetos;
- [ ] Criar CRUD de experiências;
- [ ] Criar CRUD de certificações;
- [ ] Criar CRUD de tecnologias;
- [ ] Criar CRUD de links sociais;
- [ ] Adicionar upload ou gerenciamento de imagens;
- [ ] Adicionar validação de conteúdo;
- [ ] Registrar alterações importantes;
- [ ] Criar interface administrativa no Angular.

Critério de conclusão:

- um administrador consegue atualizar o conteúdo com segurança;
- alterações aparecem no portfólio público sem novo deploy do frontend;
- endpoints públicos continuam disponíveis sem autenticação;
- operações administrativas permanecem protegidas.

---

## Fora do escopo inicial

Os itens abaixo não fazem parte da primeira entrega:

- analytics avançado;
- sistema de comentários;
- múltiplos administradores;
- internacionalização completa do conteúdo;
- marketplace ou área de membros;
- chatbot de propósito geral;
- edição direta de código pelo painel administrativo.

Esses itens podem ser avaliados depois que a API, a persistência e a integração principal estiverem estáveis.

## Ordem recomendada de execução

```text
1. Configuração local
2. PostgreSQL
3. Flyway
4. Domínio Hero
5. Endpoint GET /api/hero
6. Testes
7. Demais domínios
8. Integração Angular
9. Docker e CI
10. Deploy
11. Assistente de IA
12. Painel administrativo
```

## Definição geral de pronto

Uma entrega será considerada pronta quando:

- estiver implementada no código;
- possuir testes adequados;
- estiver documentada;
- não expuser credenciais ou dados sensíveis;
- funcionar em uma configuração limpa;
- tiver seu status atualizado neste documento.

Epic 6
Quality
- Unit Tests
- Integration Tests
- Testcontainers
- Performance
- Code Coverage

### Sprint T1 — Backend Testing Phase

Status: **concluída**

- [x] Criar testes unitários para regras de serviço, resolução de locale, fallback, exceções e orquestração de projetos.
- [x] Criar base compartilhada para testes de integração com PostgreSQL Testcontainers.
- [x] Validar todos os repositórios de domínio com PostgreSQL real, Flyway, ordering, locale, slug, foreign keys e ProjectTechnology.
- [x] Criar testes de controller com `@SpringBootTest` e MockMvc para todos os endpoints públicos.
- [x] Validar contratos JSON, respostas localizadas, fallback, erros, CORS, OPTIONS, segurança pública e Actuator Health.
- [x] Validar a população real do cache Caffeine e o comportamento de requests repetidas.
- [x] Documentar a estratégia de testes no README, arquitetura, checklist e decisões.
- [x] Confirmar `./gradlew test` com todos os testes passando.

Critério de conclusão:

- a suíte executa contra PostgreSQL real e migrations de produção;
- não utiliza H2, testes ignorados ou mocks de banco/cache em testes de integração;
- o foco permanece em confiança de produção, não em percentual de cobertura.
### Sprint 12 — Developer Tooling: Icon Synchronizer

Status: **completed**

- [x] Create support/icons provider, downloader, storage, synchronizer, and cli packages.
- [x] Implement IconProvider and DeviconProvider.
- [x] Implement SVG-only downloading and non-overwriting storage.
- [x] Create the syncIcons Gradle task.
- [x] Read technologies from the Flyway seed without starting the application.
- [x] Generate a summary and report slugs without a Devicon icon.
- [x] Document the manual workflow and versioned asset strategy.

### Sprint 13 — Backend Localization Foundation

Status: **completed**

- [x] Add locale metadata to Hero, About, Project, Experience, and Certification domain and persistence models.
- [x] Add Flyway migrations for locale columns, `pt-BR` backfill, and `en-US`/`es-ES` seed rows.
- [x] Resolve `Accept-Language` in the service layer with `en-US` then `pt-BR` fallback.
- [x] Add locale predicates to repositories; do not filter languages in memory.
- [x] Keep locale out of endpoint URLs and preserve Swagger/OpenAPI endpoints.
- [x] Update localization architecture, domain, decision, roadmap, and checklist documentation.

---

## V3 — Knowledge Platform

V3 is knowledge-first: the knowledge base is the source of truth and the LLM only generates natural-language responses.

### V3.1 — Knowledge Platform Foundation — completed

- [x] Isolated `assistant` module and official Spring AI OpenAI starter.
- [x] Typed `portfolio.ai.*` configuration and classpath prompt loading.
- [x] Stateless, non-streaming `POST /api/v1/assistant/chat` proof of concept.
- [x] Future `RetrievalService` abstraction without retrieval implementation.
- [x] Documentation of local ingestion and production boundaries.
- [x] No embeddings, pgvector, RAG, PDF ingestion, or document processing.

### V3.2 — Retrieval Foundation — completed

- [x] Define the persistence-agnostic `KnowledgeChunk` model.
- [x] Define the store-independent `KnowledgeRepository` contract.
- [x] Extend `RetrievalService` as the future retrieval boundary without implementing retrieval.
- [x] Add typed retrieval configuration for enablement, vector-store name, default top-k, and minimum score.
- [x] Document the external n8n → chunking → embeddings → Supabase pgvector lifecycle.
- [x] Keep document processing, embeddings, vector search, and pgvector queries out of production.

### V3.3 — Vector Search (pgvector) — planned
- [x] Sprint 3.3: backend embedding processing with OpenAI and pgvector
- [x] Implement a pgvector-backed `KnowledgeRepository` adapter.
- [ ] Implement similarity retrieval behind `RetrievalService`.

### Sprint 3.4 — Retrieval (RAG)

- [x] Implement question embedding and Top K pgvector retrieval.
- [x] Add bounded context assembly and empty-context handling.
- [x] Integrate retrieval into chat generation.
- [x] Add retrieval configuration and unit tests.
- [ ] Add bounded context assembly and grounding tests.
- [ ] Keep ingestion and embedding generation outside the production runtime.

### Sprint 4.0 — Hybrid Retrieval — completed

- [x] Retrieve profile, experience, projects, certifications, technologies, and social links from PostgreSQL through existing services.
- [x] Keep pgvector retrieval behind a dedicated vector retrieval service.
- [x] Orchestrate both sources through `HybridRetrievalService`.
- [x] Build a structured, deduplicated, source-neutral context.
- [x] Prioritize structured facts and preserve descriptive vector knowledge.
- [x] Isolate source failures and preserve configured top-k and bounded prompt context.
- [x] Add unit coverage and document trade-offs and future provider extensibility.

### V3.4 — Frontend Chat — planned

- [ ] Integrate Angular chat with loading, error, and empty states.

### V3.5 — Conversation Memory — planned

- [ ] Define conversation identity, retention, privacy, and context limits.

### V3.6 — Streaming — planned

- [ ] Add streaming with cancellation, timeout, and non-streaming fallback behavior.

### V3.7 — AI Tools / Function Calling — planned

- [ ] Define explicit, safe, authorized portfolio tools.

### V3.8 — Knowledge Administration — planned

- [ ] Add protected knowledge source management, indexing status, and versioning.

### Sprint 14 - Security Hardening

Status: **completed**

- [x] Protect every `/api/v1/admin/**` endpoint with `X-API-KEY`.
- [x] Configure the key with `portfolio.security.admin-api-key` and `PORTFOLIO_ADMIN_API_KEY`.
- [x] Return HTTP 401 for missing or invalid keys and fail closed without a configured secret.
- [x] Keep the public API, icons, Swagger/OpenAPI, health, and `POST /api/v1/assistant/chat` public.
- [x] Keep Basic Authentication, JWT, and OAuth out of scope; assistant rate limiting is implemented in Sprint 15.
- [x] Record ADR-036 and synchronize security documentation.

### Sprint 15 - AI Hardening Sprint

Status: **completed**

- [x] Bucket4j per-IP limiting with localized `429` and `Retry-After`.
- [x] Global validation, security, malformed-request, business, timeout, rate-limit, and provider error handling.
- [x] Prompt trust boundaries, configurable message/prompt limits, and injection checks.
- [x] Resilience4j retry, circuit breaker, timeout, and bulkhead with safe fallback.
- [x] Assistant responsibility split and removal of the legacy vector-only compatibility path.
- [x] Operational-only AI logging and focused hardening tests.

The limiter is process-local until horizontal deployment justifies a shared store.

