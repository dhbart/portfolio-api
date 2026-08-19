CREATE TABLE social_link (
    id BIGINT PRIMARY KEY,
    label VARCHAR(255) NOT NULL,
    value VARCHAR(255) NOT NULL,
    url VARCHAR(2048) NOT NULL,
    icon VARCHAR(100) NOT NULL,
    display_order INTEGER NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_social_link_display_order ON social_link (display_order);

INSERT INTO social_link (
    id, label, value, url, icon, display_order, created_at, updated_at
) VALUES
    (1, 'LinkedIn', 'linkedin.com/in/daniel-bartholdy',
     'https://linkedin.com/in/daniel-bartholdy', 'linkedin', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'GitHub', 'github.com/dhbart',
     'https://github.com/dhbart', 'github', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
