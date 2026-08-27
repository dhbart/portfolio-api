CREATE TABLE knowledge.documents
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    title TEXT NOT NULL,

    source TEXT,

    language VARCHAR(10),

    file_type VARCHAR(20),

    original_file_name TEXT NOT NULL,

    mime_type VARCHAR(100),

    file_size BIGINT,

    file_hash CHAR(64) NOT NULL UNIQUE,

    created_at TIMESTAMP DEFAULT NOW(),

    ingested_at TIMESTAMP DEFAULT NOW()
);


CREATE TABLE knowledge.chunks
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    document_id UUID NOT NULL REFERENCES knowledge.documents(id),

    chunk_index INTEGER NOT NULL,

    content TEXT NOT NULL,

    metadata JSONB,

    created_at TIMESTAMP DEFAULT NOW()
);
