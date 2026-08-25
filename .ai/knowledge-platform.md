# Knowledge Platform Specification

## Vision

The Portfolio Platform is evolving into a Knowledge Platform. The knowledge base is the source of truth; the LLM only turns retrieved knowledge into natural language.

## Architecture

```text
Knowledge Sources (PDF, DOCX, Markdown, future sources)
        ↓
Local n8n ingestion pipeline → Chunking → Embeddings
        ↓
Supabase pgvector → Portfolio API → Retrieval Layer → RetrievalService
        ↓
Spring AI → OpenAI → Angular Chat
```

Knowledge Ingestion prepares sources outside the production API. Knowledge Storage owns chunks and embeddings. The Retrieval Layer consumes indexed knowledge through a repository port and exposes the `RetrievalService` boundary to the Assistant module. Response Generation uses Spring AI and OpenAI. The backend never processes documents directly, generates embeddings, or owns the ingestion workflow.

## Retrieval Layer

The Retrieval Layer lives inside `assistant/retrieval` and is independent from the portfolio business modules. Its contracts are `KnowledgeChunk` (the persistence-agnostic indexed-chunk model), `KnowledgeRepository` (the store-independent access port), `RetrievalService` (the future Assistant boundary), and typed `RetrievalProperties`.

This sprint defines contracts only. There is no SQL implementation, similarity search, embeddings, pgvector query, context assembly, or document processing. Future vector-store adapters will implement `KnowledgeRepository`; `AssistantService` remains focused on chat orchestration and must never access a store directly.

## Knowledge lifecycle

```text
PDF / DOCX / Markdown
        ↓
Local n8n → Chunking → Embedding Generation
        ↓
Supabase pgvector
        ↓
KnowledgeRepository → RetrievalService → AssistantService → Spring AI
```

The ingestion pipeline is external and local. Production only consumes already-indexed knowledge through the retrieval boundary.

## Production vs Local Infrastructure

Production: Angular, Portfolio API, Spring AI, OpenAI, and Supabase with planned pgvector storage.

Local development: n8n ingestion, document ingestion, embedding generation, and optional experimentation tools.

n8n is never part of the production runtime. OmniRoute is only an optional local experimentation tool and is not used by the production backend. Production communicates directly with OpenAI through Spring AI.

## Design Principles

- Knowledge-first architecture.
- Separation of ingestion and retrieval.
- Retrieval before generation once RAG exists.
- Stateless assistant.
- Documents are external knowledge assets.
- Backend never processes documents directly.
- Backend never generates embeddings.
- Backend only consumes the future vector database through retrieval adapters.

## Future Evolution

Vector Search through a pgvector adapter, RAG context assembly, Conversation Memory, Streaming Responses, AI Tools / Function Calling, Knowledge Administration, and additional document sources.

## Glossary

- **Chunk:** A bounded piece of source content used for indexing and retrieval.
- **Embedding:** A numerical vector representing semantic content.
- **Vector Database:** Storage optimized for similarity search over embeddings.
- **Retrieval:** Selecting relevant knowledge for a user request.
- **Context:** Retrieved content supplied to the generation prompt.
- **Prompt:** Instructions and input sent to a language model.
- **RAG:** Retrieval-Augmented Generation: retrieval followed by grounded generation.
- **Knowledge Base:** The curated collection of indexed knowledge assets.
- **Knowledge Platform:** A system that ingests, stores, retrieves, and presents knowledge.
- **Ingestion Pipeline:** A workflow transforming sources into stored chunks and embeddings.
