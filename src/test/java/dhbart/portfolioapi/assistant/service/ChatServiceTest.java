package dhbart.portfolioapi.assistant.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import dhbart.portfolioapi.assistant.config.AiProperties;
import dhbart.portfolioapi.assistant.retrieval.config.RetrievalProperties;
import dhbart.portfolioapi.assistant.retrieval.model.Context;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ChatServiceTest {
    @Test
    void returnsGeneratedResponseFromTheResilientGateway() {
        OpenAiResilienceService openAi = mock(OpenAiResilienceService.class);
        PromptService prompts = mock(PromptService.class);
        when(prompts.build(any(), eq("question"), eq(6000))).thenReturn("safe prompt");
        when(openAi.generate("safe prompt")).thenReturn("answer");
        ChatService service = new ChatService(openAi, prompts,
                new AiProperties(true, "model", .2, 512, "prompt.md"), new RetrievalProperties(5, 6000));
        assertThat(service.chat("question", new Context(Map.of(), java.util.List.of())).response()).isEqualTo("answer");
    }

    @Test
    void returnsFallbackWhenOpenAiIsUnavailable() {
        OpenAiResilienceService openAi = mock(OpenAiResilienceService.class);
        PromptService prompts = mock(PromptService.class);
        when(prompts.build(any(), anyString(), eq(6000))).thenReturn("safe prompt");
        when(openAi.generate(anyString())).thenThrow(new OpenAiResilienceService.OpenAiCommunicationException("failed", null));
        ChatService service = new ChatService(openAi, prompts,
                new AiProperties(true, "model", .2, 512, "prompt.md"), new RetrievalProperties(5, 6000));
        assertThat(service.chat("question", new Context(Map.of(), java.util.List.of())).response())
                .contains("temporarily unavailable");
    }
}
