CREATE EXTENSION IF NOT EXISTS vector;

ALTER TABLE knowledge.chunks
    ADD COLUMN embedding vector(1536);


CREATE INDEX idx_chunks_embedding
    ON knowledge.chunks
        USING hnsw (embedding vector_cosine_ops);

CREATE INDEX idx_chunks_document
    ON knowledge.chunks(document_id);

CREATE INDEX idx_documents_language
    ON knowledge.documents(language);