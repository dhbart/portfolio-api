package dhbart.portfolioapi.assistant.prompt;

import static org.assertj.core.api.Assertions.assertThat;

import dhbart.portfolioapi.assistant.retrieval.model.KnowledgeChunk;
import dhbart.portfolioapi.assistant.retrieval.model.Context;
import java.util.Map;
import java.util.List;
import org.junit.jupiter.api.Test;

class PromptBuilderTest {
    private final PromptBuilder builder = new PromptBuilder();

    @Test
    void preservesChunkOrderAndQuestion() {
        var chunks = List.of(chunk("first"), chunk("second"));
        String prompt = builder.build("System", new Context(Map.of(), chunks), "What?", 100);
        assertThat(prompt).contains("System", "<retrieved_context>", "first\n\nsecond", "<user_question>", "What?");
    }

    @Test
    void stopsBeforeContextExceedsConfiguredLength() {
        String prompt = builder.build("System", new Context(Map.of(), List.of(chunk("12345"), chunk("67890"))), "Q", 7);
        assertThat(prompt).contains("12345").doesNotContain("67890");
    }

    @Test
    void includesInternalWarningWhenNoChunkExists() {
        assertThat(builder.build("System", new Context(Map.of(), List.of()), "Q", 10))
                .contains("No relevant knowledge was found.");
    }

    private KnowledgeChunk chunk(String text) {
        return new KnowledgeChunk("id", "doc", null, null, text, 0.1, java.util.Map.of());
    }
}
