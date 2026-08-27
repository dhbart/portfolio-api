package dhbart.portfolioapi.assistant.service;

import static org.mockito.Mockito.*;

import dhbart.portfolioapi.assistant.config.EmbeddingProperties;
import dhbart.portfolioapi.assistant.model.*;
import dhbart.portfolioapi.assistant.retrieval.repository.KnowledgeProcessingRepository;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class KnowledgeProcessingServiceTest {

    private final KnowledgeProcessingRepository repository = mock(KnowledgeProcessingRepository.class);
    private final EmbeddingService embeddingService = mock(EmbeddingService.class);
    private final KnowledgeProcessingService service = new KnowledgeProcessingService(repository, embeddingService,
            new EmbeddingProperties("key", "http://localhost", "test-model", Duration.ofSeconds(1), 1,
                    Duration.ZERO));

    @Test
    void embedsOnlyChunksWithoutEmbeddingAndCompletesDocument() {
        UUID documentId = UUID.randomUUID();
        UUID chunkId = UUID.randomUUID();
        when(repository.findDocument(documentId)).thenReturn(Optional.of(new KnowledgeDocument(documentId,
                DocumentProcessingStatus.PENDING)));
        when(repository.findChunksWithoutEmbedding(documentId)).thenReturn(List.of(new UnprocessedChunk(chunkId, "text")));
        when(embeddingService.generate("text")).thenReturn(new float[]{1f, 2f});

        service.process(documentId);

        verify(repository).markProcessing(documentId);
        verify(repository).saveEmbedding(chunkId, new float[]{1f, 2f});
        verify(repository).markCompleted(documentId);
        verify(repository, never()).markFailed(any(), any());
    }

    @Test
    void marksDocumentFailedWhenProviderThrows() {
        UUID documentId = UUID.randomUUID();
        when(repository.findDocument(documentId)).thenReturn(Optional.of(new KnowledgeDocument(documentId,
                DocumentProcessingStatus.PENDING)));
        when(repository.findChunksWithoutEmbedding(documentId)).thenReturn(List.of(new UnprocessedChunk(UUID.randomUUID(), "text")));
        when(embeddingService.generate("text")).thenThrow(new RuntimeException("provider unavailable"));

        service.process(documentId);

        verify(repository).markFailed(eq(documentId), contains("provider unavailable"));
        verify(repository, never()).markCompleted(documentId);
    }
}
