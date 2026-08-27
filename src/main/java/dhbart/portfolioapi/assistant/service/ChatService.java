package dhbart.portfolioapi.assistant.service;

import dhbart.portfolioapi.assistant.config.AiProperties;
import dhbart.portfolioapi.assistant.model.ChatResponse;
import dhbart.portfolioapi.assistant.prompt.PromptBuilder;
import dhbart.portfolioapi.assistant.prompt.PromptLoader;
import dhbart.portfolioapi.assistant.retrieval.RetrievalService;
import dhbart.portfolioapi.assistant.retrieval.config.RetrievalProperties;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private static final Logger log = LoggerFactory.getLogger(ChatService.class);
    private final ChatClient chatClient;
    private final PromptLoader promptLoader;
    private final PromptBuilder promptBuilder;
    private final RetrievalService retrievalService;
    private final AiProperties aiProperties;
    private final RetrievalProperties retrievalProperties;

    public ChatService(ChatClient chatClient, PromptLoader promptLoader, PromptBuilder promptBuilder,
            RetrievalService retrievalService, AiProperties aiProperties, RetrievalProperties retrievalProperties) {
        this.chatClient = chatClient;
        this.promptLoader = promptLoader;
        this.promptBuilder = promptBuilder;
        this.retrievalService = retrievalService;
        this.aiProperties = aiProperties;
        this.retrievalProperties = retrievalProperties;
    }

    public ChatResponse chat(String message) {
        if (!aiProperties.enabled()) throw new IllegalStateException("AI assistant is disabled");
        long started = System.nanoTime();
        var chunks = retrievalService.retrieve(message);
        long retrievalMs = (System.nanoTime() - started) / 1_000_000;
        String prompt = promptBuilder.build(promptLoader.load(aiProperties.systemPrompt()), chunks, message,
                retrievalProperties.maxContextLength());
        long openAiStarted = System.nanoTime();
        String response = chatClient.prompt().user(prompt).call().content();
        long openAiMs = (System.nanoTime() - openAiStarted) / 1_000_000;
        log.info("Chat completed: chunks={}, retrievalMs={}, openAiMs={}, totalMs={}", chunks.size(), retrievalMs,
                openAiMs, (System.nanoTime() - started) / 1_000_000);
        return new ChatResponse(response, aiProperties.model(), Instant.now());
    }
}
