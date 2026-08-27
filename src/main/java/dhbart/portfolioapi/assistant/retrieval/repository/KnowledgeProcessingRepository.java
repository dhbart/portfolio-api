package dhbart.portfolioapi.assistant.retrieval.repository;

import dhbart.portfolioapi.assistant.model.KnowledgeDocument;
import dhbart.portfolioapi.assistant.model.UnprocessedChunk;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface KnowledgeProcessingRepository {

    Optional<KnowledgeDocument> findDocument(UUID documentId);

    List<UnprocessedChunk> findChunksWithoutEmbedding(UUID documentId);

    void markProcessing(UUID documentId);

    void saveEmbedding(UUID chunkId, float[] embedding);

    void markCompleted(UUID documentId);

    void markFailed(UUID documentId, String errorMessage);
}
