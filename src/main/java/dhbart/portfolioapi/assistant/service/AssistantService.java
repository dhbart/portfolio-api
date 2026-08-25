package dhbart.portfolioapi.assistant.service;

import dhbart.portfolioapi.assistant.config.AiProperties;
import dhbart.portfolioapi.assistant.model.ChatResponse;
import dhbart.portfolioapi.assistant.prompt.PromptLoader;
import java.time.Instant;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AssistantService {

    private final ChatClient chatClient;
    private final PromptLoader promptLoader;
    private final AiProperties aiProperties;

    public AssistantService(
            ChatClient chatClient,
            PromptLoader promptLoader,
            AiProperties aiProperties) {
        this.chatClient = chatClient;
        this.promptLoader = promptLoader;
        this.aiProperties = aiProperties;
    }

    public ChatResponse chat(String message) {
        if (!aiProperties.enabled()) {
            throw new IllegalStateException("AI assistant is disabled");
        }

        String response = chatClient.prompt()
                .system(promptLoader.load(aiProperties.systemPrompt()))
                .user(message)
                .call()
                .content();

        return new ChatResponse(response, aiProperties.model(), Instant.now());
    }
}
