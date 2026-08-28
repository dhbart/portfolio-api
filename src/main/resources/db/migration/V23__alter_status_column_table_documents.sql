ALTER TABLE knowledge.documents
    DROP CONSTRAINT chk_documents_status;

ALTER TABLE knowledge.documents
    ADD CONSTRAINT chk_documents_status
        CHECK (
            status IN (
                       'PENDING',
                       'PROCESSING',
                       'COMPLETED',
                       'EMBEDDING_COMPLETED',
                       'FAILED'
                )
            );