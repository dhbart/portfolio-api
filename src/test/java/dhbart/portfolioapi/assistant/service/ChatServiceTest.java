package dhbart.portfolioapi.assistant.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import dhbart.portfolioapi.assistant.config.AiProperties;
import dhbart.portfolioapi.assistant.prompt.PromptBuilder;
import dhbart.portfolioapi.assistant.prompt.PromptLoader;
import dhbart.portfolioapi.assistant.retrieval.RetrievalService;
import dhbart.portfolioapi.assistant.retrieval.config.RetrievalProperties;
import dhbart.portfolioapi.assistant.retrieval.model.KnowledgeChunk;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;

class ChatServiceTest {
    @Test
    void retrievesBeforeCallingOpenAiWithEnrichedPrompt() {
        ChatClient chatClient = mock(ChatClient.class, RETURNS_DEEP_STUBS);
        when(chatClient.prompt().user(anyString()).call().content()).thenReturn("answer");
        PromptLoader loader = mock(PromptLoader.class);
        when(loader.load("prompt.md")).thenReturn("system");
        RetrievalService retrieval = mock(RetrievalService.class);
        when(retrieval.retrieve("question")).thenReturn(List.of(
                new KnowledgeChunk("1", "d", null, null, "context", .1, java.util.Map.of())));
        ChatService service = new ChatService(chatClient, loader, new PromptBuilder(), retrieval,
                new AiProperties(true, "model", .2, 512, "prompt.md"), new RetrievalProperties(5, 6000));

        assertThat(service.chat("question").response()).isEqualTo("answer");
        verify(retrieval).retrieve("question");
        verify(chatClient.prompt()).user(contains("context"));
    }
}
