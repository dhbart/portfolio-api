# DECISIONS.md

# Portfolio Platform - Architecture Decisions

Version: 2.1

Last Update: 2026-08-19

---

## Purpose

This document records the architectural decisions made during the development of the Portfolio Platform.

Its purpose is to explain **why** a decision was made, preventing future inconsistencies and helping both developers and AI assistants understand the project.

Whenever a proposal conflicts with an accepted decision, the conflict must be explained before suggesting a different implementation.

---

# Decision Status

Accepted
→ Decision is final and should be respected.

Proposed
→ Decision is under discussion.

Deprecated
→ Decision should no longer be used.

---

# Immutable Decisions

These decisions should never be changed without explicit approval.

---

## ADR-001

### Title

Frontend and Backend must be separated.

### Status

Accepted

### Decision

The frontend and backend will live in separate repositories.

Repositories:

- bartholdy-portfolio (Angular)
- portfolio-api (Spring Boot)

### Reason

- Better scalability
- Independent deployments
- Better CI/CD
- Enterprise architecture
- Easier maintenance

---

## ADR-002

### Title

REST API

### Status

Accepted

### Decision

Communication between frontend and backend will use REST APIs.

### Reason

REST is sufficient for the project's requirements.

GraphQL would introduce unnecessary complexity.

---

## ADR-003

### Title

Database

### Status

Accepted

### Decision

PostgreSQL will be the official database.

### Reason

- Mature
- Open Source
- Excellent Spring Boot support
- Widely used in enterprise environments

---

## ADR-004

### Title

Angular Architecture

### Status

Accepted

### Decision

Use Angular Standalone Components.

### Reason

Modern Angular standard.

Better tree-shaking.

Simpler architecture.

---

## ADR-005

### Title

Frontend Responsibility

### Status

Accepted

### Decision

Frontend must not contain business data.

### Reason

All application data must come from the backend API.

Temporary mock data is allowed only during development.

---

## ADR-006

### Title

Backend Architecture

### Status

Accepted

### Decision

Use layered architecture.

Controller

↓

Service

↓

Repository

↓

Database

### Reason

Simple.

Maintainable.

Well-known Spring Boot pattern.

---

## ADR-007

### Title

Clean Code

### Status

Accepted

### Decision

Code should prioritize readability over cleverness.

### Principles

- SOLID
- KISS
- DRY
- YAGNI

---

## ADR-008

### Title

Language

### Status

Accepted

### Decision

Application must support:

- Portuguese
- English

### Reason

International portfolio.

---

## ADR-009

### Title

Theme

### Status

Accepted

### Decision

Application must support:

- Light Mode
- Dark Mode

---

## ADR-010

### Title

Project Data

### Status

Accepted

### Decision

Projects, certifications, experiences and profile information must be stored in the database.

No static business data should remain inside Angular.

---

## ADR-011

### Title

Portfolio API

### Status

Accepted

### Decision

The Portfolio API is the single source of truth.

Angular must consume the API.

No duplicated business logic.

---

## ADR-012

### Title

Documentation

### Status

Accepted

### Decision

The project must keep the following documentation updated:

- README.md
- ARCHITECTURE.md
- ROADMAP.md
- DECISIONS.md
- AGENTS.md

---

## ADR-013

### Title

AI Assistant

### Status

Accepted

### Decision

The AI Assistant must answer only questions related to Daniel Bartholdy.

### It must answer

- Professional experience
- Projects
- Leadership
- ERP experience
- Certifications
- Technical skills
- Career
- Portfolio

### It must NOT answer

General knowledge questions.

Weather.

News.

Programming unrelated to the portfolio.

---

## ADR-014

### Title

Repository Structure

### Status

Accepted

### Decision

Each repository has a single responsibility.

bartholdy-portfolio

Frontend.

portfolio-api

Backend.

Future CMS

Administration.

---

# Flexible Decisions

These decisions may change without affecting the architecture.

- Docker Compose
- Flyway
- Testcontainers
- Spring Security
- JWT
- Redis
- OpenTelemetry
- Grafana
- Prometheus
- RabbitMQ
- Kafka

---

# AI Guidelines

When suggesting code:

