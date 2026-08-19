INSERT INTO technology (id, name, slug, display_order, created_at, updated_at) VALUES
    (1, 'Angular', 'angular', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'TypeScript', 'typescript', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'SCSS', 'scss', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 'Java', 'java', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 'Spring Boot', 'spring-boot', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (6, 'Spring AI', 'spring-ai', 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (7, 'Tool Calling', 'tool-calling', 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (8, 'Speech-to-Text', 'speech-to-text', 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (9, 'Text-to-Speech', 'text-to-speech', 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (10, 'Java 21', 'java-21', 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (11, 'Gradle', 'gradle', 11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (12, 'PostgreSQL', 'postgresql', 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (13, 'OpenAPI', 'openapi', 13, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (14, 'Domain-Driven Design', 'domain-driven-design', 14, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (15, 'MySQL', 'mysql', 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (16, 'MongoDB', 'mongodb', 16, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (17, 'Redis', 'redis', 17, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (18, 'REST API', 'rest-api', 18, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (19, 'Swagger', 'swagger', 19, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (20, 'Design Patterns', 'design-patterns', 20, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (21, 'TMDB API', 'tmdb-api', 21, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO project_technology (project_id, technology_id, display_order)
SELECT p.id, t.id, links.display_order
FROM (VALUES
    ('portfolio', 'angular', 1), ('portfolio', 'typescript', 2), ('portfolio', 'scss', 3),
    ('budgeting', 'java', 1), ('budgeting', 'spring-boot', 2), ('budgeting', 'spring-ai', 3),
    ('budgeting', 'tool-calling', 4), ('budgeting', 'speech-to-text', 5), ('budgeting', 'text-to-speech', 6),
    ('crescer-juntos', 'java-21', 1), ('crescer-juntos', 'spring-boot', 2), ('crescer-juntos', 'gradle', 3),
    ('crescer-juntos', 'postgresql', 4), ('crescer-juntos', 'openapi', 5), ('crescer-juntos', 'domain-driven-design', 6),
    ('tickets-marketplace', 'java', 1), ('tickets-marketplace', 'spring-boot', 2),
    ('tickets-marketplace', 'mysql', 3), ('tickets-marketplace', 'postgresql', 4),
    ('tickets-marketplace', 'mongodb', 5), ('tickets-marketplace', 'redis', 6),
    ('movie-api', 'java-21', 1), ('movie-api', 'spring-boot', 2), ('movie-api', 'gradle', 3),
    ('movie-api', 'rest-api', 4), ('movie-api', 'swagger', 5), ('movie-api', 'design-patterns', 6),
    ('movie-api', 'tmdb-api', 7)
) AS links(project_slug, technology_slug, display_order)
JOIN project p ON p.slug = links.project_slug
JOIN technology t ON t.slug = links.technology_slug;
