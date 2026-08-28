package dhbart.portfolioapi.assistant.service;

import dhbart.portfolioapi.assistant.config.AiProperties;
import dhbart.portfolioapi.assistant.model.ChatResponse;
import dhbart.portfolioapi.assistant.retrieval.config.RetrievalProperties;
import dhbart.portfolioapi.assistant.retrieval.model.Context;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private static final Logger log = LoggerFactory.getLogger(ChatService.class);
    private final OpenAiResilienceService openAi;
    private final PromptService promptService;
    private final AiProperties aiProperties;
    private final RetrievalProperties retrievalProperties;

    public ChatService(OpenAiResilienceService openAi, PromptService promptService,
                       AiProperties aiProperties, RetrievalProperties retrievalProperties) {
        this.openAi = openAi;
        this.promptService = promptService;
        this.aiProperties = aiProperties;
        this.retrievalProperties = retrievalProperties;
    }

    public ChatResponse chat(String message, Context context) {
        if (!aiProperties.enabled()) throw new IllegalStateException("AI assistant is disabled");
        long started = System.nanoTime();
        String prompt = promptService.build(context, message, retrievalProperties.maxContextLength());
        long openAiStarted = System.nanoTime();
        String response;
        try {
            response = openAi.generate(prompt);
        } catch (OpenAiResilienceService.OpenAiCommunicationException exception) {
            log.warn("OpenAI generation unavailable; returning fallback");
            response = "I’m sorry, the assistant is temporarily unavailable. Please try again later.";
        }
        log.info("Chat completed: vectorChunks={}, structuredSections={}, openAiMs={}, totalMs={}",
                context.vectorChunks().size(), context.structuredSections().size(),
                (System.nanoTime() - openAiStarted) / 1_000_000,
                (System.nanoTime() - started) / 1_000_000);
        return new ChatResponse(response, aiProperties.model(), Instant.now());
    }
}
