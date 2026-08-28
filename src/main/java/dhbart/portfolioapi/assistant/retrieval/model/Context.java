package dhbart.portfolioapi.assistant.retrieval.model;

import java.util.List;
import java.util.Map;

public record Context(Map<String, String> structuredSections, List<KnowledgeChunk> vectorChunks) {
    public Context {
        structuredSections = Map.copyOf(structuredSections);
        vectorChunks = List.copyOf(vectorChunks);
    }
}
