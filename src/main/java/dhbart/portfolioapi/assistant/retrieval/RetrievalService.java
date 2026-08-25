package dhbart.portfolioapi.assistant.retrieval;

import dhbart.portfolioapi.assistant.retrieval.model.KnowledgeChunk;
import java.util.List;

public interface RetrievalService {

    /**
     * Retrieves indexed knowledge for a future RAG flow.
     *
     * <p>Vector search is intentionally not implemented in this sprint.</p>
     */
    default List<KnowledgeChunk> retrieve(String query) {
        throw new UnsupportedOperationException("Knowledge retrieval is not implemented yet");
    }
}
