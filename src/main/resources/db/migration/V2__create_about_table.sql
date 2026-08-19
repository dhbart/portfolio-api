CREATE TABLE about (
    id BIGINT PRIMARY KEY CHECK (id = 1),
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

INSERT INTO about (id, title, description, created_at, updated_at)
VALUES (
    1,
    'About Me',
    'I am a technology professional focused on solving business problems through simple, scalable and meaningful solutions.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
