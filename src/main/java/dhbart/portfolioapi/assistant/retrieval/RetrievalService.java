package dhbart.portfolioapi.assistant.retrieval;

import dhbart.portfolioapi.assistant.retrieval.config.RetrievalProperties;
import dhbart.portfolioapi.assistant.retrieval.model.KnowledgeChunk;
import dhbart.portfolioapi.assistant.retrieval.repository.KnowledgeRetrievalRepository;
import dhbart.portfolioapi.assistant.service.EmbeddingService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RetrievalService {
    private static final Logger log = LoggerFactory.getLogger(RetrievalService.class);
    private final EmbeddingService embeddingService;
    private final KnowledgeRetrievalRepository repository;
    private final RetrievalProperties properties;

    public RetrievalService(EmbeddingService embeddingService, KnowledgeRetrievalRepository repository,
            RetrievalProperties properties) {
        this.embeddingService = embeddingService;
        this.repository = repository;
        this.properties = properties;
    }

    public List<KnowledgeChunk> retrieve(String question) {
        long started = System.nanoTime();
        float[] embedding = embeddingService.generate(question);
        long vectorStarted = System.nanoTime();
        List<KnowledgeChunk> chunks = repository.findSimilar(embedding, properties.topK());
        long vectorMs = (System.nanoTime() - vectorStarted) / 1_000_000;
        log.info("Knowledge retrieval completed: question={}, chunks={}, distances={}, vectorSearchMs={}, elapsedMs={}", question,
                chunks.size(), chunks.stream().map(KnowledgeChunk::getSimilarityScore).toList(),
                vectorMs, (System.nanoTime() - started) / 1_000_000);
        return chunks;
    }
}