- Respect all Accepted decisions.
- Explain conflicts before proposing alternatives.
- Do not introduce unnecessary complexity.
- Prefer readability over optimization.
- Do not recommend changing technologies already defined.
- Keep the project aligned with enterprise best practices.

---

# Project Vision

The Portfolio Platform is not only a personal website.

It is a technical showcase demonstrating:

- Angular
- Spring Boot
- PostgreSQL
- REST APIs
- Clean Architecture
- Software Design
- AI Integration
- Full Stack Development
- Product Thinking

Every new feature should reinforce this vision.

## ADR-022

### Title

PostgreSQL Flyway integration and startup validation

### Status

Accepted

### Decision

Projects using Spring Boot, Flyway, and PostgreSQL must declare both the Flyway starter and the PostgreSQL-specific Flyway database module. Datasource URLs must use the `jdbc:postgresql://` format. Local PostgreSQL must be reproducible through Docker Compose, while integration tests must use independent Testcontainers databases.

### Reason

- Flyway separates database support from its core runtime;
- Invalid or incomplete datasource configuration fails before migrations execute;
- Compose provides a repeatable local environment;
- Testcontainers prevents integration tests from depending on developer machine state.

### Consequences

Every new PostgreSQL project must verify Flyway startup and migrations as part of integration testing. `ddl-auto` must not create or update the schema when Flyway owns migrations; use validation instead.

---

## ADR-023

### Title

Project feature as a read-only portfolio catalogue

### Status

Accepted

### Decision

The initial Project feature exposes only public read operations: listing all projects ordered by `displayOrder` and retrieving one project by `slug`. The initial records are loaded by Flyway from the frontend project catalogue. Project technologies are returned as `TechnologyResponse` objects through the explicit `ProjectTechnology` relationship.

### Reason

- Completes the public portfolio read use case with the data currently available;
- keeps the first richer business feature simple;
- keeps Technology independent while allowing Project responses to expose ordered technology associations;
- preserves the feature-first dependency direction and existing Hero implementation style.

### Consequences

The Project API provides `GET /api/v1/projects` and `GET /api/v1/projects/{slug}`. Both endpoints return ordered technology associations. Administrative write operations remain outside this sprint. The Technology catalogue is exposed through `GET /api/v1/technologies`.

---

## ADR-021

### Title

Feature-first architecture with domain repository ports

### Status

Accepted

### Decision

Each business feature is a self-contained top-level package with `application`, `domain`, `infrastructure`, and feature-local `shared` areas. Domain contains business models and repository interfaces. Infrastructure contains persistence entities, persistence mappers, and repository implementations. Application contains controllers, services, DTOs, and application mappers.

The dependency direction is `application -> domain <- infrastructure`. Domain never depends on infrastructure.

### Reason

- Isolates business features;
- Makes dependency direction explicit;
- Keeps domain contracts independent from Spring Data and JPA;
- Allows persistence implementations to change without changing use cases;
- Scales the codebase without global layer packages.

### Consequences

Repositories require a domain interface and an infrastructure implementation. JPA repositories are infrastructure details. Global cross-cutting concerns remain outside feature packages, while feature-specific shared objects stay inside their owning feature.

---

## ADR-020

### Title

Minimal validation and Lombok annotations

### Status

Accepted

### Decision

Jakarta Validation annotations must be used only for real business invariants. They must not be generated automatically or added merely because a field has a particular Java type.

Lombok annotations must be minimal. `@Getter`, `@Setter`, `@NoArgsConstructor`, and `@AllArgsConstructor` are preferred when useful. `@Data` must not be used. `@Builder` requires a clear construction benefit and must not be applied by default.

### Reason

- Keeps validation meaningful and tied to business behavior;
- Avoids decorative and redundant annotations;
- Preserves readable, explicit code;
- Prevents broad Lombok annotations from generating unintended API or equality behavior;
- Follows KISS, YAGNI, and Clean Code principles.

### Consequences

Each validation or Lombok annotation must be justifiable by the class responsibility. Code reviews must reject annotations added only by convention or automation.

Domain models must not include persistence audit fields such as `createdAt` and `updatedAt` by default. Those fields belong to infrastructure entities unless they become explicit business concepts.

