# Domain Model Guidelines

The domain layer represents business concepts and rules only.

Each feature owns `domain/model` and `domain/repository`. Repository types in the domain are interfaces only. Feature-local shared objects belong under `{feature}/shared`; cross-cutting concerns remain outside business features.

Domain classes must not depend on:

- JPA or Hibernate annotations;
- Spring Data repositories;
- Spring MVC;
- database schemas or persistence configuration.

Domain models are regular Lombok-backed classes. Use Jakarta Validation annotations such as `@NotBlank`, `@Size`, `@Email`, and `@NotNull` when they express business data constraints.

Validation must be selective. For each domain, annotate only fields with confirmed business invariants. Do not annotate every text field automatically. Persistence metadata such as `createdAt` and `updatedAt` belongs to the infrastructure entity and is not part of the domain model unless it has explicit business meaning.

Lombok and Jakarta Validation are permitted in the domain because they do not describe storage or framework persistence behavior.

Persistence concerns belong under `infra/persistence/{feature}`. Each persistence feature contains an entity with the `Entity` suffix and its repository, for example `HeroEntity` and `HeroRepository`.

Application mappers define the boundary between persistence entities, domain models, and API DTOs.

## Education & Certifications

The portfolio groups every educational achievement into a single feature called Certification.

A Certification represents any relevant educational or professional achievement displayed on the portfolio.

Each record belongs to one type represented by `CertificationType`.

Types:

- DEGREE
- MBA
- BOOTCAMP
- COURSE
- CERTIFICATION

The single Certification aggregate intentionally groups academic and professional achievements. The type distinguishes records for frontend grouping without creating separate Degree, MBA, Bootcamp, Course, or ProfessionalCertification entities.

Examples

DEGREE

- Systems Analysis & Development

MBA

- MBA in People Management, Leadership & Productivity

BOOTCAMP

- Santander Java Bootcamp
- Deal Spring Boot & Angular

COURSE

- Domain Driven Design
- Test Driven Development
- SOLID Express
- Keycloak
- Advanced Testing

CERTIFICATION

- AI Product Specialist
- Product Management

## Technology

Technology is an independent aggregate representing a technology used throughout Daniel's professional career. Its model contains `id`, `name`, `slug`, `website`, and `displayOrder`.

## Localization

`Hero`, `About`, `Project`, `Experience`, and `Certification` are localized aggregates. Each domain model contains the supported locale tag (`pt-BR`, `en-US`, or `es-ES`). Locale is content identity metadata, not a URL parameter.

The API selects content from the `Accept-Language` header. Persistence adapters filter by locale in the database; services try the requested supported locale, then `en-US`, then `pt-BR`.

`slug` is the canonical technology identifier. Shared technology icons are backend-owned static assets resolved at `/icons/technologies/{slug}.svg`; icon fields and external icon URLs are not domain data.

Technology is intentionally not owned by Project. It can be reused by Projects, Professional Experiences, Certifications, and future Skills or Articles.

Project associations are represented explicitly by `ProjectTechnology`, which contains `project`, `technology`, and `displayOrder`. Relationship-specific business data is not added until a real requirement exists.

## Assistant module

The isolated `assistant` module owns the stateless chat controller, immutable chat contracts, AI configuration, prompt loading, and `AssistantService` orchestration. `AssistantService` remains responsible only for conversation orchestration and generation; it must not know about PDF, DOCX, Markdown, chunking, embeddings, or vector-store details.

The `assistant/retrieval` package owns access to indexed knowledge. `KnowledgeChunk` is the persistence-agnostic knowledge model, `KnowledgeRetrievalRepository` owns pgvector access, and `RetrievalService` orchestrates question embedding and similarity retrieval. `PromptBuilder` assembles the ordered, bounded context for the chat flow. Document ingestion remains external.

Knowledge is produced outside the backend by the local n8n pipeline. Production consumes indexed chunks from the future vector store through retrieval adapters and never processes source documents or generates embeddings.
