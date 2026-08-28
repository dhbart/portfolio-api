package dhbart.portfolioapi.assistant.retrieval;

import dhbart.portfolioapi.assistant.retrieval.model.Context;
import dhbart.portfolioapi.assistant.retrieval.model.KnowledgeChunk;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ContextBuilder {
    public Context build(Map<String, String> structuredSections, List<KnowledgeChunk> vectorChunks) {
        Map<String, String> uniqueSections = new LinkedHashMap<>();
        structuredSections.forEach((name, value) -> {
            if (value != null && !value.isBlank()) uniqueSections.put(name, value);
        });
        String structuredText = String.join("\n", uniqueSections.values()).toLowerCase(Locale.ROOT);
        var uniqueChunks = vectorChunks.stream()
                .filter(chunk -> chunk.getContent() != null && !chunk.getContent().isBlank())
                .filter(chunk -> !structuredText.contains(chunk.getContent().trim().toLowerCase(Locale.ROOT)))
                .filter(new java.util.function.Predicate<>() {
                    private final java.util.Set<String> seen = new java.util.HashSet<>();
                    public boolean test(KnowledgeChunk chunk) {
                        return seen.add(chunk.getContent().trim().toLowerCase(Locale.ROOT));
                    }
                })
                .toList();
        return new Context(uniqueSections, uniqueChunks);
    }
}
