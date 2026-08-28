package dhbart.portfolioapi.assistant.retrieval;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import dhbart.portfolioapi.assistant.retrieval.model.KnowledgeChunk;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class HybridRetrievalServiceTest {
    private final StructuredRetrievalService structured = mock(StructuredRetrievalService.class);
    private final VectorRetrievalService vector = mock(VectorRetrievalService.class);
    private final HybridRetrievalService service = new HybridRetrievalService(structured, vector, new ContextBuilder());

    @Test
    void mergesStructuredContextBeforeVectorKnowledgeAndRemovesDuplicateChunks() {
        when(structured.retrieve("question")).thenReturn(Map.of("PROFILE", "Daniel"));
        var duplicate = new KnowledgeChunk("1", "d", null, null, "Daniel", .1, Map.of());
        var additional = new KnowledgeChunk("2", "d", null, null, "ERP migration", .2, Map.of());
        when(vector.retrieve("question")).thenReturn(List.of(duplicate, additional, additional));

        var context = service.retrieve("question");

        assertThat(context.structuredSections()).containsEntry("PROFILE", "Daniel");
        assertThat(context.vectorChunks()).extracting(KnowledgeChunk::getContent).containsExactly("ERP migration");
        var order = inOrder(structured, vector);
        order.verify(structured).retrieve("question");
        order.verify(vector).retrieve("question");
    }

    @Test
    void continuesWithStructuredContextWhenVectorRetrievalFails() {
        when(structured.retrieve("question")).thenReturn(Map.of("PROFILE", "Daniel"));
        when(vector.retrieve("question")).thenThrow(new IllegalStateException("vector unavailable"));
        assertThat(service.retrieve("question").structuredSections()).containsKey("PROFILE");
    }

    @Test
    void failsOnlyWhenBothRetrievalMechanismsFail() {
        when(structured.retrieve("question")).thenThrow(new IllegalStateException("sql unavailable"));
        when(vector.retrieve("question")).thenThrow(new IllegalStateException("vector unavailable"));
        assertThatThrownBy(() -> service.retrieve("question"))
                .isInstanceOf(HybridRetrievalService.RetrievalException.class);
    }
}
