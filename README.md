# Portfolio API

Backend da plataforma de portfólio pessoal **dhbart**.

Este projeto faz parte da evolução do portfólio para uma aplicação full stack, substituindo os dados estáticos do frontend por uma API REST conectada a um banco de dados.

> **Status:** em desenvolvimento — fase de fundação do projeto.

## Objetivo

Disponibilizar uma API para gerenciar e expor as informações profissionais do portfólio, incluindo:

- apresentação pessoal;
- experiência profissional;
- projetos;
- tecnologias;
- certificações;
- links de redes sociais.

## Tecnologias

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

## Arquitetura atual

O backend usa arquitetura feature-first. Cada domínio possui `application`, `domain` e `infrastructure`, mantendo o fluxo:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

Os contratos da API são definidos por DTOs, mantendo as entidades de persistência separadas da camada HTTP.

## Estrutura do projeto

```text
src/
├── main/
│   ├── java/dhbart/portfolioapi/
│   └── resources/
└── test/
    └── java/dhbart/portfolioapi/
```

Cada feature é organizada da seguinte forma:

```text
config/
controller/
dto/
entity/
exception/
mapper/
repository/
service/
```

## Pré-requisitos

- JDK 25;
- Docker, para execução dos serviços de infraestrutura e dos testes de integração;
- acesso ao PostgreSQL quando a persistência for habilitada.

O projeto inclui o Gradle Wrapper, portanto não é necessário instalar o Gradle globalmente.

## Configuração de ambiente

A aplicação utiliza variáveis de ambiente para acessar o PostgreSQL. Nenhuma credencial deve ser versionada.

Variáveis obrigatórias:

```text
DB_URL=jdbc:postgresql://localhost:5432/portfolio
DB_USERNAME=portfolio
DB_PASSWORD=change-me
```

Opcionalmente, configure `FRONTEND_ORIGIN` para alterar a origem permitida pelo CORS. O padrão é `http://localhost:4200`.

## Executando o projeto

No Windows:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/portfolio"
$env:DB_USERNAME="portfolio"
$env:DB_PASSWORD="change-me"
.\gradlew.bat bootRun --args='--spring.profiles.active=local'
```

Em sistemas Unix-like:

```bash
./gradlew bootRun
```

Ao iniciar, o Flyway valida o schema PostgreSQL e os endpoints públicos ficam disponíveis sem autenticação.

O health check está disponível em `http://localhost:8080/actuator/health`.

## Troubleshooting PostgreSQL/Flyway

If Flyway fails during startup, confirm that `org.flywaydb:flyway-database-postgresql` is declared and that the datasource URL uses `jdbc:postgresql://...`, not `postgresql://...`. When Flyway owns the schema, keep `spring.jpa.hibernate.ddl-auto=validate`.

## Executando os testes

Para iniciar o PostgreSQL localmente:

```powershell
docker compose up -d postgres
```

Depois, configure `DB_URL`, `DB_USERNAME` e `DB_PASSWORD` e execute a aplicação. O Flyway executará as migrations automaticamente.

Os testes de integração utilizam Testcontainers e iniciam seu próprio PostgreSQL 18.4. Eles não dependem de um Compose em execução.

No Windows:

```powershell
.\gradlew.bat test
```

Em sistemas Unix-like:

```bash
./gradlew test
```

Os testes de integração utilizarão Testcontainers para executar dependências reais de infraestrutura de forma isolada.

## Documentação da API

Swagger UI: `http://localhost:8080/swagger-ui/index.html`

OpenAPI JSON: `http://localhost:8080/v3/api-docs`

Todos os endpoints REST de leitura aparecem na especificação OpenAPI.

## Roadmap

- [x] Criar o projeto Spring Boot;
- [x] Configurar Java 25 e Gradle 9.7.0;
- [x] Adicionar as dependências principais;
- [x] Criar a documentação de arquitetura;
- [x] Configurar PostgreSQL;
- [x] Configurar migrations com Flyway;
- [x] Implementar o domínio `Hero`;
- [x] Criar o endpoint `GET /api/v1/hero`;
- [x] Implementar os domínios de leitura do portfólio;
- [x] Padronizar tratamento global de exceções;
- [x] Eliminar N+1 na listagem de Projects;
- [x] Preparar a infraestrutura de segurança pública;
- [x] Verificar a documentação OpenAPI;
- [ ] Integrar o frontend Angular;
- [ ] Adicionar autenticação JWT quando operações administrativas forem criadas;
- [ ] Preparar o deploy.

## Projeto relacionado

Frontend Angular do portfólio:

<https://github.com/dhbart/bartholdy-portfolio>

## Licença

Este projeto é de uso pessoal e está em desenvolvimento.
