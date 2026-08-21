# CHECKLIST.md

## Certification Sprint

- [x] Package organization follows the Hero reference feature.
- [x] Dependency direction remains `application -> domain <- infrastructure`.
- [x] `CertificationType` contains `DEGREE`, `MBA`, `BOOTCAMP`, `COURSE`, and `CERTIFICATION`.
- [x] Domain model and repository port implemented.
- [x] Application controller, service, mapper, request record, and response record implemented.
- [x] Persistence entity, mapper, Spring Data repository, and repository implementation implemented.
- [x] Table creation migration created.
- [x] Seed migration created with portfolio achievements only.
- [x] Public endpoint is `GET /api/v1/certifications`.
- [x] Results are ordered by `displayOrder ASC`.
- [x] Certification aggregate decision documented in DOMAIN.md and ADR-026.
- [x] No automated tests added, per sprint instructions.

## Sprint 11 — Static Assets & Technology Icons

- [x] Technology exposes `slug` as its canonical icon identifier.
- [x] Obsolete `icon` field removed from domain, DTOs, entity, and mappers.
- [x] Forward-only Flyway migration removes the obsolete database column.
- [x] Devicon SVGs stored under `src/main/resources/static/icons/technologies`.
- [x] `social` and `certifications` asset categories created.
- [x] Spring Boot serves `/icons/technologies/{slug}.svg` as a public static resource.
- [x] Documentation synchronized across architecture, standards, domain, decisions, and roadmap.

## Technology Extraction Sprint

- [x] Technology extracted from Project into an independent aggregate.
- [x] Technology feature follows the Hero architecture.
- [x] `ProjectTechnology` models the association explicitly.
- [x] No implicit JPA `ManyToMany` mapping used.
- [x] Technology and project-technology migrations and seeds created.
- [x] Project endpoints return `TechnologyResponse` objects.
- [x] `GET /api/v1/technologies` returns technologies ordered by `displayOrder`.
- [x] Domain, architecture, decision, and standards documentation updated.

## Sprint 10 — Platform Hardening

- [x] `ResponseStatusException` removed from production code.
- [x] Custom exceptions and centralized `@RestControllerAdvice` implemented.
- [x] Error responses use a consistent JSON contract without stack traces.
- [x] Project technology loading changed from per-project queries to one bulk query.
- [x] GET API endpoints remain public.
- [x] Swagger UI and OpenAPI endpoints remain public.
- [x] JWT and login intentionally deferred until administrative endpoints exist.
- [x] README, architecture, roadmap, and checklist synchronized.
- [x] No automated tests added, per sprint instructions.
## Sprint 12 — Developer Tooling: Icon Synchronizer

- [x] support/icons is separated into provider, downloader, storage, synchronizer, and cli packages.
- [x] IconProvider and DeviconProvider resolve technology slugs.
- [x] IconDownloader downloads and validates SVG content only.
- [x] IconStorage creates the target directory and does not overwrite by default.
- [x] IconSynchronizer coordinates the workflow and produces a report.
- [x] syncIcons Gradle task is available through ./gradlew syncIcons.
- [x] Synchronization reads the Flyway technology seed without requiring the application or database.
- [x] Assets are stored under src/main/resources/static/icons/technologies.
- [x] Manual developer workflow and versioned static asset strategy documented.

## Sprint 13 — Backend Localization Foundation

- [x] Supported locales are `pt-BR`, `en-US`, and `es-ES`.
- [x] Localized aggregates contain locale in domain and persistence models.
- [x] Flyway adds `VARCHAR(10)` locale columns and backfills existing rows to `pt-BR`.
- [x] Seed data exists for all three locales for every localized aggregate.
- [x] Repositories filter by locale in database queries.
- [x] Services resolve `Accept-Language` and apply `en-US` then `pt-BR` fallback.
- [x] Controllers keep locale out of endpoint URLs.
- [x] Localization documentation is synchronized.
- [ ] `./gradlew test` and database-backed Flyway verification remain final validation steps.
