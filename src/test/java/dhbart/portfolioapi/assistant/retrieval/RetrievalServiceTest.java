package dhbart.portfolioapi.assistant.retrieval;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import dhbart.portfolioapi.assistant.retrieval.config.RetrievalProperties;
import dhbart.portfolioapi.assistant.retrieval.model.KnowledgeChunk;
import dhbart.portfolioapi.assistant.retrieval.repository.KnowledgeRetrievalRepository;
import dhbart.portfolioapi.assistant.service.EmbeddingService;
import java.util.List;
import org.junit.jupiter.api.Test;

class RetrievalServiceTest {
    private final EmbeddingService embedding = mock(EmbeddingService.class);
    private final KnowledgeRetrievalRepository repository = mock(KnowledgeRetrievalRepository.class);
    private final RetrievalService service = new RetrievalService(embedding, repository, new RetrievalProperties(5, 6000));

    @Test
    void generatesQuestionEmbeddingAndReturnsTopKInRepositoryOrder() {
        float[] vector = {1f, 2f};
        var chunks = List.of(new KnowledgeChunk("1", "doc", null, null, "answer", .1, java.util.Map.of()));
        when(embedding.generate("question")).thenReturn(vector);
        when(repository.findSimilar(vector, 5)).thenReturn(chunks);

        assertThat(service.retrieve("question")).containsExactlyElementsOf(chunks);
        verify(repository).findSimilar(vector, 5);
    }
}
