CREATE TABLE project (
    id BIGINT PRIMARY KEY,
    slug VARCHAR(255) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    headline VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    challenge TEXT NOT NULL,
    solution TEXT NOT NULL,
    image_url VARCHAR(2048),
    github_url VARCHAR(2048),
    demo_url VARCHAR(2048),
    featured BOOLEAN NOT NULL,
    display_order INTEGER NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_project_display_order ON project (display_order);