---

## ADR-018

### Title

Domain models use Lombok and Jakarta Validation

### Status

Accepted

### Decision

Domain models are regular persistence-agnostic classes using Lombok for readable construction/accessor code and Jakarta Validation annotations for applicable business data constraints. JPA, Hibernate, and Spring Data annotations remain exclusive to infrastructure persistence entities.

### Reason

- Keeps domain constraints close to the business representation;
- Avoids boilerplate without introducing custom abstractions;
- Preserves independence from persistence frameworks;
- Makes the entity/domain boundary explicit.

### Consequences

Domain models may use Lombok and Jakarta Validation, but must not use persistence annotations. Infrastructure entities continue to contain all JPA mapping metadata.

---

## ADR-017

### Title

Persistence entities belong to Infrastructure

### Status

Accepted

### Decision

JPA entities and Spring Data repositories must live under `infra/persistence/{feature}`. They must use the `Entity` suffix, such as `HeroEntity`. The `domain/{feature}` package contains persistence-agnostic business models only.

Application mappers convert between persistence entities, domain models, and response/request DTOs.

### Reason

- Keeps the domain independent from JPA and Hibernate;
- Makes persistence replaceable without changing business concepts;
- Prevents infrastructure annotations from spreading into application logic;
- Makes the entity-to-domain boundary explicit.

### Consequences

The previous ADR-016 package organization remains valid, but its domain model interpretation is corrected by this ADR. Existing runtime behavior and endpoint contracts remain unchanged.

---

## ADR-016

### Title

Feature-oriented package organization

### Status

Accepted

### Decision

Organize application code by feature using `application`, `domain`, and `infra` areas. Application artifacts stay under `application/{feature}`, domain models under `domain/{feature}`, and technical persistence implementations under `infra/persistence/{feature}`.

The runtime dependency flow remains layered: `Controller -> Service -> Repository -> Database`.

### Reason

- Keeps each use case cohesive;
- Reduces cross-package navigation;
- Preserves layered responsibilities;
- Avoids generic abstractions and premature framework boundaries.

### Consequences

The former global `controller`, `service`, `repository`, `entity`, `dto`, and `mapper` packages are historical and must not be used for new code. Existing REST, PostgreSQL, DTO, and layered dependency decisions remain unchanged.

---

## ADR-015

### Title

DTO Implementation

### Status

Accepted

### Decision

All Request and Response DTOs must be implemented as Java Records.

### Reason

- Immutable by default
- Better readability
- Less boilerplate
- Modern Java standard (Java 21+)
- Fully supported by Spring Boot 4

### Exceptions

JPA Entities must remain regular classes.

---

## ADR-024

### Title

Experience content preserves the frontend data shape

### Status

Accepted

### Decision

The Experience read model preserves the complete data currently available in the Angular frontend: location, period, position, summary, detailed description paragraphs, highlights, and technologies, in addition to normalized dates, current-position status, and display order. Collection results are exposed by `GET /api/v1/experiences` ordered by `displayOrder` descending.

The paragraph, highlight, and technology collections are persisted as PostgreSQL `JSONB` columns and mapped to `List<String>` at the domain and API boundaries.

### Reason

- Prevents the API migration from losing portfolio content;
- keeps the frontend catalogue and API response semantically aligned;
- uses PostgreSQL's native structured value support without introducing extra feature tables for read-only content;
- preserves the existing feature-first dependency direction and read-only scope.

### Consequences

Experience schema evolution must use forward-only Flyway migrations. The content collections remain ordered arrays, and future administrative write operations must validate their structure before persistence.

---

## ADR-025

### Title

SocialLink feature as a read-only frontend-backed catalogue

### Status

Accepted

### Decision

Social links are implemented as an isolated `sociallink` feature using the definitive feature-first architecture. The public API exposes `GET /api/v1/social-links`, returning links ordered by `displayOrder`. The initial data is loaded by Flyway migration `V8__create_and_seed_social_link_table.sql` from the active Angular contact catalogue.

The persisted contract contains `label`, `value`, `url`, and `icon`, plus the infrastructure-owned ordering and audit fields. Only LinkedIn and GitHub are seeded because the Angular email link is currently commented out.

