package dhbart.portfolioapi.assistant.prompt;

import dhbart.portfolioapi.assistant.retrieval.model.Context;
import dhbart.portfolioapi.assistant.retrieval.model.KnowledgeChunk;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {
    private static final String EMPTY_CONTEXT_WARNING = "No relevant knowledge was found.";

    public String build(String systemPrompt, Context context, String question, int maxContextLength) {
        StringBuilder assembled = new StringBuilder();
        context.structuredSections().forEach((section, content) -> append(assembled, section, content, maxContextLength));
        StringBuilder vectorContext = new StringBuilder();
        for (KnowledgeChunk chunk : context.vectorChunks()) {
            if (chunk.getContent() == null || chunk.getContent().isBlank()) continue;
            String separator = vectorContext.isEmpty() ? "" : "\n\n";
            if (vectorContext.length() + separator.length() + chunk.getContent().length() > maxContextLength) break;
            vectorContext.append(separator).append(chunk.getContent());
        }
        append(assembled, "VECTOR KNOWLEDGE", vectorContext.toString(), Integer.MAX_VALUE);
        if (assembled.isEmpty()) assembled.append(EMPTY_CONTEXT_WARNING);
        return systemPrompt + "\n\n<retrieved_context>\n" + assembled
                + "\n</retrieved_context>\n\n<user_question>\n" + question
                + "\n</user_question>";
    }

    private void append(StringBuilder target, String section, String content, int maxLength) {
        if (content == null || content.isBlank()) return;
        String candidate = (target.isEmpty() ? "" : "\n\n") + section + "\n" + content;
        if (target.length() + candidate.length() <= maxLength) target.append(candidate);
    }

}
