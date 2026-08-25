package dhbart.portfolioapi.assistant.retrieval.repository;

import dhbart.portfolioapi.assistant.retrieval.model.KnowledgeChunk;
import java.util.List;

/**
 * Port for reading knowledge indexed by the external knowledge pipeline.
 *
 * <p>Implementations may use a vector store in a later sprint. This contract
 * intentionally has no Spring AI, pgvector, SQL, or provider dependency.</p>
 */
public interface KnowledgeRepository {

    List<KnowledgeChunk> findRelevant(String query, int topK, double minimumScore);
}