### Reason

- keeps the API contract aligned with the active frontend data;
- makes the Portfolio API the source of truth for contact links;
- preserves the existing read-only scope for public portfolio content;
- avoids persisting disabled or unavailable contact channels.

### Consequences

SocialLink follows `application -> domain <- infrastructure`, with JPA restricted to infrastructure and response DTOs kept separate from entities. Future administrative CRUD operations remain outside this sprint and must use forward-only migrations for schema changes.

## ADR-026 — Education & Certification Modeling

Status

Accepted

Context

The initial design considered every educational item as a Certification.

During implementation we identified different types of achievements:

- Academic Degrees
- MBA
- Bootcamps
- Professional Courses
- Professional Certifications

Decision

Keep a single Certification aggregate.

Differentiate records through a `CertificationType` enum with the values `DEGREE`, `MBA`, `BOOTCAMP`, `COURSE`, and `CERTIFICATION`.

Consequences

- Simpler API.
- Simpler Angular implementation.
- Better extensibility.
- Easier ordering.
- Richer UI.

The aggregate is intentionally named Certification because all records are portfolio achievements consumed by the same read use case. The type preserves the distinction needed by the frontend without multiplying entities for concepts that share the same lifecycle, persistence shape, and endpoint.

Implementation status

Accepted decision implemented in the Certification feature. The public read endpoint is `GET /api/v1/certifications`, and the initial records are loaded by Flyway migrations `V9__create_certification_table.sql` and `V10__insert_data_certification_table.sql`.

## ADR-027 — Technology as an independent aggregate

### Status

Accepted

### Decision

Technology is an independent aggregate with its own feature, persistence, and read endpoint. Projects reference technologies through `ProjectTechnology`; Technology does not contain Project references.

### Reason

The same technology can be used by Projects, Professional Experiences, Certifications, Skills, and Articles. Keeping it independent prevents duplicated records and avoids coupling one reusable business concept to a single bounded context.

### Consequences

Technology is seeded once and reused through explicit associations. Future bounded contexts can create their own relationship entities without changing the Technology aggregate.

## ADR-028 — Explicit ProjectTechnology relationship

### Status

Accepted

### Decision

Persist Project-to-Technology with `ProjectTechnology`, containing `project`, `technology`, and `displayOrder`, backed by two explicit `ManyToOne` foreign keys. Implicit JPA `ManyToMany` mappings are not permitted.

### Reason

The association has business meaning and ordering. An explicit entity makes constraints visible, supports relationship-specific data later, and keeps persistence behavior predictable without introducing YAGNI fields such as proficiency or experience level.

### Consequences

Project APIs can return ordered Technology responses, while the relationship remains independently extensible for future contexts.

---

## ADR-029 — Backend-owned static technology assets

### Status

Accepted

### Decision

Shared static assets belong to the backend and are packaged under `src/main/resources/static`, organized by category. Technology SVGs are sourced from Devicon and served at `/icons/technologies/{slug}.svg`. `Technology.slug` is the canonical identifier; `icon`, `iconKey`, `iconUrl`, external URLs, and stored icon filenames are not part of Technology.

### Reason

- Keeps shared assets versioned and deployed with the API;
- gives every frontend the same stable asset contract;
- avoids coupling Technology persistence to a presentation-specific URL or filename;
- allows future frontends to reuse the same backend-owned assets.

### Consequences

Technology responses expose `slug` only. Frontends resolve icons with `{apiUrl}/icons/technologies/{slug}.svg`. Schema evolution removes the obsolete `icon` column through a forward-only Flyway migration.

---

## ADR-030 — Accept-Language localization foundation

`Hero`, `About`, `Project`, `Experience`, and `Certification` store `locale VARCHAR(10)`. Controllers accept `Accept-Language` and delegate it to services. Services resolve supported locale candidates, while repositories apply locale predicates in the database. The fallback order is requested locale, `en-US`, then `pt-BR`; invalid headers follow the same chain and never cause HTTP 500. `V14` backfills `pt-BR`, `V15` seeds `en-US` and `es-ES` for every localized aggregate, and `V16` supplies Spanish portfolio content.
