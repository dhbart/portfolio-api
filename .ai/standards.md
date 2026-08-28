# STANDARDS.md

# Portfolio Platform - Development Standards

Version: 1.0

Last Updated: 2026-08-18

---

# Purpose

This document defines the coding standards and development conventions used throughout the Portfolio Platform.

Every new feature must follow these standards.

If a suggestion conflicts with this document, explain the conflict before generating code.

---

# General Principles

Always prefer:

- Readability
- Simplicity
- Consistency
- Explicit code

Avoid:

- Clever code
- Premature optimization
- Unnecessary abstractions
- Boilerplate
- Hidden behavior

---

# Architectural Principles

The project follows:

- Package by Feature
- Clean Architecture (simplified)
- SOLID
- KISS
- DRY
- YAGNI

---

# Feature Structure

Every business feature must follow this structure:

feature/

    application/

        controller/

        service/

        mapper/

        dto/

    domain/

        model/

        repository/

    infrastructure/

        persistence/

            entity/

            mapper/

            repository/

    shared/

---

# Package Responsibilities

## Application

Responsible for:

- REST Controllers
- Services
- DTOs
- Use Case orchestration
- Application Mappers

Never access JPA directly.

---

## Domain

Contains:

- Business Models
- Repository Interfaces

Domain models represent business concepts.

They must never contain persistence concerns.

---

## Infrastructure

Contains:

- JPA Entities
- Repository Implementations
- Persistence Mappers

Infrastructure depends on frameworks.

---

# Naming Conventions

## Domain Model

Hero

Project

Technology

Certification

---

## Entity

Always use Entity suffix.

Examples:

HeroEntity

ProjectEntity

TechnologyEntity

---

## Repository Interface

HeroRepository

ProjectRepository

---

## Repository Implementation

HeroRepositoryImpl

ProjectRepositoryImpl

---

## Mapper

HeroMapper

HeroEntityMapper

---

## DTO

HeroRequest

HeroResponse

ProjectRequest

ProjectResponse

---

# DTO Guidelines

DTOs belong to:

application/<feature>/dto

Use Java Records whenever possible.

DTOs must be immutable.

---

# Domain Model Guidelines

Domain models:

✔ Lombok allowed

✔ Jakarta Validation allowed

❌ No JPA annotations

❌ No Hibernate annotations

❌ No Spring annotations

Validation annotations must represent real business rules.

Never generate validations automatically.

---


## Domain Modeling

Prefer modeling concepts instead of screens.

A feature may represent multiple business concepts when they belong to the same bounded context.

Example:

Certification

may contain:

- Degrees
- MBAs
- Bootcamps
- Courses
- Certifications

Different concepts should be differentiated through enums whenever appropriate instead of creating unnecessary entities.


---


# Entity Guidelines

Entities belong to:

infrastructure/persistence/entity

Entities represent database persistence.

Allowed:

@Entity

@Table

@Id

@Column

@ManyToOne

@OneToMany

@JoinColumn

@GeneratedValue

Forbidden:

Business logic

REST annotations

DTO responsibilities

---

# Repository Guidelines

Repository Interfaces belong to:

domain/repository

Repository Implementations belong to:

infrastructure/persistence/repository

Application depends only on repository interfaces.

---

# Mapper Guidelines

Application Mapper

Converts:

Request DTO

↓

Domain

↓

Response DTO

Persistence Mapper

Converts:

Domain

↓

Entity

↓

Domain

Never expose Entities outside Infrastructure.

---

# Lombok

Use the minimum necessary annotations.

Preferred:

@Getter

@Setter

@NoArgsConstructor

@AllArgsConstructor

Avoid:

@Data

@Builder (unless object construction clearly benefits)

Never generate annotations by default.

---

# Validation

Validation is not decoration.

Use only when there is a business rule.

Good examples:

@NotBlank

@NotNull

@Email

@Positive

Avoid:

@Size without business justification

Validation just because a field is String

---

# Java Style

Prefer:

Constructor Injection

Records for DTOs

Final variables whenever possible

Small methods

Small classes

Avoid:

Static helpers

God classes

Long methods

Magic numbers

---

# Controller Guidelines

Controllers must:

- Validate requests
- Delegate to Services
- Return DTOs

Controllers must never:

Access repositories directly.

Contain business logic.

---

# Service Guidelines

Services orchestrate use cases.

Services may:

Call repositories

Call mappers

Coordinate business flow

Services must never:

Return Entities.

Expose persistence.

---

# Exception Handling

Use custom exceptions.

Never return generic RuntimeExceptions to the client.

Centralize exception handling.

---

# Comments

Code should be self-explanatory.

Prefer expressive names over comments.

Document only business rules or non-obvious decisions.

---

# Testing

Every new feature should eventually include:

Unit Tests

Integration Tests

Testcontainers

## Testing Strategy

The backend testing phase is implemented. Business behavior must be covered at the narrowest realistic level before a feature is considered complete.

