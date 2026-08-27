ALTER TABLE knowledge.documents
    ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'PENDING';

ALTER TABLE knowledge.documents
    ADD COLUMN processing_started_at TIMESTAMP;

ALTER TABLE knowledge.documents
    ADD COLUMN processing_finished_at TIMESTAMP;

ALTER TABLE knowledge.documents
    ADD COLUMN error_message TEXT;

CREATE INDEX idx_documents_status
    ON knowledge.documents(status);

UPDATE knowledge.documents SET STATUS = 'COMPLETED';

ALTER TABLE knowledge.documents
    ADD CONSTRAINT chk_documents_status
        CHECK (
            status IN (
                       'PENDING',
                       'PROCESSING',
                       'COMPLETED',
                       'FAILED'
                )
            );