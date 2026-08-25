## Projeto

API backend do portfólio pessoal dhbart.

## Stack

- Java 25
- Spring Boot 4.0.7
- Gradle 9.7.0
- PostgreSQL
- Spring Data JPA
- Flyway
- Bean Validation

## PostgreSQL and Flyway convention

When configuring PostgreSQL with Flyway, include `flyway-database-postgresql`, use `jdbc:postgresql://` URLs, keep `ddl-auto: validate`, document Docker Compose, and validate migrations with independent Testcontainers tests.

## Arquitetura

## Arquitetura vigente

O cÃ³digo Ã© organizado por feature dentro de `application`, `domain` e `infra`. A dependÃªncia continua seguindo `Controller -> Service -> Repository -> Database`.

Utilizar arquitetura em camadas:

- `controller`: endpoints REST
- `service`: regras de negócio
- `repository`: acesso aos dados
- `entity`: entidades persistidas
- `dto`: contratos da API
- `mapper`: conversão entre entidades e DTOs
- `exception`: tratamento global de erros
- `config`: configurações da aplicação

## Arquitetura de persistÃªncia

JPA entities belong in `infra/persistence/{feature}` and must use the `Entity` suffix. Domain models are persistence-agnostic and must not use JPA, Hibernate, or Spring persistence annotations.

## ConvenÃ§Ãµes do domÃ­nio

Domain models use Lombok and Jakarta Validation where applicable. They remain persistence-agnostic and must not use JPA, Hibernate, or Spring Data annotations.

## Definitive feature architecture

Each business feature is isolated under `{feature}` with `application`, `domain`, `infrastructure`, and feature-local `shared` packages. Domain repositories are interfaces; infrastructure repositories implement them. Application depends on domain contracts, and domain never depends on infrastructure.

## Implemented features

- `Hero`: read-only portfolio hero content with PostgreSQL persistence.
- `About`: read-only portfolio introduction with PostgreSQL persistence.
- `Project`: read-only project listing and slug lookup with PostgreSQL persistence and initial data from the frontend project catalogue.
- `Technology`: independent read-only technology catalogue reused through explicit context relationships.
- `Certification`: read-only educational and professional achievement listing with PostgreSQL persistence and type-based grouping.

The Project feature is implemented under `project/application`, `project/domain`, and `project/infrastructure/persistence`. Its public endpoints are:

- `GET /api/v1/projects`
- `GET /api/v1/projects/{slug}`

The collection endpoint orders projects by `displayOrder`. The Project migration creates the table and inserts the initial portfolio project data.

## Regras

- Não expor entidades JPA diretamente nos controllers.
- Utilizar DTOs para entrada e saída da API.
- Utilizar migrations do Flyway para alterações no banco.
- Evitar lógica de negócio em controllers.
- Usar nomes em inglês no código.
- Manter endpoints sob o prefixo `/api`.
- Criar testes para regras de negócio.
- Não adicionar dependências sem justificar sua necessidade.

## Endpoints públicos iniciais

- `GET /api/hero`
- `GET /api/about`
- `GET /api/projects`
- `GET /api/projects/{slug}`
- `GET /api/experience`
- `GET /api/v1/certifications`
- `GET /api/v1/social-links`

## Qualidade

Antes de concluir uma alteração:

1. Executar os testes.
2. Verificar compilação.
3. Validar o contrato da API.
4. Atualizar a documentação quando necessário.

## Minimal Annotation Principle
Every class must use the smallest possible number of annotations.
Never add Lombok or Jakarta Validation annotations unless they provide a real benefit.
Prefer explicitness over annotation proliferation.
For domain models, retain only business invariants. Do not add `@Size`, `@NotBlank`, or similar annotations to fields without a confirmed business rule. Persistence audit fields such as `createdAt` and `updatedAt` remain infrastructure concerns by default.

## Validation and Lombok rules
Jakarta Validation annotations must represent a real business invariant. Do not add validation annotations merely because a field is a `String` or because validation is available in the stack.
Lombok annotations must also be minimal. Prefer `@Getter`, `@Setter`, `@NoArgsConstructor`, and `@AllArgsConstructor` only when useful. Do not use `@Data` or add `@Builder` by default; use `@Builder` only when construction is genuinely improved by it.

## Definition of Done

A feature is only complete when all of the following are implemented:

- Domain
- Application
- Infrastructure
- Flyway migration
- Initial data migration (when applicable)
- Documentation updates (if required)

Never consider a feature complete if the database migration is missing.

## Future AI ecosystem

Recruiter Assistant, Technical Assistant, Resume Assistant, and Interview Assistant are planned capabilities. They will share the same knowledge-first architecture, prompt management, retrieval boundary, Spring AI integration, and stateless service conventions. They are future use cases, not separate provider integrations. Indexed knowledge is consumed through `assistant/retrieval`; the backend does not process documents or generate embeddings.
