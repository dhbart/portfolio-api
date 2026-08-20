INSERT INTO hero (id, locale, greeting, name, title, description, primary_button_label, primary_button_url,
                  secondary_button_label, secondary_button_url, created_at, updated_at)
SELECT id + 100, 'en-US', greeting, name, title, description, primary_button_label, primary_button_url,
       secondary_button_label, secondary_button_url, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM hero WHERE locale = 'pt-BR';

INSERT INTO hero (id, locale, greeting, name, title, description, primary_button_label, primary_button_url,
                  secondary_button_label, secondary_button_url, created_at, updated_at)
SELECT id + 200, 'es-ES', greeting, name, title, description, primary_button_label, primary_button_url,
       secondary_button_label, secondary_button_url, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM hero WHERE locale = 'pt-BR';

UPDATE hero SET greeting = 'Hi, I''m', description = 'I enjoy solving business problems through technology, building scalable solutions and leading high-performing teams.',
                primary_button_label = 'View Projects', secondary_button_label = 'Download Resume'
WHERE locale = 'en-US';
UPDATE hero SET greeting = 'Hola, soy', description = 'Me gusta resolver problemas de negocio con tecnología, construir soluciones escalables y liderar equipos de alto rendimiento.',
                primary_button_label = 'Ver proyectos', secondary_button_label = 'Descargar currículum'
WHERE locale = 'es-ES';

INSERT INTO about (id, locale, title, description, created_at, updated_at)
SELECT id + 100, 'en-US', 'About Me', 'I am a technology professional focused on solving business problems through simple, scalable and meaningful solutions.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM about WHERE locale = 'pt-BR';
INSERT INTO about (id, locale, title, description, created_at, updated_at)
SELECT id + 200, 'es-ES', 'Sobre mí', 'Soy un profesional de tecnología enfocado en resolver problemas de negocio con soluciones simples, escalables y significativas.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM about WHERE locale = 'pt-BR';

INSERT INTO project (id, locale, slug, title, headline, description, challenge, solution, image_url, github_url, demo_url,
                    featured, display_order, created_at, updated_at)
SELECT id + 100, 'en-US', slug, title, headline, description, challenge, solution, image_url, github_url, demo_url,
       featured, display_order, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM project WHERE locale = 'pt-BR';
INSERT INTO project (id, locale, slug, title, headline, description, challenge, solution, image_url, github_url, demo_url,
                    featured, display_order, created_at, updated_at)
SELECT id + 200, 'es-ES', slug, title, headline, description, challenge, solution, image_url, github_url, demo_url,
       featured, display_order, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM project WHERE locale = 'pt-BR';

INSERT INTO experience (id, locale, company, location, period, position, summary, description, highlights, technologies,
                        start_date, end_date, current_position, display_order, created_at, updated_at)
SELECT id + 100, 'en-US', company, location, period, position, summary, description, highlights, technologies,
       start_date, end_date, current_position, display_order, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM experience WHERE locale = 'pt-BR';
INSERT INTO experience (id, locale, company, location, period, position, summary, description, highlights, technologies,
                        start_date, end_date, current_position, display_order, created_at, updated_at)
SELECT id + 200, 'es-ES', company, location, period, position, summary, description, highlights, technologies,
       start_date, end_date, current_position, display_order, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM experience WHERE locale = 'pt-BR';

INSERT INTO certification (id, locale, title, issuer, description, certification_type, issue_date, credential_code,
                           credential_url, image_url, display_order, created_at, updated_at)
SELECT id + 100, 'en-US', title, issuer, description, certification_type, issue_date, credential_code,
       credential_url, image_url, display_order, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM certification WHERE locale = 'pt-BR';
INSERT INTO certification (id, locale, title, issuer, description, certification_type, issue_date, credential_code,
                           credential_url, image_url, display_order, created_at, updated_at)
SELECT id + 200, 'es-ES', title, issuer, description, certification_type, issue_date, credential_code,
       credential_url, image_url, display_order, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM certification WHERE locale = 'pt-BR';
