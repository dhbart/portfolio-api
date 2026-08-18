CREATE TABLE hero (
    id BIGINT PRIMARY KEY CHECK (id = 1),
    greeting VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    primary_button_label VARCHAR(255) NOT NULL,
    primary_button_url VARCHAR(2048) NOT NULL,
    secondary_button_label VARCHAR(255) NOT NULL,
    secondary_button_url VARCHAR(2048) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

INSERT INTO hero (
    id,
    greeting,
    name,
    title,
    description,
    primary_button_label,
    primary_button_url,
    secondary_button_label,
    secondary_button_url,
    created_at,
    updated_at
) VALUES (
    1,
    'Hi, I''m',
    'Daniel Henrique Bartholdy',
    'Software Developer • Tech Lead • Business Analyst • Product Owner',
    'I enjoy solving business problems through technology, building scalable solutions and leading high-performing teams.',
    'View Projects',
    '#projects',
    'Download Resume',
    '/docs/Daniel-Bartholdy-CV.pdf',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
