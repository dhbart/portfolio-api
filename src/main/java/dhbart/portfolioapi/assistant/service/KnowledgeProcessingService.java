package dhbart.portfolioapi.assistant.service;

import dhbart.portfolioapi.assistant.model.DocumentProcessingStatus;
import dhbart.portfolioapi.assistant.model.KnowledgeDocument;
import dhbart.portfolioapi.assistant.model.UnprocessedChunk;
import dhbart.portfolioapi.assistant.config.EmbeddingProperties;
import dhbart.portfolioapi.assistant.retrieval.repository.KnowledgeProcessingRepository;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeProcessingService {

    private static final Logger log = LoggerFactory.getLogger(KnowledgeProcessingService.class);
    private final KnowledgeProcessingRepository repository;
    private final EmbeddingService embeddingService;
    private final EmbeddingProperties properties;

    public KnowledgeProcessingService(KnowledgeProcessingRepository repository, EmbeddingService embeddingService,
                                      EmbeddingProperties properties) {
        this.repository = repository;
        this.embeddingService = embeddingService;
        this.properties = properties;
    }

    @Async("knowledgeProcessingExecutor")
    public void process(UUID documentId) {
        Instant started = Instant.now();
        try {
            KnowledgeDocument document = repository.findDocument(documentId)
                    .orElseThrow(() -> new IllegalArgumentException("Document not found: " + documentId));
            validateStatus(document);
            repository.markProcessing(documentId);
            List<UnprocessedChunk> chunks = repository.findChunksWithoutEmbedding(documentId);
            log.info("Starting embedding processing documentId={} chunks={} model={}",
                    documentId, chunks.size(), properties.model());
            for (UnprocessedChunk chunk : chunks) {
                Instant chunkStarted = Instant.now();
                repository.saveEmbedding(chunk.id(), embeddingService.generate(chunk.content()));
                log.debug("Embedded chunk documentId={} chunkId={} durationMs={}", documentId, chunk.id(),
                        Duration.between(chunkStarted, Instant.now()).toMillis());
            }
            repository.markCompleted(documentId);
            long elapsed = Duration.between(started, Instant.now()).toMillis();
            log.info("Completed embedding processing documentId={} chunks={} totalMs={} averageMs={}",
                    documentId, chunks.size(), elapsed, chunks.isEmpty() ? 0 : elapsed / chunks.size());
        } catch (Exception exception) {
            log.error("Embedding processing failed documentId={}", documentId, exception);
            try {
                repository.markFailed(documentId, exception.getMessage());
            } catch (Exception failure) {
                log.error("Could not mark document as FAILED documentId={}", documentId, failure);
            }
        }
    }

    private void validateStatus(KnowledgeDocument document) {
        if (document.status() != DocumentProcessingStatus.PENDING
                && document.status() != DocumentProcessingStatus.FAILED
                && document.status() != DocumentProcessingStatus.COMPLETED) {
            throw new IllegalStateException("Document cannot be processed from status " + document.status());
        }
    }
}
