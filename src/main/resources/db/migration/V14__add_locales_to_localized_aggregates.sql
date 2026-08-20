ALTER TABLE hero ADD COLUMN locale VARCHAR(10) NOT NULL DEFAULT 'pt-BR';
ALTER TABLE about ADD COLUMN locale VARCHAR(10) NOT NULL DEFAULT 'pt-BR';
ALTER TABLE project ADD COLUMN locale VARCHAR(10) NOT NULL DEFAULT 'pt-BR';
ALTER TABLE experience ADD COLUMN locale VARCHAR(10) NOT NULL DEFAULT 'pt-BR';
ALTER TABLE certification ADD COLUMN locale VARCHAR(10) NOT NULL DEFAULT 'pt-BR';

ALTER TABLE hero DROP CONSTRAINT IF EXISTS hero_id_check;
ALTER TABLE about DROP CONSTRAINT IF EXISTS about_id_check;
ALTER TABLE project DROP CONSTRAINT IF EXISTS project_slug_key;

CREATE UNIQUE INDEX uq_project_slug_locale ON project (slug, locale);
CREATE INDEX idx_hero_locale ON hero (locale);
CREATE INDEX idx_about_locale ON about (locale);
CREATE INDEX idx_project_locale ON project (locale);
CREATE INDEX idx_experience_locale ON experience (locale);
CREATE INDEX idx_certification_locale ON certification (locale);