However:

- Every feature must be testable.
- Constructors must allow dependency injection.
- Business logic must remain isolated.
- Architecture must facilitate unit and integration testing.

Follow the testing pyramid: unit-test business rules and service orchestration; use real PostgreSQL Testcontainers and Flyway for repository behavior; and use `@SpringBootTest` with MockMvc for HTTP contracts, security, localization, and cross-cutting behavior. Do not use H2, mock the database, or add tests solely to increase coverage. DTOs, entities, generated Lombok/MapStruct code, and trivial delegation are not unit-test targets.

## PostgreSQL and Flyway

When using PostgreSQL with Spring Boot and Flyway:

- Declare `spring-boot-starter-flyway`;
- Declare `org.flywaydb:flyway-database-postgresql`;
- Use a JDBC URL beginning with `jdbc:postgresql://`;
- Keep schema ownership with Flyway and set JPA `ddl-auto` to `validate`;
- Provide local PostgreSQL through Docker Compose;
- Use independent Testcontainers integration tests to validate migrations and persistence.

# Flyway Convention

Every feature that introduces a new persistence entity must also provide:

- the corresponding Flyway migration
- the initial seed data whenever the frontend already contains static data

The migration must be part of the same Sprint.

A feature is only considered complete when:

- Domain is implemented
- Application is implemented
- Infrastructure is implemented
- Flyway migration exists
- Initial seed data exists (when applicable)

---

# AI Development Rules

AI assistants must:

Read the .ai documentation before coding.

Follow every accepted ADR.

Respect the architecture.

Generate only the necessary code.

Never refactor unrelated files.

Explain architectural decisions before generating code.

Prefer incremental changes.

Never introduce unnecessary frameworks.

---

# Final Rule

If multiple solutions are possible:

Choose the simplest one that preserves the project's architecture.

Consistency is more important than cleverness.

## Explicit relationship entities

Many-to-many business relationships must be modeled with an explicit relationship entity. Do not use implicit JPA `@ManyToMany`. The relationship entity owns ordering and any future relationship-specific business data, while each aggregate remains independently reusable.

## Static Assets

Technology icons are resolved from the technology slug.

The backend owns shared static assets. Store them under `src/main/resources/static`, organized by category (`icons/technologies`, `icons/social`, and `icons/certifications`). Technology SVGs use Devicon as the official source and are served by Spring Boot.

Never store icon URLs.

Never store icon filenames.

The slug is the canonical identifier.

Static assets are resolved from:

/icons/technologies/{slug}.svg

Frontend clients must build the asset URL as `{apiUrl}/icons/technologies/{slug}.svg`. The API exposes only `slug`, so future frontends can reuse the same backend asset contract.
### Icon synchronization workflow

Icon synchronization is development tooling only. Run ./gradlew syncIcons
when a technology is added or when a vendored asset needs to be refreshed.
The task reads the technology seed, resolves supported slugs through Devicon,
downloads only SVG content, and stores missing files under
src/main/resources/static/icons/technologies.

Existing files are never overwritten by default. Use the CLI overwrite option
only when an intentional asset refresh is required. Generated SVGs must be
reviewed and committed as project assets. The application must never download
icons at runtime, persist external icon URLs, or expose provider URLs.

## AI Development Standards

- Never hardcode prompts in Java; store templates under `resources/prompts`.
- Use `@ConfigurationProperties` for centralized AI settings.
- Never bypass `RetrievalService` when retrieval is introduced.
- Never mix retrieval with chat generation.
- Keep AI isolated from portfolio business and persistence modules.
- Keep assistant services stateless until a memory ADR is accepted.
- Production uses Spring AI directly with OpenAI; n8n and OmniRoute are not runtime dependencies.
- The backend does not process documents or generate embeddings.

## Security Standards

- Protect every `/api/v1/admin/**` endpoint with the configured `X-API-KEY`.
- Keep API-key validation in reusable Spring Security infrastructure, never in controllers.
- Read the expected key from typed `portfolio.security.admin-api-key` configuration and support `PORTFOLIO_ADMIN_API_KEY` as the environment override.
- Return HTTP 401 for missing, blank, or invalid keys and fail closed when no key is configured.
- Keep the public portfolio API, static icons, Swagger/OpenAPI, health check, and `POST /api/v1/assistant/chat` public until a later sprint changes their policy.
- Do not introduce Basic Authentication, JWT, or OAuth as part of this sprint.

## AI Hardening Standards

- Protect public AI calls with reusable infrastructure rate limiting; controllers must not implement quotas.
- Keep AI limits, safety flags, timeouts, retries, circuit-breaker thresholds, and bulkhead limits in typed configuration.
- Retry only transient provider failures and keep one retry layer around the chat provider.
- Treat user input and retrieved context as untrusted data; never allow them to override system instructions.
- Never log prompts, responses, embeddings, API keys, or authorization headers.
- Return safe, localized error contracts without stack traces or provider details.
