package dhbart.portfolioapi.assistant.retrieval;

import dhbart.portfolioapi.assistant.retrieval.model.Context;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HybridRetrievalService {
    private static final Logger log = LoggerFactory.getLogger(HybridRetrievalService.class);
    private final StructuredRetrievalService structuredRetrievalService;
    private final VectorRetrievalService vectorRetrievalService;
    private final ContextBuilder contextBuilder;

    public HybridRetrievalService(StructuredRetrievalService structuredRetrievalService,
            VectorRetrievalService vectorRetrievalService, ContextBuilder contextBuilder) {
        this.structuredRetrievalService = structuredRetrievalService;
        this.vectorRetrievalService = vectorRetrievalService;
        this.contextBuilder = contextBuilder;
    }

    public Context retrieve(String question) {
        boolean structuredOk = false;
        boolean vectorOk = false;
        var structured = java.util.Map.<String, String>of();
        var chunks = List.<dhbart.portfolioapi.assistant.retrieval.model.KnowledgeChunk>of();
        try {
            structured = structuredRetrievalService.retrieve(question);
            structuredOk = true;
            log.debug("Structured retrieval executed: entities={}", structured.keySet());
        } catch (RuntimeException exception) {
            log.warn("Structured retrieval failed; continuing with vector knowledge", exception);
        }
        try {
            chunks = vectorRetrievalService.retrieve(question);
            vectorOk = true;
            log.debug("Vector retrieval executed: chunks={}", chunks.size());
        } catch (RuntimeException exception) {
            log.warn("Vector retrieval failed; continuing with structured context", exception);
        }
        if (!structuredOk && !vectorOk) throw new RetrievalException("Both retrieval mechanisms failed");
        Context context = contextBuilder.build(structured, chunks);
        log.debug("Merged context size: structuredSections={}, vectorChunks={}",
                context.structuredSections().size(), context.vectorChunks().size());
        return context;
    }

    public static class RetrievalException extends RuntimeException {
        public RetrievalException(String message) { super(message); }
    }
}
