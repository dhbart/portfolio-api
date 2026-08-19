CREATE TABLE certification (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    issuer VARCHAR(255),
    description TEXT,
    certification_type VARCHAR(32) NOT NULL,
    issue_date DATE,
    credential_code VARCHAR(255),
    credential_url VARCHAR(2048),
    image_url VARCHAR(2048),
    display_order INTEGER NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT certification_type_check CHECK (certification_type IN ('DEGREE', 'MBA', 'BOOTCAMP', 'COURSE', 'CERTIFICATION'))
);

CREATE INDEX idx_certification_display_order ON certification (display_order);
