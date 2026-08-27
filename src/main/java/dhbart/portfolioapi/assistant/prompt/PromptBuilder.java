package dhbart.portfolioapi.assistant.prompt;

import dhbart.portfolioapi.assistant.retrieval.model.KnowledgeChunk;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {
    private static final String EMPTY_CONTEXT_WARNING = "No relevant knowledge was found.";

    public String build(String systemPrompt, List<KnowledgeChunk> chunks, String question, int maxContextLength) {
        StringBuilder context = new StringBuilder();
        for (KnowledgeChunk chunk : chunks) {
            String content = chunk.getContent();
            int separatorLength = context.isEmpty() ? 0 : 2;
            if (content == null || context.length() + separatorLength + content.length() > maxContextLength) break;
            if (!context.isEmpty()) context.append("\n\n");
            context.append(content);
        }
        if (context.isEmpty()) context.append(EMPTY_CONTEXT_WARNING);
        return systemPrompt + "\n\nContext\n\n" + context + "\n\nQuestion\n\n" + question;
    }

    public String build(String systemPrompt, List<KnowledgeChunk> chunks, String question) {
        return build(systemPrompt, chunks, question, Integer.MAX_VALUE);
    }
}
