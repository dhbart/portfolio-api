# Portfolio API

[![Java](https://img.shields.io/badge/Java-25-ED8B00?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.7-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-4169E1?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Flyway](https://img.shields.io/badge/Flyway-migrations-CC0200?logo=flyway&logoColor=white)](https://documentation.red-gate.com/flyway)
[![Docker](https://img.shields.io/badge/Docker-ready-2496ED?logo=docker&logoColor=white)](https://www.docker.com/)
[![OpenAPI](https://img.shields.io/badge/OpenAPI-documented-6BA539?logo=swagger&logoColor=white)](https://swagger.io/specification/)

> **Status: Active Development**  
> Public read-only REST API powering Daniel Bartholdy’s portfolio. The application is deployed on Render, uses Supabase PostgreSQL, manages its schema with Flyway, and provides localized content to an Angular frontend.

## About

Portfolio API replaces hard-coded frontend content with a centralized, localized REST API. It exposes the professional information used by the public portfolio—hero content, profile, experience, projects, technologies, certifications, social links, and public icons—from PostgreSQL.

The backend follows production-minded practices: feature-first organization, HTTP DTOs separated from persistence entities, a dedicated service layer, Flyway migrations, container support, OpenAPI documentation, and integration testing with Testcontainers.

## Features

- Public, read-only REST API
- Localized content in **Portuguese (`pt-BR`)**, **English (`en-US`)**, and **Spanish (`es-ES`)**
- Standard language negotiation through `Accept-Language`
- Feature-first architecture with application, domain, and infrastructure boundaries
- PostgreSQL persistence with Flyway-managed migrations
- Spring Security foundation for a public API
- CORS restricted to the configured Angular frontend origin
- Global exception handling and Bean Validation
- OpenAPI specification and Swagger UI
- Docker and Docker Compose support
- Integration tests using Testcontainers and PostgreSQL

## Architecture

```text
HTTP request
    │
    ▼
Spring Security ───────────────► CORS and public read-only policy
    │
    ▼
REST Controller ───────────────► Request/response DTOs
    │
    ▼
Service layer ─────────────────► Use cases and business rules
    │
    ▼
Repository layer
    │
    ▼
PostgreSQL ◄─────────────────── Flyway versioned migrations
```

### Internationalization flow

```text
Accept-Language: en-US,en;q=0.9
                │
                ▼
          Locale resolution
                │
                ▼
      Locale-aware service query
                │
                ▼
          Localized JSON response
```

If the request does not provide a supported locale, the application uses its configured default.

## Project structure

The project is organized by feature. Each domain keeps its application, domain, and infrastructure responsibilities close together; cross-cutting concerns stay in shared packages.

```text
src/
├── main/
│   ├── java/dhbart/portfolioapi/
│   │   ├── config/                 # web, CORS, OpenAPI, security configuration
│   │   ├── common/                 # shared abstractions
│   │   ├── exception/              # global API exception handling
│   │   ├── hero/
│   │   ├── about/
│   │   ├── experience/
│   │   ├── projects/
│   │   ├── technologies/
│   │   ├── certifications/
│   │   ├── sociallinks/
│   │   └── support/icons/
│   └── resources/
│       ├── db/migration/           # Flyway migrations
│       ├── static/                 # public static assets
│       └── application.yml
└── test/
    └── java/dhbart/portfolioapi/
```

Within a feature, code is separated into `application`, `domain`, and `infrastructure` layers. HTTP contracts use DTOs, preserving the separation between API and persistence models.

## API endpoints

All portfolio endpoints are read-only and support the `Accept-Language` header.

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/api/v1/hero` | Introductory portfolio content |
| `GET` | `/api/v1/about` | Professional profile information |
| `GET` | `/api/v1/experience` | Professional experience |
| `GET` | `/api/v1/projects` | Project list |
| `GET` | `/api/v1/projects/{slug}` | Project details |
| `GET` | `/api/v1/technologies` | Technologies and skills |
| `GET` | `/api/v1/certifications` | Certification list |
| `GET` | `/api/v1/certifications/{slug}` | Certification details |
| `GET` | `/api/v1/social-links` | Social and contact links |
| `GET` | `/icons/**` | Public icon assets |

Example request:

```bash
curl -H "Accept-Language: en-US" http://localhost:8080/api/v1/projects
```

## API documentation

Swagger UI provides the authoritative list of operations, parameters, response schemas, and examples:

```text
http://localhost:8080/swagger-ui/index.html
http://localhost:8080/v3/api-docs
```

## Security

The current API is deliberately **public and read-only**. Only `GET` and `OPTIONS` are allowed for the public API and icon resources. CORS is constrained to `FRONTEND_ORIGIN`; authentication is not required for consuming portfolio content.

JWT authentication and role-based authorization are planned for a future administrative API, when content-management operations (`POST`, `PUT`, `PATCH`, and `DELETE`) are introduced.

## Prerequisites

- JDK 25
- Docker, for local PostgreSQL and Testcontainers-backed integration tests
- PostgreSQL, locally or through a Supabase project

The repository includes the Gradle Wrapper, so a global Gradle installation is unnecessary.

## Configuration

The datasource is assembled from the following environment variables. Keep credentials out of source control.

| Variable | Description | Local example |
| --- | --- | --- |
| `PGHOST` | PostgreSQL host | `localhost` |
| `PGPORT` | PostgreSQL port | `5432` |
| `PGDATABASE` | Database name | `portfolio` |
| `PGUSER` | Database user | `portfolio` |
| `PGPASSWORD` | Database password | `change-me` |
| `FRONTEND_ORIGIN` | Allowed Angular frontend origin | `http://localhost:4200` |
| `SPRING_PROFILES_ACTIVE` | Active Spring profile | `local` |

`SHOW_SQL` is also supported for development diagnostics and defaults to `false`.

For Supabase through the Session Pooler, copy the host, port, database, and user exactly as supplied by Supabase. The pooler username may include the project reference (for example, `postgres.<project-ref>`).

## Run locally

Start PostgreSQL with Docker Compose:

```bash
docker compose up -d postgres
```

Set the database variables, then run the application.

```powershell
$env:PGHOST="localhost"
$env:PGPORT="5432"
$env:PGDATABASE="portfolio"
$env:PGUSER="portfolio"
$env:PGPASSWORD="change-me"
$env:FRONTEND_ORIGIN="http://localhost:4200"
$env:SPRING_PROFILES_ACTIVE="local"
.\gradlew.bat bootRun
```

On Unix-like systems:

```bash
./gradlew bootRun
```

Flyway validates and applies pending migrations at startup. The health endpoint is available at `http://localhost:8080/actuator/health`.

## Docker

The project includes Docker and Docker Compose support for a repeatable environment.

```bash
docker compose up --build
```

To start only the database infrastructure, use `docker compose up -d postgres` and run the Spring Boot application via Gradle as shown above.

## Deployment

The production architecture is:

```text
Angular frontend (Vercel)
          │
          ▼
Portfolio API (Render)
          │
          ▼
Supabase PostgreSQL
```

Configure `PGHOST`, `PGPORT`, `PGDATABASE`, `PGUSER`, `PGPASSWORD`, `FRONTEND_ORIGIN`, and `SPRING_PROFILES_ACTIVE` in Render’s environment settings. Flyway runs automatically during application startup.

Keep all Supabase credentials in Render’s encrypted environment variables. For Render environments that require IPv4 connectivity, use Supabase’s Session Pooler connection details instead of an IPv6-only direct connection.

## Testing

Run the test suite with:

```bash
./gradlew test
```

On Windows:

```powershell
.\gradlew.bat test
```

Integration tests use Testcontainers to provision an isolated PostgreSQL instance, so they do not depend on a running Compose database. Docker must be available for these tests.

## Troubleshooting Flyway

If Flyway fails at startup, confirm that the PostgreSQL Flyway database module is present and that the datasource uses a JDBC URL (`jdbc:postgresql://...`), not a plain PostgreSQL URI. Because Flyway owns the schema, Hibernate is configured to validate it rather than generate it.

## Roadmap

- [x] Spring Boot 4, Java 25, Gradle, PostgreSQL, and Flyway foundation
- [x] Public read-only API for portfolio content
- [x] Feature-first backend architecture and global exception handling
- [x] Portuguese, English, and Spanish localized data
- [x] Angular frontend integration
- [x] Docker, Render deployment, and Supabase PostgreSQL
- [x] Swagger/OpenAPI and public health check
- [ ] Complete security hardening: security configuration, headers, and production profiles
- [ ] Cache public resources
- [ ] Rate limiting for the public API
- [ ] Expand unit and integration test coverage
- [ ] CI/CD with GitHub Actions
- [ ] Observability: Actuator metrics and structured logs
- [ ] JWT-protected administrative API and Angular administration panel

## Why this project?

This is more than a static personal site: it is a real, deployed full-stack system. It demonstrates how a focused public API can be designed with localization, persistence, schema migrations, documentation, cloud deployment, and a clear path toward authenticated administration—without adding premature complexity to a read-only product.

## Related project

Angular frontend: [dhbart/bartholdy-portfolio](https://github.com/dhbart/bartholdy-portfolio)

## License

This repository is for personal use and is under active development. No open-source license has been declared.
